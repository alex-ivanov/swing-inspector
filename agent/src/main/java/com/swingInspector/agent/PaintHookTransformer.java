package com.swingInspector.agent;

import com.swingInspector.runtime.Hooks;
import org.objectweb.asm.*;

import javax.swing.*;
import java.awt.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

/**
 * author: alex
 * date  : 11/13/13
 */
public class PaintHookTransformer implements ClassFileTransformer {
	@Override
	public byte[] transform(
			ClassLoader loader,
			String className,
			Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException
	{
		if (!classBeingRedefined.isAssignableFrom(JComponent.class))
			return null;

		try {
			ClassReader reader = new ClassReader(classfileBuffer);
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			Class<Hooks> clazz = Hooks.class;

			String target = Type.getInternalName(clazz);
			String name = "onPaint";
			Method toCall = clazz.getMethod(name, JComponent.class, Graphics.class);
			String methodDesc = Type.getMethodDescriptor(toCall);

			reader.accept(new PaintMethodHookVisitor(writer, target, name, methodDesc), ClassReader.EXPAND_FRAMES);
			return writer.toByteArray();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static class PaintMethodHookVisitor extends ClassVisitor {
		private final String target;
		private final String targetMethodName;
		private final String targetMethodDesc;
		private final String paint = "paint";
		private final String paintMethodDesc;

		public PaintMethodHookVisitor(ClassVisitor visitor, String target, String name, String targetMethodDesc)
				throws NoSuchMethodException
		{
			super(Opcodes.ASM4, visitor);
			this.target = target;
			this.targetMethodName = name;
			this.targetMethodDesc = targetMethodDesc;

			Method paintMethod = JComponent.class.getMethod(paint, Graphics.class);
			paintMethodDesc = Type.getMethodDescriptor(paintMethod);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			MethodVisitor original = super.visitMethod(access, name, desc, signature, exceptions);
			if (name.equals(paint) && desc.equals(paintMethodDesc)) {
				return new PaintMethodVisitor(original, target, targetMethodName, targetMethodDesc);
			}
			return original;
		}
	}

	private static class PaintMethodVisitor extends MethodVisitor {
		private final MethodVisitor mv;
		private final String target;
		private final String targetMethodName;
		private final String targetMethodDesc;

		public PaintMethodVisitor(MethodVisitor mv, String target, String targetMethodName, String targetMethodDesc) {
			super(Opcodes.ASM4, mv);
			this.mv = mv;
			this.target = target;
			this.targetMethodName = targetMethodName;
			this.targetMethodDesc = targetMethodDesc;
		}

		@Override
		public void visitInsn(int opcode) {
			if (opcode == Opcodes.RETURN) {
				mv.visitVarInsn(Opcodes.ALOAD, 0);//load this
				mv.visitVarInsn(Opcodes.ALOAD, 1);//load first parameter on stack (graphics)
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, target, targetMethodName, targetMethodDesc);
			}
			super.visitInsn(opcode);
		}
	}
}
