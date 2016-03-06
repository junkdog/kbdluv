package net.onedaybeard.kbdluv;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.IntMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static net.onedaybeard.kbdluv.ShortcutProcessor.MOD_ALT;
import static net.onedaybeard.kbdluv.ShortcutProcessor.MOD_CTRL;
import static net.onedaybeard.kbdluv.ShortcutProcessor.MOD_SHIFT;

public final class ShortcutUtil {

	private static final StringBuilder _sb = new StringBuilder();
	private static final IntMap<String> keyToString;
	static {
		keyToString = getKeyNames();
	}

	private ShortcutUtil() {}

	public static String toString(Method method) {
		Shortcut shortcut = method.getAnnotation(Shortcut.class);
		if (shortcut == null)
			throw new RuntimeException(method.toString());

		_sb.setLength(0);
		if ((shortcut.value() & MOD_CTRL) != 0)
			_sb.append("CTRL + ");
		if ((shortcut.value() & MOD_ALT) != 0)
			_sb.append("ALT + ");
		if ((shortcut.value() & MOD_SHIFT) != 0)
			_sb.append("SHIFT + ");

		_sb.append(keyToString.get(shortcut.value() & 0xff));

		return _sb.toString();
	}

	private static IntMap<String> getKeyNames() {
		IntMap<String> keyToString = new IntMap<>();
		try {
			for (Field f : Input.Keys.class.getFields()) {
				if (f.getName().startsWith("META_"))
					continue;

				f.setAccessible(true);
				keyToString.put((int) f.get(null), f.getName());
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return keyToString;
	}
}
