package net.onedaybeard.kbdluv.reflect;

import com.artemis.Entity;

import java.lang.reflect.Method;

public abstract class MethodInvoker {
	protected Object obj;
	protected Method method;

	public MethodInvoker(Object obj, Method method) {
		this.obj = obj;
		this.method = method;
		method.setAccessible(true);
	}

	public abstract void invoke(Entity e) throws ReflectiveOperationException;
	public abstract boolean evaluate(Entity e);
}
