package com.swingInspector.agent;

import org.objectweb.asm.Type;
import sun.tools.jconsole.JConsole;

import javax.swing.*;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Collections;

/**
 * Agent main class. It installs class transformer and request {@link javax.swing.JComponent} constructor rework.
 * author: alex
 * date  : 11/10/13
 */
public class Agent {
	public static void premain(String options, Instrumentation instrumentation) {
		String internalJComponentName = Type.getInternalName(JComponent.class);
		ConstructorCallTransformer transformer = new ConstructorCallTransformer(Collections.singleton(internalJComponentName));
		instrumentation.addTransformer(transformer);
		try {
			instrumentation.retransformClasses(JComponent.class);//retransform existed classes
		} catch (UnmodifiableClassException e) {
			throw new RuntimeException(e);
		} finally {
			instrumentation.removeTransformer(transformer);
		}
	}

}
