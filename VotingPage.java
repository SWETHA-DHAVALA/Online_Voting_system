package voting_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VotingPage extends JFrame implements ActionListener {

    int voterId;
    JButton p1, p2, p3;

    VotingPage(int id) {
        voterId = id;
        setTitle("Vote Here");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Background image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("voting_bg.jpg"));
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Card panel for buttons
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(400, 350));
        card.setBackground(new Color(255,255,255,220)); // semi-transparent white
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15,15,15,15);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Party Icons
        ImageIcon icon1 = new ImageIcon(getClass().getResource("whale.jpg"));
        icon1 = new ImageIcon(icon1.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("horse.jpg"));
        icon2 = new ImageIcon(icon2.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("egale.png"));
        icon3 = new ImageIcon(icon3.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        // Buttons
        p1 = createButton("Party A", icon1, new Color(153, 204, 255));
        p2 = createButton("Party B", icon2, new Color(255, 204, 153));
        p3 = createButton("Party C", icon3, new Color(204, 255, 204));

        // Add buttons vertically
        c.gridx = 0; c.gridy = 0;
        card.add(p1, c);
        c.gridy = 1;
        card.add(p2, c);
        c.gridy = 2;
        card.add(p3, c);

        // Add card to center
        gbc.gridx = 0; gbc.gridy = 0;
        background.add(card, gbc);

        // Add action listeners
        p1.addActionListener(this);
        p2.addActionListener(this);
        p3.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to create HD button
    private JButton createButton(String text, ImageIcon icon, Color bgColor) {
        JButton btn = new JButton(text, icon);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(15);

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String party = ((JButton)e.getSource()).getText();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "update parties set votes=votes+1 where party_name=?"
            );
            ps.setString(1, party);
            ps.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                "update voters set has_voted='voted' where voter_id=?"
            );
            ps2.setInt(1, voterId);
            ps2.executeUpdate();

            JOptionPane.showMessageDialog(this,"Thank you for voting");
            dispose();

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new VotingPage(1); // Example voter ID
    }
}