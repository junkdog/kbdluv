package net.onedaybeard.kbdluv;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntMap;
import net.onedaybeard.kbdluv.reflect.MethodInvoker;
import net.onedaybeard.kbdluv.reflect.MethodInvokerFactory;

import java.lang.reflect.Method;

import static com.badlogic.gdx.Input.Keys.ALT_LEFT;
import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;

public abstract class ShortcutProcessor extends InputAdapter {

	// encoding Input.Keys value as 1st byte
	public static final int MOD_CTRL  = 1 << 8;
	public static final int MOD_ALT   = 1 << 9;
	public static final int MOD_SHIFT = 1 << 10;

	private final IntMap<MethodInvoker> shortcuts;

	private int modState;
	private boolean consumedEvent;
	protected World world;

	protected ShortcutProcessor(World world) {
		shortcuts = registerShortcuts(new MethodInvokerFactory());
		this.world = world;
		world.inject(this);
	}

	protected abstract Entity getEntity();

	private IntMap<MethodInvoker> registerShortcuts(MethodInvokerFactory factory) {
		IntMap<MethodInvoker> shortcuts = new IntMap<>();

		Method[] methods = getClass().getDeclaredMethods();
		for (Method method : methods) {
			Shortcut shortCut = method.getAnnotation(Shortcut.class);
			if (shortCut != null) {
				MethodInvoker invoker = factory.create(this, method);
				shortcuts.put(shortCut.value(), invoker);
			}
		}

		return shortcuts;
	}

	@Override
	public final boolean keyDown(int keycode) {
		if (isModKey(ALT_LEFT, keycode)) {
			modState |= MOD_ALT;
		} else if (isModKey(CONTROL_LEFT, keycode)) {
			modState |= MOD_CTRL;
		} else if (isModKey(SHIFT_LEFT, keycode)) {
			modState |= MOD_SHIFT;
		}

		return processKeyState(modState | keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		boolean old = consumedEvent;
		consumedEvent = false;
		return old;
	}

	boolean processKeyState(int state) {
		MethodInvoker m = shortcuts.get(state);
		try {
			Entity entity = getEntity();
			if (m != null && m.evaluate(entity)) {
				m.invoke(entity);
				consumedEvent = true;
				return true;
			} else {
				consumedEvent = false;
				return false;
			}
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isModKey(int leftKeycode, int actualKeycode) {
		return leftKeycode == actualKeycode || (leftKeycode + 1) == actualKeycode;
	}
}
