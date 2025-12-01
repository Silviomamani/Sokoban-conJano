
package model.factory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Optional;

public class GameElementRegistry<T> {
    private final Map<String, GameElementFactory<T>> factories;
    private final GameElementFactory<T> defaultFactory;

    public GameElementRegistry(GameElementFactory<T> defaultFactory) {
        this.factories = new ConcurrentHashMap<>();
        this.defaultFactory = defaultFactory;
    }


    public void register(GameElementFactory<T> factory) {
        if (factory == null || factory.getType() == null) {
            throw new IllegalArgumentException("Factory and type cannot be null");
        }
        factories.put(factory.getType().toLowerCase(), factory);
    }

    public GameElementFactory<T> getFactory(String type) {
        return Optional.ofNullable(type)
                .map(String::toLowerCase)
                .map(factories::get)
                .orElse(defaultFactory);
    }

    public T create(String type, CreationContext context) {
        return getFactory(type).create(context);
    }


    public boolean isRegistered(String type) {
        return type != null && factories.containsKey(type.toLowerCase());
    }
}