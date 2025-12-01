package model.factory;


public interface GameElementFactory<T> {
    T create(CreationContext context);
    String getType();
}
