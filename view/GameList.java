package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import controller.Controller;
import model.*;

public class GameList {

    private User user;

    public GameList(User user) {
        this.user = user;
        menu();
    }

    public void menu() {
        Controller con = new Controller();
        ArrayList<Game> games = con.getGames();
        JFrame menu = new JFrame("Game List");

        JButton transactionButton = new JButton("Transactions");
        transactionButton.setBounds(10, 10, 150, 30);
        transactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.dispose();
                new Transactions(user);
            }
        });

        String column[] = { "Id", "Name", "Genre", "Price", "Action" };
        DefaultTableModel model = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Game game : games) {
            int id = game.getId();
            String name = game.getName();
            String genre = game.getGenre();
            int price = game.getPrice();

            JButton buyButton = new JButton("Buy Game");
            buyButton.addActionListener(new BuyButtonActionListener(con, user, id));

            Object[] data = { id, name, genre, price, buyButton };

            model.addRow(data);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 400, 300);

        menu.add(transactionButton);
        menu.add(scrollPane);

        menu.setSize(500, 400);
        menu.setLayout(null);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class BuyButtonActionListener implements ActionListener {
        private Controller controller;
        private User user;
        private int gameId;

        public BuyButtonActionListener(Controller controller, User user, int gameId) {
            this.controller = controller;
            this.user = user;
            this.gameId = gameId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if the user already bought this game
            if (controller.checkPurchase(user.getId(), gameId)) {
                JOptionPane.showMessageDialog(null, "You've already purchased this game.", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean success = controller.insertTransaction(user.getId(), gameId);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Purchase failed. Please try again later.", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
