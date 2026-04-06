
package voting_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    JTextField user;
    JPasswordField pass;
    JButton login;

    LoginPage() {

        setTitle("Online Voting System");

        // Background Image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("voting_bg.jpg"));
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        // Login Panel (Center Box)
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350,250));
        panel.setBackground(new Color(255,255,255,230));
        panel.setLayout(null);

        JLabel title = new JLabel("Booth Member Login");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setBounds(90,10,200,30);
        panel.add(title);

        JLabel l1 = new JLabel("Username");
        l1.setBounds(40,60,100,25);
        panel.add(l1);

        user = new JTextField();
        user.setBounds(150,60,150,25);
        panel.add(user);

        JLabel l2 = new JLabel("Password");
        l2.setBounds(40,100,100,25);
        panel.add(l2);

        pass = new JPasswordField();
        pass.setBounds(150,100,150,25);
        panel.add(pass);

        login = new JButton("Login");
        login.setBounds(120,160,100,35);
        login.setBackground(new Color(30,144,255));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        panel.add(login);

        background.add(panel);

        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {

            Connection con = DBConnection.getConnection();

            String q="select * from lead_person where username=? and password=?";

            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1,user.getText());
            ps.setString(2,String.valueOf(pass.getPassword()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                new Dashboard();
                dispose();

            } else {

                JOptionPane.showMessageDialog(this,"Invalid Login");

            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}