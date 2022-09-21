package com.cmm.generator.filters;

import com.cmm.generator.DWAbstractGeneratorFilter;
import com.cmm.generator.handler.ConfigInfoCheckHandler;
import com.cmm.generator.vo.GlobalConfigVo;
import com.cmm.utils.LogOutQueue;
import com.cmm.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 配置信息检查器
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/29$ 14:46$
 */
public class ConfigInfoCheckFilter extends DWAbstractGeneratorFilter {
    private final static Log log = LogFactory.getLog(ConfigInfoCheckFilter.class);

    @Override
    public void doExecute(GlobalConfigVo globalConfigVo,Object object , Chain chain) throws Exception {
        log.info("配置信息检查-------------------检查中");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"配置信息检查-------------------检查中");
        ConfigInfoCheckHandler configInfoCheckHandler = new ConfigInfoCheckHandler();
        configInfoCheckHandler.check(globalConfigVo);
        log.info("配置信息检查-------------------检查通过");
        LogOutQueue.add(TimeUtils.newTime()+" 信息: "+"配置信息检查-------------------检查通过");
        chain.doFilter(object);
    }
}