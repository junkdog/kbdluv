## kbdluv-artemis

Easy shortcuts for libgdx/artemis.


#### Usage

- Extend ShortcutProcessor
- Implement `getEntity()`
 - `getEntity` can return null if no shortcut methods take an Entity
   or Component parameter
- Create a method, annotate it with `@Shortcut`
- Register the input processor as you normally would.


#### Example


```java

// makes for shorter @Shorcut:s.
import static com.badlogic.gdx.Input.Keys.*;

public class MyShortcuts extends ShortcutProcessor {
    private final World world;

    // while an artemis world isn't strictly necessary, they
    // tend to be pretty handy in this context.
    public MyShortcuts(World world) {
        // if this class had any injectable fields, now would
        // be a good time to inject
        world.inject(this);
        this.world = world;
    }

    /**
     * The entity returned by this method is passed to shortcuts which
     * require an {@link Entity} - see below.
     */
    @Override
    protected Entity getEntity() {
        // how entities are resolved (from cursor position, last selected,
        // from a list, etc) is up to you
        return world.getSystem(MyEntityTrackerSystem.class).getHoveredEntity();
    }

    // 'A' - note "import static com.badlogic.gdx.Input.Keys.*;"
    @Shortcut(A)
    private void shortcutWithNoParameters() {
        System.out.println("hi");
    }

	// MOD_ fields live in ShortcutProcessor
    @Shortcut(MOD_SHIFT | B) // left and right modifier keys are treated equally
    private void doSomethingWith(Entity e) {
        // only invoked if getEntity() != null
    }

    @Shortcut(MOD_CTRL | MOD_SHIFT | C) // combine multiple modifier keys
    private void entity_component_a(Entity owner, ComponentA componentOfOwner) {
        // only invoked if both parameters are non-null
    }

    @Shortcut(MOD_ALT | D) // same as above, but omits the Entity
    private void component_a(ComponentA comp) {
        // only invoked if comp != null
    }
}

```