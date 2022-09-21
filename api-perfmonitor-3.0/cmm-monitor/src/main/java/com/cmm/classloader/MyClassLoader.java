package com.cmm.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author caomm
 * @date 2022-8-18
 */
public class MyClassLoader extends URLClassLoader {
    // 使用当前AppClassLoder作为父类加载器，确保MyClassLoader加载类，能正常访问AppClassLoder加载类,比如org.slf4j.LoggerFactory
    public MyClassLoader(URL[] urls) {
        super(urls, ClassLoader.getSystemClassLoader());
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        final Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }

        // 优先从parent（SystemClassLoader）里加载系统类，避免抛出ClassNotFoundException
        if (name != null && (name.startsWith("sun.") || name.startsWith("java."))) {
            return super.loadClass(name, resolve);
        }
        try {
            Class<?> aClass = findClass(name);
            if (resolve) {
                resolveClass(aClass);
            }
            return aClass;
        } catch (Exception e) {
            // ignore
        }
        return super.loadClass(name, resolve);
    }
}
