package cmm.enhance;


import cmm.util.FileUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author caomm
 * @Title 字节码增强
 * @Description TODO
 * @Date 2022/8/5 15:01
 */
public class Enhance {
    private static final Logger logger = LoggerFactory.getLogger(Enhance.class);

    /**
     * @author caomm
     * @Title 使用ASM 进行 字节码编写 增强方法功能，打印执行时间代码
     * @Date 2022/8/5 15:01
     */
    public static byte[] invoke(ClassLoader loader, String classzPath, String trackPath, boolean isMainClass, byte[] classfileBuffer) throws IOException {
        String classPath = "";
        String className = "";
        try {
            classPath = FileUtils.classPathChange(classzPath, trackPath);
            className = FileUtils.getClassName(classPath);

            ClassNode classNode = new ClassNode();
            ClassReader cr = new ClassReader(classfileBuffer);
            cr.accept(classNode, Opcodes.ASM9);
            // 增强code
            MethodTraceTransform.transform(classNode, classPath, className, isMainClass);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(cw);

            return cw.toByteArray();
        } catch (Exception e) {
            if(e instanceof TypeNotPresentException){
                return classfileBuffer;
            }
            if(e instanceof ClassNotFoundException){
                logger.info("【ApiHelp】类增强失败，依赖缺失！" + className + ":" + e);
                return classfileBuffer;
            }
            return classfileBuffer;
        }
    }


}
