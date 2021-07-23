package model;

import java.util.ArrayList;

public class User implements Comparable<User> {
    private static final ArrayList<User> allUsers;
    private final String username;
    private String password;
    private int score;
    private int rank;
    private ArrayList<Cell[][]> maps;

    static {
        allUsers = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        maps = new ArrayList<>();
        allUsers.add(this);
    }

    public User(String username, String password, int score) {
        this.username = username;
        this.password = password;
        this.score = score;
        allUsers.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public static void deleteUser(User user) {
        allUsers.remove(user);
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public int getScore() {
        return score;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public ArrayList<Cell[][]> getMaps() {
        return maps;
    }

    public void addToMaps(Cell[][] map) {
        if (!maps.contains(map))
            maps.add(map);
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(User b) {
        if (b.getScore() < score)
            return -1;
        else if (b.getScore() > score)
            return 1;
        else
            return username.compareTo(b.username);
    }
}
