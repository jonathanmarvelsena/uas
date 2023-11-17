package controller;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;
import view.Transactions;

public class Controller {

    static DatabaseHandler conn = new DatabaseHandler();

    // public static boolean findUser(String email, String password){
    //     conn.connect();
    //     boolean found = false;
    //     String query = "Select * from users WHERE email = '"+email +"'' AND password = '" +password + "'";
    //     try {
    //         Statement stmt = conn.con.createStatement();
    //         ResultSet rs = stmt.executeQuery(query);
    //         while (rs.next()) {
    //             found = true;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();

    //     }
    //     return found;
    // }

    public static User getUser(String email, String password) {
        conn.connect();
        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (PreparedStatement preparedStatement = conn.con.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
    
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<Game> getGames(){
        conn.connect();
        String query = "SELECT * FROM game";
        ArrayList<Game> games = new ArrayList<>();
        try{
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setName(rs.getString("name"));
                game.setGenre(rs.getString("genre"));
                game.setPrice(rs.getInt("price"));

                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public boolean checkPurchase(int userId, int gameId) {
        conn.connect();
        boolean hasPurchased = false;
        String query = "SELECT * FROM transactions WHERE user_id = ? AND game_id = ?";

        try (PreparedStatement preparedStatement = conn.con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, gameId);
            ResultSet rs = preparedStatement.executeQuery();

            hasPurchased = rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasPurchased;
    }

    public boolean insertTransaction(int userId, int gameId) {
        conn.connect();
        boolean success = false;

        try {
            if (!checkPurchase(userId, gameId)) {
                String insertQuery = "INSERT INTO transactions (user_id, game_id) VALUES (?, ?)";
                try (PreparedStatement insertStatement = conn.con.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, userId);
                    insertStatement.setInt(2, gameId);
                    int rowsAffected = insertStatement.executeUpdate();

                    success = rowsAffected > 0;
                }
            } else {
                success = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    // public ArrayList<Transaction> getTransactions(User user){
    //     conn.connect();
    //     String query = "SELECT * FROM transactions WHERE user_id = '" + user.getId()+ "'";
    //     ArrayList<Transaction> transactions = new ArrayList<>();
    //     try{
    //         Statement stmt = conn.con.createStatement();
    //         ResultSet rs = stmt.executeQuery(query);
    //         while (rs.next()) {
    //             Transaction transaction = new Transaction();
    //             transaction.setId(rs.getInt("id"));
    //             transaction.setUser(rs.getInt("user_id"));
    //             transaction.setGame(rs.getInt("game_id"));

    //             transactions.add(transaction);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return transactions;
    // }

    public ArrayList<Transaction> getTransactions(User user) {
        conn.connect();
        String query = "SELECT transactions.id, transactions.user_id, users.name, transactions.game_id, games.name, games.price\n"
                + "FROM transactions\n"
                + "JOIN users ON users.id = transactions.user_id\n"
                + "JOIN games ON games.id = transactions.game_id\n"
                + "WHERE transactions.user_id = '" + user.getId() + "'";
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Transaction tr = new Transaction();
                tr.setId(rs.getInt("transactions.id"));
                tr.setUser_id(rs.getInt("transactions.user_id"));
                tr.setUsername(rs.getString("users.name"));
                tr.setGame_id(rs.getInt("transactions.game_id"));
                tr.setGameName(rs.getString("games.name"));
                tr.setPrice(rs.getString("games.price"));

                transactions.add(tr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (transactions);
    }
}
