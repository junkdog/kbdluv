package net.onedaybeard.kbdluv;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import net.onedaybeard.kbdluv.component.ComponentA;
import net.onedaybeard.kbdluv.component.ComponentB;
import net.onedaybeard.kbdluv.reflect.MethodInvokerFactory;

import static com.badlogic.gdx.Input.Keys.*;
import static org.junit.Assert.fail;

public class MyShortcutProcessor extends ShortcutProcessor {
	public Entity entity;
	public boolean emptyArgInvoked;
	public boolean entityInvoked;
	public boolean entityComponentInvoked;

	public boolean failOnNullEntity = true;
	public boolean componentInvoked;

	public boolean customSystem;

	public TagManager tags;
	public ComponentMapper<ComponentA> componentAMapper;

	public MyShortcutProcessor() {
		this(new MethodInvokerFactory());
	}

	public MyShortcutProcessor(MethodInvokerFactory factory) {
		super(new World(new WorldConfiguration().setSystem(TagManager.class)), factory);
		entity = world.createEntity();
		entity.edit().create(ComponentA.class);

		world.process();
	}

	@Override
	protected Entity getEntity() {
		return entity;
	}

	@Shortcut(MOD_CTRL | MOD_SHIFT | A)
	private void emptyArg() {
		emptyArgInvoked = true;
	}

	@Shortcut(B)
	private void entity(Entity e) {
		entityInvoked = true;
		if (failOnNullEntity && e == null)
			fail("Entity was null");
	}

	@Shortcut(C)
	private void entity_component_a(Entity e, ComponentA comp) {
		entityComponentInvoked = true;
		if (failOnNullEntity && e == null)
			fail("Entity was null");
		if (comp == null)
			fail("Component was null");
	}

	@Shortcut(D)
	private void component_a(ComponentA comp) {
		componentInvoked = true;
		if (comp == null)
			fail("Component was null");
	}

	@Shortcut(E)
	private void component_b(Entity e, ComponentB comp) {
		fail("shoudln't be here");
	}

	@Shortcut(F)
	private void custom_pass_system(TagManager tags) {
		customSystem = true;
	}

	@Shortcut(MOD_SHIFT | F)
	private void custom_dont_pass_system(GroupManager shouldBeNull) {
		fail("shoudln't be here");
	}
}
