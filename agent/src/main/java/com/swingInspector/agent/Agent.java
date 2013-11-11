package com.swingInspector.agent;

import com.swingInspector.runtime.SwingComponentHolder;
import com.swingInspector.runtime.SwingHelper;
import com.swingInspector.ui.SwingDevelopmentConsoleUI;
import org.objectweb.asm.Type;

import javax.swing.*;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;

/**
 * Agent main class. It installs class transformer and request {@link javax.swing.JComponent} constructor rework.
 * author: alex
 * date  : 11/10/13
 */
public class Agent {
	public static final String JAVAAGENT_PREFIX = "-javaagent:";

	public static void premain(String options, Instrumentation instrumentation) {
		String jar = locateAgentJar();
		if (jar == null) {
			System.err.println("Can't startup the agent");
			System.exit(1);
		}

		String internalJComponentName = Type.getInternalName(JComponent.class);
		ConstructorCallTransformer transformer = new ConstructorCallTransformer(
				Collections.singleton(internalJComponentName));
		instrumentation.addTransformer(transformer, true);
		try {
			instrumentation.retransformClasses(JComponent.class);
		} catch (UnmodifiableClassException e) {
			throw new RuntimeException(e);
		}

		try {
			instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(jar));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		SwingHelper.collectAllComponents(SwingComponentHolder.components);
		SwingDevelopmentConsoleUI ui = new SwingDevelopmentConsoleUI();
		ui.setVisible(true);
	}

	private static String locateAgentJar() {
		List<String> arguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		for (String argument : arguments) {
			String lower = argument.toLowerCase();
			if (lower.startsWith(JAVAAGENT_PREFIX)) {
				String secondPart = lower.substring(JAVAAGENT_PREFIX.length());
				String[] split = secondPart.split("=");
				return split[0];
			}
		}
		return null;
	}
}
