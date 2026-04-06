package voting_system;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class VotingVerification extends JFrame implements ActionListener {

    JTextField voter;
    JButton check;

    VotingVerification(){

        setTitle("Voter Verification");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Background Image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("voting_bg.jpg"));
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        // Center Card Panel
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(400,220));
        card.setBackground(new Color(255,255,255,220)); // transparent white
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));

        // Title
        JLabel title = new JLabel("VOTER VERIFICATION", JLabel.CENTER);
        title.setFont(new Font("Georgia",Font.BOLD,22));
        title.setBounds(40,20,320,30);
        card.add(title);

        // Label
        JLabel label = new JLabel("Enter Voter ID");
        label.setFont(new Font("Segoe UI",Font.PLAIN,16));
        label.setBounds(40,90,120,30);
        card.add(label);

        // Text field
        voter = new JTextField();
        voter.setBounds(170,90,170,30);
        voter.setFont(new Font("Segoe UI",Font.PLAIN,16));
        voter.setBackground(new Color(255,255,255,200));
        voter.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.add(voter);

        // HD Verify Button
        check = new JButton("Verify");
        check.setBounds(140,150,120,40);
        check.setFont(new Font("Segoe UI",Font.BOLD,16));
        check.setBackground(new Color(30,144,255));
        check.setForeground(Color.WHITE);
        check.setFocusPainted(false);
        check.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));

        // Hover Effect
        check.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                check.setBackground(new Color(25,120,220));
            }
            public void mouseExited(MouseEvent e){
                check.setBackground(new Color(30,144,255));
            }
        });

        card.add(check);

        check.addActionListener(this);

        // Center card
        background.add(card);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        try{

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "select has_voted from voters where voter_id=?");

            ps.setInt(1,Integer.parseInt(voter.getText()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                if(rs.getString(1).equals("voted")){

                    JOptionPane.showMessageDialog(this,"Candidate Already done voting");

                }else{

                    new VotingPage(Integer.parseInt(voter.getText()));
                    dispose();
                }

            }else{

                JOptionPane.showMessageDialog(this,"Invalid Voter ID");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}