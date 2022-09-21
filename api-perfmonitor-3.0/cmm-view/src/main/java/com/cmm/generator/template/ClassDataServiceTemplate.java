package com.cmm.generator.template;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/2/9 15:23
 */
public class ClassDataServiceTemplate {
    public final static String ClassData="\n" +
            "import com.digiwin.app.service.DWEAIResult;\n" +
            "import com.digiwin.ema.bomcompare.service.ICompareBomService;\n" +
            "import com.digiwin.ema.bomcompare.service.impl.business.*;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * @Title TODO\n" +
            " * @author \n" +
            " * @Description  TODO\n" +
            " * @Date 2022/1/5 17:56\n" +
            " */\n" +
            "@Service\n" +
            "public class DefaultService implements IDefaultService {\n" +
            "\n" +
            "    @Autowired\n" +
            "    ${impl_name} ${method_name};\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * @Title ${title}\n" +
            "     * */\n" +
            "    @Override\n" +
            "    public DWEAIResult ${method_name}(Map<String, Object> headers, Map<String, Object> messageBody) throws Exception {\n" +
            "        return ${method_name}.${method_name}(headers,messageBody);\n" +
            "    }\n" +
            "}";

    public final static String ClassDataImplTemplate = "\n" +
            "import java.text.ParseException;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.Date;\n" +
            "import java.util.List;\n" +
            "import java.util.Map;\n" +
            "/**\n" +
            " * @author\n" +
            " * @Title TODO\n" +
            " * @Description TODO\n" +
            " * @Date\n" +
            " */\n" +
            "@Service\n" +
            "public class ${impl_name} {\n" +
            "\n" +
            "    /**\n" +
            "     * @Description ${title}\n" +
            "     */\n" +
            "    ${annocation} \n" +
            "    public DWEAIResult ${method_name}(Map<String, Object> headers, Map<String, Object> messageBody) throws Exception {\n" +
            "        DWEAIResult dweaiResult = new DWEAIResult();\n" +
            "        dweaiResult.setCode(\"0\");\n" +
            "        Map<String, Object> stdData = (Map<String, Object>) messageBody.get(\"std_data\");\n" +
            "        Map para = (Map) stdData.get(\"parameter\");\n" +
            "        if (para != null) {\n" +
            "            this.do${impl_name}(headers, para, dweaiResult);\n" +
            "        }\n" +
            "        return dweaiResult;\n" +
            "    }\n" +
            "\n" +
            "    private void do${impl_name}(Map<String, Object> headers, Map param, DWEAIResult dweaiResult) {\n" +
            "       //业务逻辑实现\n" +
            "        \n" +
            "    }\n" +
            "    \n" +
            "}\n";

    public final static String ClassDataImplMethodTemplate ="\n" +
            "    /**\n" +
            "     * @Description ${title}\n" +
            "     */\n" +
            "    ${annocation} \n" +
            "    public DWEAIResult ${method_name}(Map<String, Object> headers, Map<String, Object> messageBody) throws Exception {\n" +
            "        DWEAIResult dweaiResult = new DWEAIResult();\n" +
            "        dweaiResult.setCode(\"0\");\n" +
            "        Map<String, Object> stdData = (Map<String, Object>) messageBody.get(\"std_data\");\n" +
            "        Map para = (Map) stdData.get(\"parameter\");\n" +
            "        if (para != null) {\n" +
            "            this.do${impl_name}(headers, para, dweaiResult);\n" +
            "        }\n" +
            "        return dweaiResult;\n" +
            "    }\n" +
            "\n" +
            "    private void do${impl_name}(Map<String, Object> headers, Map param, DWEAIResult dweaiResult) {\n" +
            "       /*业务逻辑*/\n" +
            "        \n" +
            "    }\n" ;
}
