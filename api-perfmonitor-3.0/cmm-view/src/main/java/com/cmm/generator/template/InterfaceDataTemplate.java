package com.cmm.generator.template;

/**
 * @author caomm
 * @Title 接口类模板
 * @Description TODO
 * @Date
 */
public class InterfaceDataTemplate {
    public final static String INameService = "\n" +
            "import com.digiwin.app.service.DWEAIResult;\n" +
            "import com.digiwin.app.service.DWService;\n" +
            "import com.digiwin.app.service.eai.EAIService;\n" +
            "\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * @Title \n" +
            " * @author ${author}\n" +
            " * @Description \n" +
            " * @Date ${time}\n" +
            " */\n" +
            "public interface ${interfactClassName} extends DWService {\n" +
            "\n" +
            "}\n";

}
