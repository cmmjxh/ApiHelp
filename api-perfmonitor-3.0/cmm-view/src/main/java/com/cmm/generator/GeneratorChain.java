package com.cmm.generator;

import com.cmm.generator.filters.*;
import com.cmm.generator.vo.GlobalConfigVo;

import java.util.LinkedList;

/**
 * TODO 生成器处理链
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/29$ 14:31$
 */
public class GeneratorChain {
    private static LinkedList<DWAbstractGeneratorFilter> generatorChain = new LinkedList<>();

    /**
     * 添加过滤器
     */
    public static void add(DWAbstractGeneratorFilter DWAbstractGeneratorFilter) {
        generatorChain.add(DWAbstractGeneratorFilter);
    }

    /**
     * 移除过滤器
     */
    public static void remove(DWAbstractGeneratorFilter dwAbstractGeneratorFilter) {
        for(DWAbstractGeneratorFilter dwAbstractGeneratorFilterChilder : generatorChain){
             if(dwAbstractGeneratorFilterChilder.getClass() == dwAbstractGeneratorFilter.getClass()){
                 generatorChain.remove(dwAbstractGeneratorFilterChilder);
                 break;
             }
        }
    }

    /**
     * 查看链路
     */
    public static LinkedList<DWAbstractGeneratorFilter> get() {
        return generatorChain;
    }

    /**
     * 情况链路
     */
    public static void removeAll() {
        generatorChain.clear();
    }

    /**
     * 执行链路
     * 只执行首条执行器，接下来的执行器交给重写的过滤器完成
     */
    public static void doExecute(GlobalConfigVo globalConfigVo) throws Exception {
        GeneratorChain.removeAll();
        GeneratorChain.add(new ConfigInfoCheckFilter());
        // 文档规范目录生成器
        GeneratorChain.add(new CodeFileGeneratorFilter());
        // 参数解析器
        GeneratorChain.add(new ParamParseFilter());
        // 代码生成器
        GeneratorChain.add(new CodeGeneratorFilter());
        // 注解生成器
        GeneratorChain.add(new ParamGeneratorFilter());
        // 结束输出
        GeneratorChain.add(new SuccessFilter());
        if (generatorChain.size() > 0) {
            DWAbstractGeneratorFilter dwAbstractGeneratorFilter = generatorChain.poll();
            dwAbstractGeneratorFilter.doExecute(globalConfigVo,null,new DWAbstractGeneratorFilter.Chain(globalConfigVo,generatorChain,null));
        }
    }

}