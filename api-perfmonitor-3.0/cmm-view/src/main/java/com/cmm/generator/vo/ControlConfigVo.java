package com.cmm.generator.vo;

/**
 * TODO 控制器类配置
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 14:36
 */
public class ControlConfigVo {
    // 是否开启注解生成
    private boolean isParamGenerator;
    // 是否开启代码规范目录生成
    private boolean isCodeFileGenerator;
    // 是否开启规格代码生成
    private boolean isCodeGenerator;

    public boolean isCodeGenerator() {
        return isCodeGenerator;
    }

    public void setCodeGenerator(boolean codeGenerator) {
        isCodeGenerator = codeGenerator;
    }

    public boolean isParamGenerator() {
        return isParamGenerator;
    }

    public void setParamGenerator(boolean paramGenerator) {
        isParamGenerator = paramGenerator;
    }

    public boolean isCodeFileGenerator() {
        return isCodeFileGenerator;
    }

    public void setCodeFileGenerator(boolean codeFileGenerator) {
        isCodeFileGenerator = codeFileGenerator;
    }
}