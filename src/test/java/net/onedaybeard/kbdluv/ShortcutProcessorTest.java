package net.onedaybeard.kbdluv;

import com.badlogic.gdx.Input;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShortcutProcessorTest {
	@Test
	public void zero_param_invokation_test() {
		MyShortcutProcessor ip = new MyShortcutProcessor();

		assertFalse(ip.emptyArgInvoked);
		ip.processKeyState(Input.Keys.A);
		assertTrue(ip.emptyArgInvoked);
	}

	@Test
	public void entity_param_invokation_test() {
		MyShortcutProcessor ip = new MyShortcutProcessor();

		assertFalse(ip.entityInvoked);
		ip.processKeyState(Input.Keys.B);
		assertTrue(ip.entityInvoked);
	}

	@Test
	public void entity_component_param_invokation_test() {
		MyShortcutProcessor ip = new MyShortcutProcessor();

		assertFalse(ip.entityComponentInvoked);
		ip.processKeyState(Input.Keys.C);
		assertTrue(ip.entityComponentInvoked);
	}

	@Test
	public void component_param_invokation_test() {
		MyShortcutProcessor ip = new MyShortcutProcessor();

		assertFalse(ip.componentInvoked);
		ip.processKeyState(Input.Keys.D);
		assertTrue(ip.componentInvoked);
	}

	@Test
	public void null_component_param_no_invokation_test() {
		MyShortcutProcessor ip = new MyShortcutProcessor();

		assertFalse(ip.componentInvoked);
		ip.processKeyState(Input.Keys.E);
		assertFalse(ip.componentInvoked);
	}
}