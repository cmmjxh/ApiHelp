package com.cmm.generator.template;

/**
 * @author caomm
 * @Title 实现类模板
 * @Description TODO
 * @Date
 */
public class ClassImplTemplate {
    public final static String classImplTemplateName = "\n" +
            "import com.digiwin.app.dao.DWDao;\n" +
            "import com.digiwin.app.service.DWEAIResult;\n" +
            "import org.apache.commons.logging.Log;\n" +
            "import org.apache.commons.logging.LogFactory;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.beans.factory.annotation.Qualifier;\n" +
            "import org.springframework.stereotype.Component;\n" +
            "\n" +
            "import java.util.Map;" +
            "/**\n" +
            " * @Title ***实现类\n" +
            " * @author ${author}\n" +
            " * @Description \n" +
            " * @Date ${time}\n" +
            " */\n" +
            "@Component\n" +
            "public class ${class_impl_name} {\n" +
            "    private final static Log log = LogFactory.getLog(${class_impl_name}.class);\n" +
            "\n" +
            "    @Autowired\n" +
            "    @Qualifier(\"dw-dao\")\n" +
            "    private DWDao dwDao;\n" +
            "    \n" +
            "    @Autowired\n" +
            "    private ${class_dao_name} ${class_dao_name_child};\n" +
            "    \n" +
            "}\n";

}
