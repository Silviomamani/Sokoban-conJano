package model.factory;

/**
 * Creator abstracto - Patrón Factory Method
 * Information Expert: Conoce cómo crear instancias de su tipo
 */
public interface GameElementFactory<T> {
    T create(CreationContext context);
    String getType();
}
