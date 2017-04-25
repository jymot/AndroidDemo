package im.wangchao.asmdemo;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * <p>Description  : CostClassVisitor.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/4/24.</p>
 * <p>Time         : 下午4:40.</p>
 */
public class CostClassVisitor extends ClassVisitor {

    public CostClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return super.visitField(access, name, desc, signature, value);
    }

    @Override public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (cv != null){
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {

                private boolean inject = false;

                @Override protected void onMethodEnter() {
                    super.onMethodEnter();
                    if (inject){
                        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitLdcInsn("========start=========(" + name + "desc: " + desc + ")");
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                                "(Ljava/lang/String;)V", false);

                        mv.visitLdcInsn("wcwcwc");
                        mv.visitLdcInsn("========start=========(name: " + name + ", desc: " + desc + ")");
                        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                    }
                }

                @Override protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode);
                    if (inject){
                        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitLdcInsn("========end=========(" + name + "desc: " + desc + ")");
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                                "(Ljava/lang/String;)V", false);

                        mv.visitLdcInsn("wcwcwc");
                        mv.visitLdcInsn("========end=========(name: " + name + ", desc: " + desc + ")");
                        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                    }
                }

                @Override public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                    if (Type.getDescriptor(CostTest.class).equals(desc)){
                        inject = true;
                    }
                    return super.visitAnnotation(desc, visible);
                }
            };

            return mv;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
}
