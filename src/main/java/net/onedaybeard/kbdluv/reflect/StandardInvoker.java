package net.onedaybeard.kbdluv.reflect;

import com.artemis.Entity;

import java.lang.reflect.Method;

public class StandardInvoker extends MethodInvoker {
	private static final Object[] PARAMS = new Object[0];

	StandardInvoker(Object obj, Method method) {
		super(obj, method);
	}

	@Override
	public void invoke(Entity e) throws ReflectiveOperationException {
		method.invoke(obj, PARAMS);
	}

	@Override
	public boolean evaluate(Entity e) {
		return true;
	}
}
