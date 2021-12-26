package top.xmdhs.fixlog4j2;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class AsmMethodVisit extends MethodVisitor implements Opcodes{
    public AsmMethodVisit(MethodVisitor mv) {
        super(ASM4, mv);
    }

    @Override
    public void visitCode() {
        mv.visitInsn(Opcodes.ACONST_NULL);
        mv.visitInsn(Opcodes.ARETURN);
    }
}