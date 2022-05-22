package it.univaq.disim.psvmsa.unify.model;

public class Admin extends User {

    public Admin(String username, String password, int id) {
        super(username, password, id);
    }

    public Admin(String username, String password) {
        super(username, password);
    }
}
