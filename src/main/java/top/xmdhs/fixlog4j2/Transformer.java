package top.xmdhs.fixlog4j2;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("org/apache/logging/log4j/core/lookup/JndiLookup"))
            try {
                ClassPool p = ClassPool.getDefault();
                p.appendClassPath(new LoaderClassPath(loader));
                CtClass c = p.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod lookup = c.getDeclaredMethod("lookup");
                lookup.setBody("return null;");
                return c.toBytecode();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        return null;
    }
}
