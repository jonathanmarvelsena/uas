package model;

public class Transaction {
    private int id;
    private int user_id;
    private String username;
    private int game_id;
    private String gameName;
    private String price;

    public Transaction() {
    }

    public Transaction(int id, int user_id, String username, int game_id, String gameName, String price) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.game_id = game_id;
        this.gameName = gameName;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    
}
