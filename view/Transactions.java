package view;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import model.*;

public class Transactions {
    public Transactions(User user) {
        menu(user);
    }

    public void menu(User user) {
        Controller con = new Controller();
        ArrayList<Transaction> transactions = con.getTransactions(user);
        JFrame menu = new JFrame("Transactions");

        String column[] = { "Id", "Name", "Genre", "Price", "Action" };
        DefaultTableModel model = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Transaction transaction : transactions) {
            int id = transaction.getId();
            int userid = transaction.getUser_id();
            int gameid = transaction.getGame_id();

            Object[] data = { id, userid, user.getName(), gameid, };

            model.addRow(data);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 400, 300);

        menu.add(scrollPane);

        menu.setSize(500, 400);
        menu.setLayout(null);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
