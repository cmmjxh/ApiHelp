package com.cmm.generator.template;

/**
 * @author caomm
 * @Title 接口方法模板
 * @Description TODO
 * @Date 2022/2/9 15:12
 */
public class InterfactDataMethodTemplate {
   public final static String InterfactDataMethod="    /**\n" +
           "     * @Title ${title}\n" +
           "     * @Author ${author}\n" +
           "     * @Date ${time}\n" +
           "     * */\n" +
           "    @EAIService(id=\"${server_name}\")\n" +
           "    public DWEAIResult ${method_name}(Map<String, Object> headers, Map<String, Object> messageBody) throws Exception;";
}
