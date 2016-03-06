package net.onedaybeard.kbdluv.reflect;

import com.artemis.Component;
import com.artemis.Entity;

import java.lang.reflect.Method;

public class EntityComponentInvoker extends MethodInvoker {
	private static final Object[] PARAMS = new Object[2];
	private final Class<? extends Component> type;

	EntityComponentInvoker(Object obj, Method method) {
		super(obj, method);
		type = (Class<? extends Component>) method.getParameterTypes()[1];
	}

	@Override
	public void invoke(Entity e) throws ReflectiveOperationException {
		PARAMS[0] = e;
		PARAMS[1] = e.getComponent(type);
		method.invoke(obj, PARAMS);
	}

	@Override
	public boolean evaluate(Entity e) {
		return e != null && e.getComponent(type) != null;
	}

	public static class Factory extends MethodInvokerFactory.Factory {
		@Override
		public boolean checkParameters(Class<?>[] types) {
			return types.length == 2 && isEntity(types[0]) && isComponent(types[1]);
		}

		@Override
		public MethodInvoker create(Object obj, Method method) {
			return new EntityComponentInvoker(obj, method);
		}
	}
}
