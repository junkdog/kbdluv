package net.onedaybeard.kbdluv.reflect;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;

import java.lang.reflect.Method;

public final class MethodInvokerFactory {
	public Array<Factory> factories = new Array<>();

	public MethodInvokerFactory() {
		factories.add(new ComponentInvoker.Factory());
		factories.add(new EntityComponentInvoker.Factory());
		factories.add(new EntityInvoker.Factory());
		factories.add(new StandardInvoker.Factory());
	}

	public MethodInvokerFactory(Factory... additional) {
		this();

		for (int i = 0; i < additional.length; i++) {
			factories.add(additional[i]);
		}
	}

	public MethodInvoker create(Object obj, Method method) {
		Class<?>[] types = method.getParameterTypes();
		for (Factory factory : factories) {
			if (factory.checkParameters(types)) {
				return factory.create(obj, method);
			}
		}

		return null;
	}

	public static abstract class Factory {
		abstract protected boolean checkParameters(Class<?>[] types);
		abstract protected MethodInvoker create(Object obj, Method method);


		public static boolean isEntity(Class<?> type) {
			return Entity.class == type;
		}

		public static boolean isComponent(Class<?> type) {
			return Component.class.isAssignableFrom(type);
		}
	}
}
