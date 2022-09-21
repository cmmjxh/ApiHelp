package com.cmm.generator.handler;

import com.cmm.generator.GeneratorChain;
import com.cmm.generator.filters.CodeFileGeneratorFilter;
import com.cmm.generator.filters.CodeGeneratorFilter;
import com.cmm.generator.filters.ParamGeneratorFilter;
import com.cmm.generator.vo.ControlConfigVo;
import com.cmm.generator.vo.DescriptionConfigVo;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.generator.vo.PathConfigVo;
import com.cmm.utils.ConfigUtils;
import com.cmm.utils.FileUtils;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 配置解析检测处理器
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 13:52
 */
public class ConfigInfoCheckHandler {
    final static String API_URL = ConfigUtils.getConfig("ApiUrl");
    final static String CODEPATHIMPL = ConfigUtils.getConfig("CodePathImpl");
    final static String CODEPATHINTERFACE = ConfigUtils.getConfig("CodePathInterface");
    private final static Log log = LogFactory.getLog(ConfigInfoCheckHandler.class);

    /**
     * 配置检查
     */
    public void check(GlobalConfigVo globalConfigVo) {
        PathConfigVo pathConfigVo = globalConfigVo.getPathConfigVo();
        ControlConfigVo controlConfigVo = globalConfigVo.getControlConfigVo();
        DescriptionConfigVo descriptionConfigVo = globalConfigVo.getDescriptionConfigVo();
        String menthodName = "";
        // ===========================================路径类==========================================
        if ((pathConfigVo.getApiUrl() == null || pathConfigVo.getApiUrl().isEmpty())) {
            if (API_URL != null && !API_URL.isEmpty()) {
                pathConfigVo.setApiUrl(API_URL);
            } else {
                String error = String.format("ApiUrl配置数据缺失");
                LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"ApiUrl配置数据缺失");
                throw new Error(error);
            }
        }
        if ((pathConfigVo.getCodePathImpl() == null || pathConfigVo.getCodePathImpl().isEmpty())) {
            if ((CODEPATHIMPL != null && !CODEPATHIMPL.isEmpty())) {
                pathConfigVo.setCodePathImpl(CODEPATHIMPL);
            } else {
                String error = String.format("控制模组路径配置缺失");
                LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"实现类路径配置缺失");
                throw new Error(error);

            }
        }

        if ((pathConfigVo.getCodePathImpl() == null || pathConfigVo.getCodePathImpl().isEmpty())) {
            if (CODEPATHINTERFACE != null && !CODEPATHINTERFACE.isEmpty()) {
                pathConfigVo.setCodePathInterface(CODEPATHINTERFACE);
            } else {
                String error = String.format("接口类模组路劲配置缺失");
                LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"接口类模组路劲配置缺失");
                throw new Error(error);
            }
        }
        // 如果api名称不为空则生成方法名
        if (pathConfigVo.getApiName() != null && !pathConfigVo.getApiName().isEmpty()) {
            menthodName = FileUtils.UpperFrist(FileUtils.doServerName(pathConfigVo.getApiName()));
        }
        // ===========================================控制器类==========================================
        // 若未false则关闭目录生成器
        if (!controlConfigVo.isCodeFileGenerator()) {
            GeneratorChain.remove(new CodeFileGeneratorFilter());
        }
        // 若未false则关闭代码生成器,开启时，检查是否配置API信息
        if (!controlConfigVo.isCodeGenerator()) {
            GeneratorChain.remove(new CodeGeneratorFilter());
        }else{
            if (pathConfigVo.getApiName() == null || pathConfigVo.getApiName().isEmpty() || "null".equals(pathConfigVo.getApiName())) {
                LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"API名称未设置");
                throw new Error("API名称未设置");
            }
        }
        // 若未false则关闭注解生成器
        if (!controlConfigVo.isParamGenerator()) {
            GeneratorChain.remove(new ParamGeneratorFilter());
        }else{
            if (pathConfigVo.getApiName() == null || pathConfigVo.getApiName().isEmpty() || "null".equals(pathConfigVo.getApiName())) {
                LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"API名称未设置");
                throw new Error("API名称未设置");
            }
        }
        //若未false则关闭出入参实体生成器

        // ===========================================描述类==========================================
        if ((descriptionConfigVo.getAuthor() == null || descriptionConfigVo.getAuthor().isEmpty())) {
            descriptionConfigVo.setAuthor("author");
        }
        if ((descriptionConfigVo.getProductName() == null || descriptionConfigVo.getProductName().isEmpty())) {
            String error = String.format("应用名称配置缺失");
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"应用名称配置缺失");
            throw new Error(error);
        }

        // 接口名称处理，默认应用名称
        if ((descriptionConfigVo.getInterfactClassName() == null || descriptionConfigVo.getInterfactClassName().isEmpty())) {
            descriptionConfigVo.setInterfactClassName("I" + descriptionConfigVo.getProductName() + "Service");
        }
        // 控制类名称处理，默认应用名称
        if ((descriptionConfigVo.getControllerClassName() == null || descriptionConfigVo.getControllerClassName().isEmpty())) {
            descriptionConfigVo.setControllerClassName(descriptionConfigVo.getProductName() + "Service");
        }
        // 实现类名称处理，默认应用名称
        if ((descriptionConfigVo.getServiceClassName() == null || descriptionConfigVo.getServiceClassName().isEmpty())) {
            descriptionConfigVo.setServiceClassName(menthodName + "Service");
        }
        // DAO名称处理，默认应用名称(Mapper)
        if ((descriptionConfigVo.getDaoClassName() == null || descriptionConfigVo.getDaoClassName().isEmpty())) {
            descriptionConfigVo.setDaoClassName(menthodName + "Mapper");
        }
    }
}