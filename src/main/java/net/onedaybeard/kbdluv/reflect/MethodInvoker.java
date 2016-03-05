package net.onedaybeard.kbdluv.reflect;

import com.artemis.Entity;

import java.lang.reflect.Method;

public abstract class MethodInvoker {
	protected Method method;

	protected MethodInvoker(Method method) {
		this.method = method;
		method.setAccessible(true);
	}

	public abstract void invoke(Object obj, Entity e) throws ReflectiveOperationException;
	public abstract boolean evaluate(Entity e);
}
