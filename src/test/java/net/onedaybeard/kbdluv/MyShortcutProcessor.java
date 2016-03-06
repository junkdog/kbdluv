package net.onedaybeard.kbdluv;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import net.onedaybeard.kbdluv.component.ComponentA;
import net.onedaybeard.kbdluv.component.ComponentB;

import static com.badlogic.gdx.Input.Keys.*;
import static org.junit.Assert.fail;

public class MyShortcutProcessor extends ShortcutProcessor {
	public Entity entity;
	public boolean emptyArgInvoked;
	public boolean entityInvoked;
	public boolean entityComponentInvoked;

	public boolean failOnNullEntity = true;
	public boolean componentInvoked;

	public TagManager tags;
	public ComponentMapper<ComponentA> componentAMapper;

	public MyShortcutProcessor() {
		super(new World(new WorldConfiguration().setSystem(TagManager.class)));
		entity = world.createEntity();
		entity.edit().create(ComponentA.class);

		world.process();
	}

	@Override
	protected Entity getEntity() {
		return entity;
	}

	@Shortcut(A)
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
}
