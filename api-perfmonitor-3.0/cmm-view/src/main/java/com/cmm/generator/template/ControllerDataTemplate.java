package com.cmm.generator.template;

/**
 * @author caomm
 * @Title 控制类模板
 * @Description TODO
 * @Date
 */
public class ControllerDataTemplate {
    public final static String ControllerTemplateName = "" +
            "\n" +
            "import com.digiwin.app.service.DWEAIResult;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "\n" +
            "import java.util.List;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * @Title ${prodect}应用API控制类\n" +
            " * @Author ${author}\n" +
            " * @Date ${time}\n" +
            " * @Description ${prodect}应用API控制类\n" +
            " */\n" +
            "public class ${className} implements ${interfaceName} {\n" +
            "\n" +
            "}";

}
