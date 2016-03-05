package net.onedaybeard.kbdluv.reflect;

import com.artemis.Component;
import com.artemis.Entity;

import java.lang.reflect.Method;

public class EntityComponentInvoker extends MethodInvoker {
	private static final Object[] PARAMS = new Object[2];
	private final Class<? extends Component> type;

	EntityComponentInvoker(Method method) {
		super(method);
		type = (Class<? extends Component>) method.getParameterTypes()[1];
	}

	@Override
	public void invoke(Object obj, Entity e) throws ReflectiveOperationException {
		PARAMS[0] = e;
		PARAMS[1] = e.getComponent(type);
		method.invoke(obj, PARAMS);
	}

	@Override
	public boolean evaluate(Entity e) {
		return e != null && e.getComponent(type) != null;
	}
}
