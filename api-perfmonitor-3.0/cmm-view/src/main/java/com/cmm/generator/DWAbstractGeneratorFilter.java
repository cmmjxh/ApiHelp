package com.cmm.generator;

import com.cmm.generator.vo.GlobalConfigVo;
import jdk.Exported;

import java.util.LinkedList;

/**
 * 重写过滤器
 * 实现自定义消息传递，通过chain.doFilter()实现链路节点执行
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/29$ 14:42$
 */
public abstract class DWAbstractGeneratorFilter {
    public abstract void doExecute(GlobalConfigVo globalConfigVo,Object object, Chain chain) throws Exception;

    @Exported
    public static class Chain {
        private LinkedList<DWAbstractGeneratorFilter> iter;
        private Object obj;
        private GlobalConfigVo globalConfigVo;

        public Chain(GlobalConfigVo globalConfigVo,LinkedList<DWAbstractGeneratorFilter> var1, Object message) {
            this.iter = var1;
            this.obj = message;
            this.globalConfigVo = globalConfigVo;
        }

        public void doFilter(Object obj) throws Exception {
            if (this.iter.peek() != null) {
                DWAbstractGeneratorFilter var2 = (DWAbstractGeneratorFilter) this.iter.poll();
                var2.doExecute(globalConfigVo,obj , this);
            }

        }
    }
}