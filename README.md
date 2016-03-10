## kbdluv-artemis

Easy shortcuts for libgdx/artemis.


#### Usage

- Extend ShortcutProcessor
- Implement `getEntity()`
 - `getEntity` can return null if no shortcut methods take an Entity
   or Component parameter
- Create a method, annotate it with `@Shortcut`. Method can take 4 forms:
  1. `zeroarguments()`
  1. `withEntity(Entity e)` (see getEntity())
  1. `withEntityAndComponent(Entity e, Position posiiton)`
  1. `withComponent(Position posiiton)`
- Register the input processor as you normally would.


## Getting Started

#### Maven
```xml
<dependency>
	<groupId>net.onedaybeard.kbdluv</groupId>
	<artifactId>kbdluv-artemis</artifactId>
	<version>0.1.1</version>
</dependency>
```

#### Gradle
```groovy
  dependencies { compile "net.onedaybeard.artemis:kbdluv-artemis:0.1.1" }
```

#### Example

Method parameters for shortcuts are guaranteed to never be null.
If any parameter can't be inferred, the shortcut method is not
invoked.


```java

// makes for shorter @Shorcut:s.
import static com.badlogic.gdx.Input.Keys.*;

public class MyShortcuts extends ShortcutProcessor {

    // shortcut processors are auto-wiring
    private ComonentMapper<Position> positionMapper;
    private TagManager tagManager;

    public MyShortcuts(World world) {
        super(world);
    }

    /**
     * The entity returned by this method is passed to shortcuts which
     * require an {@link Entity} - see below.
     */
    @Override
    protected Entity getEntity() {
        // how entities are resolved is up to you is up to you
        return world.getSystem(FooBarEntityTrackerSystem.class).getHoveredEntity();
    }

    // 'A' - note "import static com.badlogic.gdx.Input.Keys.*;"
    @Shortcut(A)
    private void shortcutWithNoParameters() {
        // ...
    }

	// MOD_ fields live in ShortcutProcessor
    @Shortcut(MOD_SHIFT | B) // left and right modifier keys are treated equally
    private void doSomethingWith(Entity e) {
        // ...
    }

    @Shortcut(MOD_CTRL | MOD_SHIFT | C) // combine multiple modifier keys
    private void entityAndComponent(Entity owner, ComponentA componentOfOwner) {
        // ...
    }

    @Shortcut(MOD_ALT | D) // same as above, but omits the Entity
    private void justTheComponent(ComponentA comp) {
        // ...
    }
}

```
