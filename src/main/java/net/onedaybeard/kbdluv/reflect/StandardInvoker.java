package net.onedaybeard.kbdluv.reflect;

import com.artemis.Entity;

import java.lang.reflect.Method;

public class StandardInvoker extends MethodInvoker {
	private static final Object[] PARAMS = new Object[0];

	StandardInvoker(Method method) {
		super(method);
	}

	@Override
	public void invoke(Object obj, Entity e) throws ReflectiveOperationException {
		method.invoke(obj, PARAMS);
	}

	@Override
	public boolean evaluate(Entity e) {
		return true;
	}
}
