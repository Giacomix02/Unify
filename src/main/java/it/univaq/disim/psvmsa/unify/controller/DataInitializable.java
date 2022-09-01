package it.univaq.disim.psvmsa.unify.controller;

public interface DataInitializable<T> {
    default void initializeData(T data) {
    }
}
