package net.onedaybeard.kbdluv.reflect;

import com.artemis.Component;
import com.artemis.Entity;

import java.lang.reflect.Method;

public final class MethodInvokerFactory {
	private MethodInvokerFactory() {}

	public static MethodInvoker create(Object obj, Method method) {
		Class<?>[] types = method.getParameterTypes();
		if (types.length == 0)
			return new StandardInvoker(obj, method);
		if (types.length == 1 && isEntity(types[0]))
			return new EntityInvoker(obj, method);
		if (types.length == 1 && isComponent(types[0]))
			return new ComponentInvoker(obj, method);
		if (types.length == 2 && isEntity(types[0]) && isComponent(types[1]))
			return new EntityComponentInvoker(obj, method);

		throw new RuntimeException("can't parse invoker for " + method);
	}

	private static boolean isEntity(Class<?> type) {
		return Entity.class == type;
	}

	private static boolean isComponent(Class<?> type) {
		return Component.class.isAssignableFrom(type);
	}
}
