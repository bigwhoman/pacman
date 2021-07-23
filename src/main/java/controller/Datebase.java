package controller;

import model.User;

public class Datebase {
    private static Datebase datebase;
    private User user;

    private Datebase() {

    }

    public static Datebase getInstance() {
        if (datebase == null)
            datebase = new Datebase();
        return datebase;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
