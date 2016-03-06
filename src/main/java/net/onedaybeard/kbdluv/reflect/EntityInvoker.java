package net.onedaybeard.kbdluv.reflect;

import com.artemis.Entity;

import java.lang.reflect.Method;

public class EntityInvoker extends MethodInvoker {
	private static final Object[] PARAMS = new Object[1];

	EntityInvoker(Object obj, Method method) {
		super(obj, method);
	}

	@Override
	public void invoke(Entity e) throws ReflectiveOperationException {
		PARAMS[0] = e;
		method.invoke(obj, PARAMS);
	}

	@Override
	public boolean evaluate(Entity e) {
		return e != null;
	}
}
