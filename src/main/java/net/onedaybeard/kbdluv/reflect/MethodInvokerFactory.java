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

	public MethodInvoker create(Object obj, Method method) {
		Class<?>[] types = method.getParameterTypes();
		for (Factory factory : factories) {
			if (factory.checkParameters(types)) {
				return factory.create(obj, method);
			}
		}

		throw new RuntimeException("can't create invoker for " + method);
	}

	private static boolean isEntity(Class<?> type) {
		return Entity.class == type;
	}

	private static boolean isComponent(Class<?> type) {
		return Component.class.isAssignableFrom(type);
	}

	public static abstract class Factory {
		abstract boolean checkParameters(Class<?>[] types);
		abstract MethodInvoker create(Object obj, Method method);


		public static boolean isEntity(Class<?> type) {
			return Entity.class == type;
		}

		public static boolean isComponent(Class<?> type) {
			return Component.class.isAssignableFrom(type);
		}
	}
}
