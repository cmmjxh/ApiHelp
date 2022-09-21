package com.cmm.generator.handler.dohander;

import com.cmm.generator.handler.ParamAnnotationgGeneratorHandler;
import com.cmm.generator.template.*;
import com.cmm.generator.vo.ApiInfoVo;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.FileUtils;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;

import java.io.File;
import java.io.IOException;

/**
 * TODO 代码生成器具体实现
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/7/1 15:09
 */
public class CodeGeneratorDoHander {

    /**
     * 接口类创建
     *
     * @param globalConfigVo 配置
     * @param apiInfoVo      api信息
     */
    public void createInterfaceFile(GlobalConfigVo globalConfigVo, ApiInfoVo apiInfoVo) throws IOException {
        try {
            // 生成Interface文件目录
            String CodePathInterface = globalConfigVo.getPathConfigVo().getCodePathInterface();
            File file = new File(CodePathInterface);
            if (!file.exists()) {
                file.mkdir();
            }
            // 获取接口信息
            String methodName = FileUtils.doServerName(apiInfoVo.getApiName());
            String interfaceData = "";

            File fileIs = new File(file + File.separator + apiInfoVo.getInterfaceClassName() + ".java");
            String fileTemp = file + File.separator + apiInfoVo.getInterfaceClassName() + "_temp.java";
            // 如果不存在文件，则创建并写入内容（包括文件夹）
            if(!fileIs.exists()){
                interfaceData = InterfaceDataTemplate.INameService.replace("${server_name}", apiInfoVo.getApiName())
                        .replace("${interfactClassName}", apiInfoVo.getInterfaceClassName())
                        .replace("${method_name}", methodName)
                        .replace("${title}", apiInfoVo.getDescription())
                        .replace("${author}", apiInfoVo.getAuthor())
                        .replace("${time}", apiInfoVo.getTime());
                FileUtils.createFileAndData(fileIs,interfaceData);
                // 如果存在API则同时写入
                if(apiInfoVo.getApiName()!=null || !apiInfoVo.getApiName().isEmpty()){
                    // 追加写入
                    interfaceData = InterfactDataMethodTemplate.InterfactDataMethod.replace("${server_name}", apiInfoVo.getApiName())
                            .replace("${method_name}", methodName)
                            .replace("${title}", apiInfoVo.getDescription())
                            .replace("${author}", apiInfoVo.getAuthor())
                            .replace("${time}", apiInfoVo.getTime());
                    FileUtils.write(fileIs,interfaceData,fileTemp);
                }
            }else{
                if(apiInfoVo.getApiName()!=null || !apiInfoVo.getApiName().isEmpty()){
                    // 追加写入
                    interfaceData = InterfactDataMethodTemplate.InterfactDataMethod.replace("${server_name}", apiInfoVo.getApiName())
                            .replace("${method_name}", methodName)
                            .replace("${title}", apiInfoVo.getDescription())
                            .replace("${author}", apiInfoVo.getAuthor())
                            .replace("${time}", apiInfoVo.getTime());
                    FileUtils.write(fileIs,interfaceData,fileTemp);
                }
            }
        } catch (Exception e) {
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"代码文件生成失败！"+e);
            throw new Error("代码文件生成失败！"+e);
        }
    }

    /**
     * 控制类创建
     *
     * @param globalConfigVo 配置
     * @param apiInfoVo      api信息
     */
    public void createControllerFile(GlobalConfigVo globalConfigVo, ApiInfoVo apiInfoVo) {
        ParamAnnotationgGeneratorHandler paramAnnotationgGeneratorHandler = new ParamAnnotationgGeneratorHandler();
        try {
            // 生成controller文件目录
            String CodePathController = globalConfigVo.getPathConfigVo().getCodePathImpl();
            String annocationStr = "";
            String interfaceData = "";
            File file = new File(CodePathController);
            if (!file.exists()) {
                file.mkdir();
            }
            // 获取接口信息
            String methodName = FileUtils.doServerName(apiInfoVo.getApiName());
            // 生成注解
            try {
                annocationStr = paramAnnotationgGeneratorHandler.execute(globalConfigVo,apiInfoVo);
            }catch (Exception e){};

            File fileIs = new File(file + File.separator + apiInfoVo.getControllerClassName() + ".java");
            String fileTemp = file + File.separator + apiInfoVo.getControllerClassName() + "_temp.java";
            // 如果不存在文件，则创建并写入内容（包括文件夹）
            if(!fileIs.exists()){
                interfaceData = ControllerDataTemplate.ControllerTemplateName.replace("${prodect}", apiInfoVo.getProductName())
                        .replace("${className}", apiInfoVo.getControllerClassName())
                        .replace("${interfaceName}", apiInfoVo.getInterfaceClassName())
                        .replace("${title}", apiInfoVo.getDescription())
                        .replace("${author}", apiInfoVo.getAuthor())
                        .replace("${time}", apiInfoVo.getTime());
                FileUtils.createFileAndData(fileIs,interfaceData);
                // 如果存在API则同时写入
                if(apiInfoVo.getApiName()!=null || !apiInfoVo.getApiName().isEmpty()){
                    // 追加写入
                    interfaceData = ControllerDataMethodTemplate.ControllerMethodTemplateName
                            .replace("${serviceClassName}", apiInfoVo.getServiceClassName())
                            .replace("${serviceClassNameChild}", FileUtils.lowerFirstCase(apiInfoVo.getServiceClassName()))
                            .replace("${method_name}", methodName)
                            .replace("${title}", apiInfoVo.getDescription())
                            .replace("${author}", apiInfoVo.getAuthor())
                            .replace("${time}", apiInfoVo.getTime())
                            .replace("${param_check}", annocationStr);
                    FileUtils.write(fileIs,interfaceData,fileTemp);
                }
            }else{
                if(apiInfoVo.getApiName()!=null || !apiInfoVo.getApiName().isEmpty()){
                    // 追加写入
                    interfaceData = ControllerDataMethodTemplate.ControllerMethodTemplateName
                            .replace("${serviceClassName}", apiInfoVo.getServiceClassName())
                            .replace("${serviceClassNameChild}", FileUtils.lowerFirstCase(apiInfoVo.getServiceClassName()))
                            .replace("${method_name}", methodName)
                            .replace("${title}", apiInfoVo.getDescription())
                            .replace("${author}", apiInfoVo.getAuthor())
                            .replace("${time}", apiInfoVo.getTime())
                            .replace("${param_check}", annocationStr);
                    FileUtils.write(fileIs,interfaceData,fileTemp);
                }
            }
        } catch (Exception e) {
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"代码文件生成失败！"+e);
            throw new Error("代码文件生成失败！"+e);
        }
    }

    /**
     * 实现类创建
     *
     * @param business     实现类类目录
     * @param globalConfigVo 配置
     * @param apiInfoVo      api信息
     */
    public void createServiceFile(String business, GlobalConfigVo globalConfigVo, ApiInfoVo apiInfoVo) {
        try {
            // 生成实现类文件目录
            String CodePathService = globalConfigVo.getPathConfigVo().getCodePathImpl();
            File file = new File(CodePathService + File.separator + business);
            if (!file.exists()) {
                file.mkdir();
            }
            // 获取接口信息
            String methodName = FileUtils.doServerName(apiInfoVo.getApiName());
            String interfaceData = "";

            File fileIs = new File(file + File.separator + apiInfoVo.getServiceClassName() + ".java");
            String fileTemp = file + File.separator + apiInfoVo.getServiceClassName() + "_temp.java";
            // 如果不存在文件，则创建并写入内容（包括文件夹）
            if(!fileIs.exists()){
                interfaceData = ClassImplTemplate.classImplTemplateName
                        .replace("${class_impl_name}", apiInfoVo.getServiceClassName())
                        .replace("${class_dao_name}", apiInfoVo.getDaoClassName())
                        .replace("${class_dao_name_child}", FileUtils.lowerFirstCase(apiInfoVo.getDaoClassName()))
                        .replace("${product}", apiInfoVo.getProductName())
                        .replace("${author}", apiInfoVo.getAuthor())
                        .replace("${time}", apiInfoVo.getTime());
                FileUtils.createFileAndData(fileIs,interfaceData);
                // 如果存在API则同时写入
                if(apiInfoVo.getApiName()!=null || !apiInfoVo.getApiName().isEmpty()){
                    // 追加写入
                    interfaceData = ClassImplMethodTemplate.classImplTemplateMethodName
                            .replace("${param_check_concent}", "")
                            .replace("${method_name}", methodName)
                            .replace("${title}", apiInfoVo.getDescription())
                            .replace("${author}", apiInfoVo.getAuthor())
                            .replace("${time}", apiInfoVo.getTime());
                    FileUtils.write(fileIs,interfaceData,fileTemp);
                }
            }else{
                if(apiInfoVo.getApiName()!=null || !apiInfoVo.getApiName().isEmpty()){
                    // 追加写入
                    interfaceData = ClassImplMethodTemplate.classImplTemplateMethodName
                            .replace("${param_check_concent}", "")
                            .replace("${method_name}", methodName)
                            .replace("${title}", apiInfoVo.getDescription())
                            .replace("${author}", apiInfoVo.getAuthor())
                            .replace("${time}", apiInfoVo.getTime());
                    FileUtils.write(fileIs,interfaceData,fileTemp);
                }
            }
        } catch (Exception e) {
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"代码文件生成失败！"+e);
            throw new Error("代码文件生成失败！"+e);
        }
    }
}