package it.univaq.disim.psvmsa.unify.controller;

import it.univaq.disim.psvmsa.unify.model.User;

public class UserWithData<T>{
    private User user;
    private T data;

    public UserWithData(User user, T data){
        this.user = user;
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public T getData() {
        return data;
    }
}

