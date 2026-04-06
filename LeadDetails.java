package voting_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LeadDetails extends JFrame {

    JButton back, logout;

    LeadDetails() {

        setTitle("Lead Person Details");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Background Image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("voting_bg.jpg"));
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Top panel for logout button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);

        logout = new JButton("Logout");
        logout.setBackground(new Color(255,80,80));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logout.setFocusPainted(false);

        logout.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        topPanel.add(logout);
        background.add(topPanel, BorderLayout.NORTH);

        // Center panel (details card)
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(450,420));
        card.setBackground(new Color(255,255,255,210)); // transparent white
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));

        // Wrapper panel to center card
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(card);

        background.add(centerWrapper, BorderLayout.CENTER);

        JLabel title = new JLabel("LEAD PERSON DETAILS", JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 24));
        title.setBounds(60,20,330,30);
        card.add(title);

        try {

            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from lead_person");

            if(rs.next()) {

                int y = 80;

                card.add(createField("Name", rs.getString("name"), y)); y+=50;
                card.add(createField("Phone", rs.getString("phone"), y)); y+=50;
                card.add(createField("Person ID", rs.getString("person_id"), y)); y+=50;
                card.add(createField("Division", rs.getString("division"), y)); y+=50;
                card.add(createField("Village", rs.getString("village"), y)); y+=50;
                card.add(createField("State", rs.getString("state"), y));

            }

        } catch(Exception e){
            e.printStackTrace();
        }

        // Back button
        back = new JButton("Back");
        back.setBounds(170,350,100,35);
        back.setBackground(new Color(30,144,255));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Segoe UI",Font.BOLD,14));
        back.setFocusPainted(false);

        back.addActionListener(e -> dispose());

        card.add(back);

        setVisible(true);
    }

    // Method to create bordered field style labels
    private JPanel createField(String label, String value, int y) {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(60,y,330,35);

        panel.setBackground(new Color(255,255,255,180));
        panel.setBorder(BorderFactory.createLineBorder(new Color(120,120,120),1));

        JLabel text = new JLabel(label + " : " + value);
        text.setFont(new Font("Segoe UI",Font.PLAIN,16));
        text.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));

        panel.add(text);

        return panel;
    }

    public static void main(String[] args) {
        new LeadDetails();
    }
}