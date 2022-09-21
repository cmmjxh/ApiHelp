package cmm.util;

import cmm.log.LogUtils;

import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/17 21:10
 */
public class ClassUtils {

    private final static String DW_CONTROLLER_INTERFACT = "com.digiwin.app.service.DWService";

    public static boolean isLambdaClass(Class<?> clazz) {
        return clazz.getName().contains("$$Lambda$");
    }

    public static List<String> DWServiceInterfaces = new ArrayList<String>();
    private final static String CONTANT_L = "L";



    /**
     * 获取 【鼎捷应用】 框架入口类（其他框架根据项目结构获取）
     */
    public static List<String> setServiceClass(String trackPath0, Set<Class<?>> matchedClasses) throws IOException {
        for (Class<?> clazz : matchedClasses) {

            // 忽略LambdaClass
            if (ClassUtils.isLambdaClass(clazz) || !clazz.getName().contains(trackPath0)) {
                continue;
            }
            try {
                if (!clazz.isInterface()) {
                    continue;
                }
                AnnotatedType[] annotatedInterfaces = clazz.getAnnotatedInterfaces();
                for(AnnotatedType annotatedType : annotatedInterfaces){
                    if(DW_CONTROLLER_INTERFACT.equals(annotatedType.getType().getTypeName())){
                        DWServiceInterfaces.add(clazz.getName());
                    }
                }
            } catch (Throwable e) {
            }
        }
        return DWServiceInterfaces;
    }

    public static boolean isMainClass(Class<?> clazz) {
        if (getDWServiceInterfaces().size() <= 0) {
            return false;
        }
        // 检查改类是否集成入口接口，如果集成则视为入口class
        AnnotatedType[] annotatedInterfaces = clazz.getAnnotatedInterfaces();
        for (AnnotatedType annotatedType : annotatedInterfaces) {
            if (getDWServiceInterfaces().contains(annotatedType.getType().getTypeName())) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getDWServiceInterfaces() {
        return DWServiceInterfaces;
    }

    /**
     * 根据ASM 规范解析返回值类型
     */
    public static String parse(String descriptor) {
        if (!descriptor.isEmpty()) {
            int index = descriptor.lastIndexOf(")");
            String returnDescriptor = descriptor.substring(index + 1, descriptor.length());
            String fristChar = returnDescriptor.substring(0, 1);
            if (!CONTANT_L.equals(fristChar)) {
                return fristChar;
            } else {
                String substring = returnDescriptor.substring(returnDescriptor.lastIndexOf("/") + 1, returnDescriptor.length() - 1);
                if ("String".equals(substring)) {
                    return "S";
                }
                return returnDescriptor.substring(1, returnDescriptor.length() - 1);
            }
        }
        return "";
    }
}
