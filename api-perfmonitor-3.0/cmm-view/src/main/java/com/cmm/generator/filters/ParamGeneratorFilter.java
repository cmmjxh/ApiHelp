package com.cmm.generator.filters;

import com.cmm.generator.DWAbstractGeneratorFilter;
import com.cmm.generator.handler.ParamAnnotationgGeneratorHandler;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 参数校验注解生成器
 *
 * @version 1.0
 * @author: Survivor
 * @createTime: 2022/6/29$ 14:46$
 */
public class ParamGeneratorFilter extends DWAbstractGeneratorFilter {
    private final static Log log = LogFactory.getLog(ParamGeneratorFilter.class);

    @Override
    public void doExecute(GlobalConfigVo globalConfigVo, Object object , Chain chain) throws Exception {
        log.info("参数校验注解-------------------生成中");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"参数校验注解-------------------生成中");
        ParamAnnotationgGeneratorHandler paramAnnotationgGeneratorHandler =new ParamAnnotationgGeneratorHandler();
        String annotationStr = paramAnnotationgGeneratorHandler.execute(globalConfigVo,object);
        log.info("================================注解生成内容================================");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"================================注解生成内容================================");
        log.info(annotationStr);
        LogOutQueue.add(annotationStr);
        chain.doFilter(object);
    }
}