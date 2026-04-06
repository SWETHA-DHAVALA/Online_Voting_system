
package voting_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    JButton lead, list, vote, leader, add, logout;

    Dashboard() {

        setTitle("Dashboard - Online Voting System");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background Image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("voting_bg.jpg"));
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        // Transparent white panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450,420));
        panel.setBackground(new Color(255,255,255,200)); // transparent white
        panel.setLayout(new GridLayout(7,1,15,15));
        panel.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

        background.add(panel);

        // Title
        JLabel title = new JLabel("WELCOME....!", JLabel.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 26));
        title.setForeground(Color.BLACK);
        panel.add(title);

        // Buttons
        lead = createButton("Booth Member Details", new Color(102,178,255));
        list = createButton("Voter List", new Color(102,178,255));
        vote = createButton("Voting", new Color(102,255,178));
        leader = createButton("Leader Board", new Color(255,204,102));
        add = createButton("Add Voter", new Color(255,153,153));
        logout = createButton("Logout", new Color(255,102,102));

        panel.add(lead);
        panel.add(list);
        panel.add(vote);
        panel.add(leader);
        panel.add(add);
        panel.add(logout);

        // Action Listeners
        lead.addActionListener(this);
        list.addActionListener(this);
        vote.addActionListener(this);
        leader.addActionListener(this);
        add.addActionListener(this);
        logout.addActionListener(this);

        setVisible(true);
    }

    // Button design
    private JButton createButton(String text, Color bgColor) {

        JButton btn = new JButton(text);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);

        // Border around button
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80,80,80),2),
                BorderFactory.createEmptyBorder(10,20,10,20)
        ));

        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==lead) new LeadDetails();

        if(e.getSource()==list) new CandidateList();

        if(e.getSource()==vote) new VotingVerification();

        if(e.getSource()==leader) new LeaderBoard();

        if(e.getSource()==add) new AddCandidate();

        if(e.getSource()==logout) {
            new LoginPage();
            dispose();
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}