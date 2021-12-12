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
        if (className.replace("/", ".").equals("org.apache.logging.log4j.core.lookup.JndiLookup"))
            try {
                System.out.println("Successfully found JndiLookup, ready to repair it ...");
                ClassPool defaultClassPool = ClassPool.getDefault();
                defaultClassPool.appendClassPath(new LoaderClassPath(loader));
                CtClass jndiLookupClass = defaultClassPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod lookup = jndiLookupClass.getDeclaredMethod("lookup");
                lookup.setBody("{return null;}");
                System.out.println("Patch of JndiLookup successfully applied.");
                return jndiLookupClass.toBytecode();
            } catch (Exception e) {
                System.out.println("Unable to apply the patch, is there an error?" + e.getMessage());
            }
        return null;
    }
}
