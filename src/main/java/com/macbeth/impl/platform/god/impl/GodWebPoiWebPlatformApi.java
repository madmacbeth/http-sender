package com.macbeth.impl.platform.god.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.macbeth.enumeration.MediaTypeEnum;
import com.macbeth.impl.platform.god.enumeration.ResponseContentTypeEnum;
import com.macbeth.util.ReflectUtils;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * 高德web 服务API 搜索POI信息
 * @author chen
 * @date 2020-12-26
 */
@Data
public class GodWebPoiWebPlatformApi extends BaseGodWebPlatformApi {
    /**
     * 规则： 多个关键字用“|”分割
     *
     * 若不指定city，并且搜索的为泛词（例如“美食”）的情况下，返回的内容为城市列表以及此城市内有多少结果符合要求。
     */
    private String keyWords;
    /**
     * 可选值：分类代码 或 汉字（若用汉字，请严格按照附件之中的汉字填写）
     *
     *
     *
     * 分类代码由六位数字组成，一共分为三个部分，前两个数字代表大类；中间两个数字代表中类；最后两个数字代表小类。
     *
     * 若指定了某个大类，则所属的中类、小类都会被显示。
     *
     * 例如：010000为汽车服务（大类）
     *
     *              010100为加油站（中类）
     *
     *                 010101为中国石化（小类）
     *
     *              010900为汽车租赁（中类）
     *
     *                 010901为汽车租赁还车（小类）
     *
     * 当指定010000，则010100等中类、010101等小类都会被包含，当指定010900，则010901等小类都会被包含。
     *
     * 下载POI分类编码和城市编码表
     *
     *
     *
     * 若不指定city，返回的内容为城市列表以及此城市内有多少结果符合要求。
     *
     * 当您的keywords和types都是空时，默认指定types为120000（商务住宅）&150000（交通设施服务）
     */
    private String types;
    /**
     * 可选值：城市中文、中文全拼、citycode、adcode
     *
     * 如：北京/beijing/010/110000
     *
     * 填入此参数后，会尽量优先返回此城市数据，但是不一定仅局限此城市结果，若仅需要某个城市数据请调用citylimit参数。
     *
     * 如：在深圳市搜天安门，返回北京天安门结果。
     */
    private String city;
    /**
     * 仅返回指定城市数据
     */
    private Boolean cityLimit;
    /**
     * 是否按照层级展示子POI数据
     * 可选值：children=1
     *
     * 当为0的时候，子POI都会显示。
     *
     * 当为1的时候，子POI会归类到父POI之中。
     *
     *
     *
     * 仅在extensions=all的时候生效
     */
    private Integer children;
    /**
     * 每页记录数据
     * 强烈建议不超过25，若超过25可能造成访问报错
     */
    private Integer offset;
    /**
     * 当前页数
     * 最大翻页数100
     */
    private volatile Integer page;
    /**
     * 返回结果控制
     * 此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息。
     */
    private String extensions;
    /**
     * 数字签名
     */
    private String sig;
    /**
     * 返回数据格式类型
     */
    private ResponseContentTypeEnum output;
    /**
     * 回调函数
     * callback值是用户定义的函数名称，此参数只在output=JSON时有效
     */
    private String callback;

    public Map<String, Object> getParameters() {
        try {
            return ReflectUtils.getBeanDeclaredFields(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("获取参数失败");
        }
    }

    public MediaTypeEnum getMediaType() {
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private String key;
        private String keyWords;
        private String types;
        private String city;
        private Boolean cityLimit;
        private Integer children;
        private Integer offset;
        private Integer page;
        private String extensions;
        private String sig;
        private ResponseContentTypeEnum output;
        private String callback;

        public Builder url(String url) {
            this.url = url;
            return this;
        }
        public Builder key(String key) {
            this.key = key;
            return this;
        }
        public Builder keyWords(String keyWords) {
            this.keyWords = keyWords;
            return this;
        }

        public Builder types(String types) {
            this.types = types;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder cityLimit(Boolean cityLimit) {
            this.cityLimit = cityLimit;
            return this;
        }

        public Builder children(Integer children) {
            this.children = children;
            return this;
        }
        public Builder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder extensions(String extensions) {
            this.extensions = extensions;
            return this;
        }

        public Builder sig(String sig) {
            this.sig =sig;
            return this;
        }

        public Builder output(ResponseContentTypeEnum output) {
            this.output = output;
            return this;
        }

        public Builder callback(String callback) {
            this.callback = callback;
            return this;
        }

        public GodWebPoiWebPlatformApi build() {
//            Preconditions.checkArgument(! Strings.isNullOrEmpty(this.url), "url不能为空");
            Preconditions.checkArgument(! Strings.isNullOrEmpty(this.key), "key不能为空");
            Preconditions.checkArgument(! Strings.isNullOrEmpty(this.keyWords) || ! Strings.isNullOrEmpty(this.types), "keywords和types必须要填一个");
            Preconditions.checkArgument(Objects.isNull(this.children) || "all".equalsIgnoreCase(this.extensions), "children只在extensions为all的时候有效");
            Preconditions.checkArgument(Objects.isNull(this.offset) || this.offset <= 25, "offset最大值为25");
            Preconditions.checkArgument(Objects.isNull(this.callback) || ResponseContentTypeEnum.JSON == this.output, "callback只有在output为JSON时有效");

            final GodWebPoiWebPlatformApi result = new GodWebPoiWebPlatformApi();

            result.setUrl(this.url);
            result.setKey(this.key);
            result.setKeyWords(this.keyWords);
            result.setTypes(this.types);
            result.setCity(this.city);
            result.setCityLimit(this.cityLimit);
            result.setChildren(this.children);
            result.setOffset(this.offset);
            result.setPage(this.page);
            result.setExtensions(this.extensions);
            result.setSig(this.sig);
            result.setOutput(this.output);
            result.setCallback(this.callback);
            return result;
        }
    }
}
