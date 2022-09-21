package com.cmm.view;

import com.cmm.generator.GeneratorChain;
import com.cmm.generator.vo.ControlConfigVo;
import com.cmm.generator.vo.DescriptionConfigVo;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.generator.vo.PathConfigVo;
import com.cmm.utils.LogOutQueue;

import javax.swing.*;

/**
 * @author caomm
 * @Title 代码生成器入口
 * @Description TODO
 * @Date 2022/7/6 14:31
 */
public class setConfig {
    public static void setConfigAndExe(String textFieldApiName, String textFieldInterfactPath, String textFieldImplPath, String textFieldProductName, String textFieldAuthor, String textFieldInterfactName, String textFieldControllerName, JCheckBox isFileGenerator, JCheckBox isCodeGenerator, JCheckBox isResPonEntity, JCheckBox isParamGenerator, JCheckBox isMapper) throws Exception {
        GlobalConfigVo globalConfigVo = new GlobalConfigVo();

        // 路径类配置
        PathConfigVo pathConfigVo = new PathConfigVo();
        pathConfigVo.setCodePathImpl(textFieldImplPath);
        pathConfigVo.setCodePathInterface(textFieldInterfactPath);

        // 控制器类配置
        ControlConfigVo controlConfigVo = new ControlConfigVo();
        // 是否开启文件目录生成
        controlConfigVo.setCodeFileGenerator(isFileGenerator.isSelected());
//        // 是否开启规格代码文件生成
        controlConfigVo.setCodeGenerator(isCodeGenerator.isSelected());
//        // 是否开启参数校验注解生成
        controlConfigVo.setParamGenerator(isParamGenerator.isSelected());

        // 描述类
        DescriptionConfigVo descriptionConfigVo = new DescriptionConfigVo();
        descriptionConfigVo.setAuthor(textFieldAuthor);
        descriptionConfigVo.setProductName(textFieldProductName);
        // 业务相关类配置，不配置则默认按照应用名称命名，不存在则创建类文件，存在则追加相关API信息
        pathConfigVo.setApiName(textFieldApiName);
        // 接口名称、默认当前应用名称
        descriptionConfigVo.setInterfactClassName(textFieldInterfactName);
        // 控制类名称、默认当前应用名称
        descriptionConfigVo.setControllerClassName(textFieldControllerName);
        // 实现类名称、默认当前方法名
        descriptionConfigVo.setServiceClassName("");
        // 持久层引入名、默认当前方法名Mapper
        descriptionConfigVo.setDaoClassName("");

        globalConfigVo.setPathConfigVo(pathConfigVo);
        globalConfigVo.setControlConfigVo(controlConfigVo);
        globalConfigVo.setDescriptionConfigVo(descriptionConfigVo);

        // 执行
        try {
            LogOutQueue.clear();
            GeneratorChain.doExecute(globalConfigVo);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
