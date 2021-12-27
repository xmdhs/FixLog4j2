package top.xmdhs.fixlog4j2;

import org.objectweb.asm.*;


public class MethodChangeClassAdapter extends ClassVisitor implements Opcodes {
    public MethodChangeClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (cv != null && "lookup".equals(name)) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            return new AsmMethodVisit(mv);
        }
        if (cv != null) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }
        return null;
    }

}