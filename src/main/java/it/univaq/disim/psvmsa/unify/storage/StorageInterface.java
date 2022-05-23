package it.univaq.disim.psvmsa.unify.storage;

public interface StorageInterface<T> {
    //TODO probably not needed
    public void add(String key, T value);

    public void update(String key, T value);

    public T get(String key);

    public void remove(String key);

    public void clear();

}
