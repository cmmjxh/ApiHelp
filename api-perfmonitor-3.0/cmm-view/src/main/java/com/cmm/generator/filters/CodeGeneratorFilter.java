package com.cmm.generator.filters;

import com.cmm.generator.DWAbstractGeneratorFilter;
import com.cmm.generator.handler.CodeGeneratorHandler;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 规格代码文件生成器
 *
 * @version 1.0
 * @author: Survivor
 * @createTime: 2022/6/29$ 14:46$
 */
public class CodeGeneratorFilter extends DWAbstractGeneratorFilter {
    private final static Log log = LogFactory.getLog(CodeGeneratorFilter.class);

    @Override
    public void doExecute(GlobalConfigVo globalConfigVo, Object object, Chain chain) throws Exception {
        log.info("规格代码文件-------------------创建中...");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"规格代码文件-------------------创建中...");
        CodeGeneratorHandler codeGeneratorHandler = new CodeGeneratorHandler();
        codeGeneratorHandler.execute(globalConfigVo,object);
        log.info("规格代码文件-------------------创建结束");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"规格代码文件-------------------创建结束");
        chain.doFilter(object);
    }
}