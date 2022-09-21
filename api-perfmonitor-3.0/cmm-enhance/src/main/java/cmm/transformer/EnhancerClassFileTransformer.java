package cmm.transformer;

import cmm.enhance.Enhance;
import cmm.util.ClassUtils;
import cmm.util.FileUtils;
import cmm.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author caomm
 * @Title EnhancerClassFileTransformer
 * @Description TODO
 * @Date 2022/8/17 17:43
 */
public class EnhancerClassFileTransformer implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(EnhancerClassFileTransformer.class);
    private String trackPath;
    private String[] ignoreFile;

    public EnhancerClassFileTransformer(String trackPath, String ignoreFile) {
        this.trackPath = FileUtils.classPathChange0(trackPath);
        this.ignoreFile = FileUtils.classPathChange1(ignoreFile);
    }

    /**
     * @author caomm
     * @title instrument技术 ，实现类转换实现
     * <pre>
     *   目标vm启动时，会加载class，此处实现class加载时，进行改造操作。
     *   对于那些已经被加载 且 需要增强的 class， 需要在{@link MainBoot} invoke中进行重新装载,
     *   对于部分class可能会在执行时加载，此处对于无需增强的class进行过滤
     * </pre>
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        boolean flag = false;
        // 忽略增强类
        if (StringUtils.isCEmpty(className) || !className.contains(this.trackPath) || className.contains("$$Lambda$") || className.contains("$") || classBeingRedefined.isInterface()) {
            return classfileBuffer;
        }
        // 忽略目录增强
        if (ignoreFile.length > 0) {
            for (int i = 0; i < ignoreFile.length; i++) {
                if (className.contains(ignoreFile[i])) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                return classfileBuffer;
            }
        }

        // 判断是否是入口class
        boolean isMainClass = ClassUtils.isMainClass(classBeingRedefined);
        try {
            classfileBuffer = Enhance.invoke(loader, className, this.trackPath, isMainClass, classfileBuffer);
            FileUtils.writeByte(className , classfileBuffer);
        } catch (Exception e) {
            logger.info("【ApiHelp】类增强失败！" + className);
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
