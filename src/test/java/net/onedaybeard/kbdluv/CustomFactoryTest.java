package net.onedaybeard.kbdluv;

import com.artemis.*;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Input;
import net.onedaybeard.kbdluv.reflect.MethodInvoker;
import net.onedaybeard.kbdluv.reflect.MethodInvokerFactory;
import org.junit.Test;

import java.lang.reflect.Method;

import static net.onedaybeard.kbdluv.ShortcutProcessor.MOD_SHIFT;
import static org.junit.Assert.*;

public class CustomFactoryTest {
	public static class SystemInvoker extends MethodInvoker  {
		private final World world;
		private final Class<? extends BaseSystem> systemType;
		private static final Object[] PARAMS = new Object[1];

		public SystemInvoker(World world, Object obj, Method method) {
			super(obj, method);
			this.world = world;
			systemType = (Class<? extends BaseSystem>) method.getParameterTypes()[0];
		}

		@Override
		public void invoke(Entity e) throws ReflectiveOperationException {
			PARAMS[0] = world.getSystem(systemType);
			assertNotNull(PARAMS[0]);
			method.invoke(obj, PARAMS);
		}

		@Override
		public boolean evaluate(Entity e) {
			return world.getSystem(systemType) != null;
		}

		public static class Factory extends MethodInvokerFactory.Factory {
			private World world;

			public Factory(World world) {
				this.world = world;
			}

			@Override
			public boolean checkParameters(Class<?>[] types) {
				return types.length == 1 && isSystem(types[0]);
			}

			@Override
			public MethodInvoker create(Object obj, Method method) {
				return new SystemInvoker(world, obj, method);
			}

			private static boolean isSystem(Class<?> type) {
				return BaseSystem.class.isAssignableFrom(type);
			}
		}
	}

	@Test
	public void system_factory_test() {
		World world = new World(new WorldConfiguration().setSystem(TagManager.class));

		MethodInvokerFactory factory = new MethodInvokerFactory(
			new SystemInvoker.Factory(world));
		MyShortcutProcessor ip = new MyShortcutProcessor(factory);

		world.process();

		assertFalse(ip.customSystem);
		ip.processKeyState(Input.Keys.F);
		assertTrue(ip.customSystem);

		ip.processKeyState(MOD_SHIFT | Input.Keys.F);
	}

}
