package top.xmdhs.fixlog4j2;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Main {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer((ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) -> {
            if (className.equals("org/apache/logging/log4j/core/lookup/JndiLookup"))
                try {
                    ClassPool p = ClassPool.getDefault();
                    p.appendClassPath(new LoaderClassPath(loader));
                    CtClass c = p.makeClass(new ByteArrayInputStream(classfileBuffer));
                    CtMethod[] lookups = c.getDeclaredMethods("lookup");
                    for (CtMethod v : lookups){
                        v.setBody("return null;");
                    }
                    return c.toBytecode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return null;
        });
    }
}
