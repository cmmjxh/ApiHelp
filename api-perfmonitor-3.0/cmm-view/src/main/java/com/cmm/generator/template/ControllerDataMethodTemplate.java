package com.cmm.generator.template;

/**
 * @author caomm
 * @Title 控制类模板
 * @Description TODO
 * @Date
 */
public class ControllerDataMethodTemplate {
    public final static String ControllerMethodTemplateName = "    /**\n" +
            "     * @author ${author}\n" +
            "     * @date ${time}\n" +
            "     * Description: ${title}\n" +
            "     */\n" +
            "    @Autowired\n" +
            "    private ${serviceClassName} ${serviceClassNameChild};\n\n" +
            "    ${param_check}\n" +
            "    @Override\n" +
            "    public DWEAIResult ${method_name}(Map<String, Object> headers, Map<String, Object> messageBody) throws Exception {\n" +
            "         return ${serviceClassNameChild}.${method_name}(headers, messageBody);\n" +
            "    }";

}
