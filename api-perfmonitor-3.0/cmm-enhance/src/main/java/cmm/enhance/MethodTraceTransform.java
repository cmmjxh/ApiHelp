package cmm.enhance;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ATHROW;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/9/18 16:35
 */
public class MethodTraceTransform {

    public static void transform(ClassNode classNode ,String classPath ,String className , boolean isMainClass) {
        // class内methods
        List<MethodNode> methods = classNode.methods;

        for (MethodNode mn : methods) {
            InsnList methodInsn = mn.instructions;
            if ("<init>".equals(mn.name) || "<clinit>".equals(mn.name) || methodInsn.size() <= 0 || className.equals(mn.name)) {
                continue;
            }
            String methodPath = classPath + "# " + mn.name + " ===> ";;

            // 检查是否已经增强
            if(checkAlreadyEnhance(mn)){
                return;
            };

            // 前置增强
            BeforeEnhance(methodInsn, methodPath);
            // 异常增强
            TryCatchEnhance(mn, methodInsn , isMainClass);
            // 后置增强
            AfterEnhance(methodInsn, methodPath);
        }
    }

    /**
     *  检查是否已经增强过了
     *  如果已经增强  则停止
     * */
    private static boolean checkAlreadyEnhance(MethodNode mn) {
        InsnList methodInsn = mn.instructions;

        Iterator<AbstractInsnNode> j = methodInsn.iterator();
        while (j.hasNext()) {
            try {
                MethodInsnNode in = (MethodInsnNode) j.next();
                if(in.owner.contains("TraceMonitorHandler")){
                    return true;
                }
            }catch (Exception e){
                continue;
            }
        }
        return false;
    }

    /**
     * 前置增强
     */
    private static void BeforeEnhance(InsnList insns, String methodPath) {
        // @Before
        InsnList beforeInsn = new InsnList();
        beforeInsn.add(new LdcInsnNode(methodPath));
        beforeInsn.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "TraceMonitorHandler", "before", "(Ljava/lang/String;)V",
                false));
        insns.insert(beforeInsn);
    }

    /**
     * 后置增强
     */
    private static void AfterEnhance(InsnList insns, String methodPath) {
        // @After
        Iterator<AbstractInsnNode> j = insns.iterator();
        while (j.hasNext()) {
            AbstractInsnNode in = j.next();
            int op = in.getOpcode();
            if ((op >= Opcodes.IRETURN && op <= Opcodes.RETURN)) {
                InsnList il = new InsnList();
                il.add(new LdcInsnNode(methodPath));
                il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "TraceMonitorHandler", "after", "(Ljava/lang/String;)V",
                        false));
                insns.insert(in.getPrevious(), il);
            }
        }
    }

    /**
     * 异常增强
     */
    private static void TryCatchEnhance(MethodNode mn, InsnList insns , boolean isMainClass) {
        if(!isMainClass){
           return;
        }
        // 异常节点Lable
        LabelNode startLabelNode = new LabelNode();
        LabelNode endLabelNode = new LabelNode();
        LabelNode exceptionHandlerLabelNode = new LabelNode();
        LabelNode returnLabelNode = new LabelNode();

        mn.tryCatchBlocks.add(new TryCatchBlockNode(startLabelNode, endLabelNode, exceptionHandlerLabelNode, "java/lang/Exception"));

        // 添加头部 try
        InsnList il = new InsnList();
        il.add(startLabelNode);
        insns.insert(il);

        // 添加尾部 catch handler
        InsnList il0 = new InsnList();
        il0.add(endLabelNode);
        il0.add(new JumpInsnNode(GOTO, returnLabelNode));

        il0.add(exceptionHandlerLabelNode);
        il0.add(new VarInsnNode(ASTORE, 100));
        il0.add(new VarInsnNode(ALOAD, 100));

        il0.add(new MethodInsnNode(INVOKESTATIC, "TraceMonitorHandler", "methodException", "(Ljava/lang/Exception;)V", false));
        il0.add(new VarInsnNode(ALOAD, 100));

        il0.add(returnLabelNode);
        il0.add(new InsnNode(ATHROW));
        insns.add(il0);
    }
}
