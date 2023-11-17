package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;
import model.User;

public class Login {
    public Login() {
        menu();
    }

    public void menu() {
        Controller con = new Controller();
        JFrame menu = new JFrame("Login");

        JLabel email = new JLabel("Email: ");
        email.setBounds(10, 10, 100, 30);
        JTextField inputEmail = new JTextField();
        inputEmail.setBounds(120, 10, 200, 30);

        JLabel password = new JLabel("Password: ");
        password.setBounds(10, 40, 100, 30); 
        JPasswordField inputPassword = new JPasswordField();
        inputPassword.setBounds(120, 40, 200, 30); 

        JButton login = new JButton("Login");
        login.setBounds(150, 200, 80, 20);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                String password = String.valueOf(inputPassword.getPassword());
                User user = con.getUser(inputEmail.getText(), password);
                if (user != null) {
                    menu.dispose();
                    new GameList(user);
                } else {
                    JOptionPane.showMessageDialog(menu, "Email or password incorrect", "User not found",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JPanel panel = new JPanel(null);

        panel.add(email);
        panel.add(inputEmail);
        panel.add(password);
        panel.add(inputPassword);
        panel.add(login);

        menu.add(panel);
        menu.setSize(1920, 1080);
        menu.setVisible(true);
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
