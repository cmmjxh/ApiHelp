package com.cmm.generator.handler;

import com.cmm.generator.vo.ApiInfoVo;
import com.cmm.generator.vo.GlobalConfigVo;

import java.util.List;
import java.util.Map;

/**
 * 入参检查注解生成器
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 13:52
 */
public class ParamAnnotationgGeneratorHandler {

    /**
     * 入参检查注解生成器
     */
    public String execute(GlobalConfigVo globalConfigVo, Object obj) {
        ApiInfoVo apiInfoVo = (ApiInfoVo) obj;
        List<Map<String, Object>> apiBody = apiInfoVo.getApiBody();
        String annotationStr = "";
        if (apiBody != null && apiBody.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            doGenerator(stringBuilder, apiBody, "");
            if (stringBuilder.length() > 0) {
                annotationStr = stringBuilder.substring(0,stringBuilder.length()-2)+"\n";
                annotationStr = "@DWParamsCheckI(checks = {\n"+annotationStr+"\t})";
            }
        }
        return annotationStr;
    }

    private void doGenerator(StringBuilder stringBuilder, List<Map<String, Object>> apiBody, String parent) {
        if (apiBody.size() > 0) {
            for (Map m : apiBody) {
                String dataName = (String) m.get("data_name");
                if (parent != null && !parent.isEmpty()) {
                    dataName = parent + "." + (String) m.get("data_name");
                }

                String dataType = (String) m.get("data_type");
                boolean required = (boolean) m.get("required");
                boolean isArray = (boolean) m.get("is_array");
                    if ("object".equals(dataType)) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) m.get("field");
                    // 必传处理
                    if (required) {
                        stringBuilder.append("\t\t@Signature(id = \"NOTEMPTY_CHECKER\", path = \"" + dataName + "\"),\n");
                    }
                    // 数组处理
                    if (isArray) {
                        stringBuilder.append("\t\t@Signature(id = \"TYPE_CHECK\", path = \"" + dataName + "\", values = {\"List\"}),\n");
                    }
                    doGenerator(stringBuilder, list, dataName);
                } else {
                    // 必传处理
                    if (required) {
                        stringBuilder.append("\t\t@Signature(id = \"NOTEMPTY_CHECKER\", path = \"" + dataName + "\"),\n");
                    }
                    // 数组处理
                    if (isArray) {
                        stringBuilder.append("\t\t@Signature(id = \"TYPE_CHECK\", path = \"" + dataName + "\", values = {\"List\"}),\n");
                    }
                }
            }
        }
    }
}