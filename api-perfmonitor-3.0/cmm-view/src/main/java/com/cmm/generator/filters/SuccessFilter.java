package com.cmm.generator.filters;

import com.cmm.generator.DWAbstractGeneratorFilter;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 成功结束
 *
 * @version 1.0
 * @author: Survivor
 * @createTime: 2022/6/29$ 14:46$
 */
public class SuccessFilter extends DWAbstractGeneratorFilter {
    private final static Log log = LogFactory.getLog(SuccessFilter.class);

    @Override
    public void doExecute(GlobalConfigVo globalConfigVo, Object object, Chain chain) throws Exception {
        log.info("：程序结束");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+":程序结束");
        chain.doFilter(object);
    }
}