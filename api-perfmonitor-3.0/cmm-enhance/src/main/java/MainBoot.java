import cmm.log.LogUtils;
import cmm.transformer.EnhancerClassFileTransformer;
import cmm.util.ClassUtils;
import cmm.util.SearchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Set;

/**
 * @author caomm
 * @Title code 核心启动入口
 * @Description TODO
 * @Date 2022/8/20 23:10
 */
public class MainBoot {
    private static final Logger logger = LoggerFactory.getLogger(MainBoot.class);

    /**
     * @param trackPath 追踪路径
     * @title 代理启动入口
     * @author caomm
     * <pre>
     *      1、添加字节码增强转换器，对需要的class进行增强{@link EnhancerClassFileTransformer}
     *      2、搜索vm已经加载的class，对于需要增强的class进行重新装载
     *      注意：ClassUtils.setServiceClass 是为了获取针对DAP框架入口方法类，目的用于异常增强。
     * <pre/>
     */
    public static synchronized void invoke(String trackPath, final Instrumentation inst) {
        String ignoreFile = "";
        String trackPath0 = "";
        try {
            if (trackPath != null && !trackPath.isEmpty()) {
                trackPath0 = trackPath.substring(0, trackPath.indexOf(";"));
                ignoreFile = trackPath.substring(trackPath.indexOf(";") + 1, trackPath.length());
            }

            // 字节码增强
            // 添加字节码增强转换器 -- EnhancerClassFileTransformer#transform 进行实现  canRetransform :true 表示该转换器可以在重新加载时触发转换
            inst.addTransformer(new EnhancerClassFileTransformer(trackPath0, ignoreFile), true);
            // 搜索jvm中指定追踪路径下 已经被加载的类
            Set<Class<?>> matchedClasses = SearchUtils.searchClass(inst, trackPath0 + ".*", false, null);

            // 获取入口接口类 - 根据集成 com.digiwin.app.service.DWService  -- 此处就是相对于鼎捷DAP架构了，目的增强入口方法tryCatch
            ClassUtils.setServiceClass(trackPath0, matchedClasses);

            for (Class<?> clazz : matchedClasses) {
                // 忽略LambdaClass 、 接口(如果重新加载会遇到请求入参接受不到问题) 、 非应用包 、内部类
                if (ClassUtils.isLambdaClass(clazz) || !clazz.getName().contains(trackPath0) || clazz.isInterface() ) {
                    continue;
                }
                try {
                    // vm在启动时会加载一部分class ， 对于需要被加载的class，进行重新改造加载
                    inst.retransformClasses(clazz);
                } catch (Exception e) {
                    String errorMsg = "重新改造类错误, 类名: " + clazz.getName();
                    logger.info("【ApiHelp】"+errorMsg);
                }
            }
        } catch (IOException e) {
        } finally {
            inst.removeTransformer(new EnhancerClassFileTransformer(trackPath0, ignoreFile));
        }
    }
}
