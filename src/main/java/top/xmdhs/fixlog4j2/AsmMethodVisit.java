package top.xmdhs.fixlog4j2;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class AsmMethodVisit extends MethodVisitor implements Opcodes {
    public AsmMethodVisit(MethodVisitor mv) {
        super(ASM5);
        this.w = mv;
    }

    private final MethodVisitor w;

    @Override
    public void visitCode() {
        w.visitCode();
        w.visitInsn(ACONST_NULL);
        w.visitInsn(ARETURN);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        w.visitMaxs(maxStack, maxLocals);
    }
}