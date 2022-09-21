package com.cmm.generator.handler;

import com.cmm.generator.handler.dohander.CodeGeneratorDoHander;
import com.cmm.generator.vo.ApiInfoVo;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.ConfigUtils;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;

/**
 * 代码生成器
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 13:52
 * @ps 如果即开启了 代码生成器 也 开启了 注解生成器，则此处会直接生成注解，同时屏蔽注解生成器执行
 */
public class CodeGeneratorHandler {
    final static String business = ConfigUtils.getConfig("business");

    public boolean execute(GlobalConfigVo globalConfigVo, Object obj) {
        CodeGeneratorDoHander codeGeneratorDoHander = new CodeGeneratorDoHander();
        try {
            ApiInfoVo apiInfoVo = (ApiInfoVo) obj;
            // 接口类创建
            codeGeneratorDoHander.createInterfaceFile(globalConfigVo, apiInfoVo);
            // 控制类创建
            codeGeneratorDoHander.createControllerFile(globalConfigVo, apiInfoVo);
            // 实现类创建
            codeGeneratorDoHander.createServiceFile(business, globalConfigVo, apiInfoVo);

        } catch (Exception e) {
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"代码文件生成失败！"+e);
            throw new Error("代码文件生成失败！"+e);
        }
        return true;
    }

}