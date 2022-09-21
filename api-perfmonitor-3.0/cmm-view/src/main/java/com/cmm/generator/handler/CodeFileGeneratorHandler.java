package com.cmm.generator.handler;

import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.ConfigUtils;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

/**
 * 配置解析检测处理器
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 13:52
 */
public class CodeFileGeneratorHandler {
    private final static Log log = LogFactory.getLog(CodeFileGeneratorHandler.class);
    final static String business = ConfigUtils.getConfig("business");
    final static String businessEntityDomain = ConfigUtils.getConfig("business_entity_domain");
    final static String businessEntityRequestDTO = ConfigUtils.getConfig("business_entity_requestDTO");
    final static String businessRntityResponseDTO = ConfigUtils.getConfig("business_entity_responseDTO");
    final static String businessManager = ConfigUtils.getConfig("business_manager");
    final static String businessMapper = ConfigUtils.getConfig("business_mapper");
    final static String commonConstant = ConfigUtils.getConfig("common_constant");
    final static String commonEnum = ConfigUtils.getConfig("common_enum");
    final static String commonUtils = ConfigUtils.getConfig("common_utils");

    public boolean execute(GlobalConfigVo globalConfigVo){
        try {
            // 获取配置文件目录
            String codePathImpl = globalConfigVo.getPathConfigVo().getCodePathImpl();
            String CodePathInterface = globalConfigVo.getPathConfigVo().getCodePathInterface();
            // 生成business以及相关文件目录
            createFileDir(codePathImpl + File.separator + business);
            createFileDir(codePathImpl + File.separator + businessEntityDomain);
            createFileDir(codePathImpl + File.separator + businessEntityRequestDTO);
            createFileDir(codePathImpl + File.separator + businessRntityResponseDTO);
            createFileDir(codePathImpl + File.separator + businessManager);
            createFileDir(codePathImpl + File.separator + businessMapper);

            //common层目录
            createFileDir(codePathImpl + File.separator + commonConstant);
            createFileDir(codePathImpl + File.separator + commonEnum);
            createFileDir(codePathImpl + File.separator + commonUtils);

        }catch (Exception e){
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"规格文件目录生成失败！"+e);
            throw new Error("规格文件目录生成失败！"+e);
        }
        return true;
    }

    /**
     *  创建文件目录file
     * */
    private void createFileDir(String filePath) {
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}