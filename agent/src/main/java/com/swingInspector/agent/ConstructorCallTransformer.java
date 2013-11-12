package com.swingInspector.agent;

import com.swingInspector.runtime.Hooks;
import org.objectweb.asm.*;

import javax.swing.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Set;

/**
* author: alex
* date  : 11/10/13
*/
public class ConstructorCallTransformer implements ClassFileTransformer {
	private final Set<String> allowedForTransformation;

	public ConstructorCallTransformer(Set<String> allowedForTransformation) {
		this.allowedForTransformation = allowedForTransformation;
	}

	@Override
	public byte[] transform(ClassLoader loader,
							String className,
							Class<?> classBeingRedefined,
							ProtectionDomain protectionDomain,
							byte[] classfileBuffer) throws IllegalClassFormatException
	{
		if (allowedForTransformation.contains(className)) {
			try {
				ClassReader reader = new ClassReader(classfileBuffer);
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				Class<Hooks> clazz = Hooks.class;

				String target = Type.getInternalName(clazz);
				String name = "onComponentCreate";
				Method toCall = clazz.getMethod(name, JComponent.class);

				String methodDesc = Type.getMethodDescriptor(toCall);
				ConstructorClassAppender realTransformer =
						new ConstructorClassAppender(writer, target, name, methodDesc);
				reader.accept(realTransformer, ClassReader.EXPAND_FRAMES);
				return writer.toByteArray();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static class ConstructorClassAppender extends ClassVisitor {
		private final String target;
		private final String methodName;
		private final String methodDesc;

		public ConstructorClassAppender(ClassVisitor cv, String target, String methodName, String methodDesc) {
			super(Opcodes.ASM4, cv);
			this.target = target;
			this.methodName = methodName;
			this.methodDesc = methodDesc;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			MethodVisitor base = super.visitMethod(access, name, desc, signature, exceptions);
			if (name.equals("<init>")) {
				return new ConstructorCodeAppender(base, target, methodName, methodDesc);
			} else {
				return base;
			}
		}
	}

	private static class ConstructorCodeAppender extends MethodVisitor {
		private final MethodVisitor mv;
		private final String target;
		private final String methodName;
		private final String methodDesc;

		public ConstructorCodeAppender(MethodVisitor mv, String target, String methodName, String methodDesc) {
			super(Opcodes.ASM4, mv);
			this.mv = mv;
			this.target = target;
			this.methodName = methodName;
			this.methodDesc = methodDesc;
		}

		@Override
		public void visitCode() {
			mv.visitVarInsn(Opcodes.ALOAD, 0);//load this on stack
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, target, methodName, methodDesc);//invoke register method
			super.visitCode();
		}
	}
}
