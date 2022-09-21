package com.cmm.generator.template;

/**
 * @author caomm
 * @Title 实现类模板
 * @Description TODO
 * @Date
 */
public class ClassImplMethodTemplate {
    public final static String classImplTemplateMethodName = "" +
            "     /**\n" +
            "     * @Title ${title}\n" +
            "     * @author ${author}\n" +
            "     * @Description ${title}\n" +
            "     * @Date ${time}\n" +
            "     */\n" +
            "    ${param_check_concent}\n" +
            "    public DWEAIResult ${method_name}(Map<String, Object> headers, Map<String, Object> messageBody) throws Exception {\n" +
            "        DWEAIResult dweaiResult = new DWEAIResult();\n" +
            "        dweaiResult.setCode(\"0\");\n" +
            "        Map<String, Object> stdData = (Map<String, Object>) messageBody.get(\"std_data\");\n" +
            "        Map para = (Map) stdData.get(\"parameter\");\n" +
            "        if (para != null) {\n" +
            "            return this.do${method_name}(headers, para, dweaiResult);\n" +
            "        }\n" +
            "        return dweaiResult;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * @author ${author}\n" +
            "     * @Title ${title}\n" +
            "     * @Description ${title}\n" +
            "     * @Date ${time}\n" +
            "     */\n" +
            "    private DWEAIResult do${method_name}(Map<String, Object> headers, Map param, DWEAIResult dweaiResult) throws Exception {\n" +
            "        // =============================== API业务实现逻辑 ===================================\n" +
            "        \n" +
            "        return dweaiResult;\n" +
            "    }";

}
