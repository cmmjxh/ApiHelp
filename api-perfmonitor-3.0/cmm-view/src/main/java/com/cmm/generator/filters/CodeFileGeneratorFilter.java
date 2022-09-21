package com.cmm.generator.filters;

import com.cmm.generator.DWAbstractGeneratorFilter;
import com.cmm.generator.handler.CodeFileGeneratorHandler;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 代码规格目录生成器
 *
 * @version 1.0
 * @author: Survivor
 * @createTime: 2022/6/29$ 14:46$
 */
public class CodeFileGeneratorFilter extends DWAbstractGeneratorFilter {
    private final static Log log = LogFactory.getLog(CodeFileGeneratorFilter.class);

    @Override
    public void doExecute(GlobalConfigVo globalConfigVo, Object object, Chain chain) throws Exception {
        log.info("代码规格目录-------------------创建中...");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"代码规格目录-------------------创建中...");
        CodeFileGeneratorHandler codeFileGeneratorHandler = new CodeFileGeneratorHandler();
        codeFileGeneratorHandler.execute(globalConfigVo);
        log.info("代码规格目录-------------------创建完成");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"代码规格目录-------------------创建完成");
        chain.doFilter(object);
    }
}