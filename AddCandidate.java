
package voting_system;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class AddCandidate extends JFrame implements ActionListener {

    JTextField name, village, age;
    JComboBox<String> gender;
    JButton add, back;

    AddCandidate(){

        setTitle("Add Voter");
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(null);

        // Background Image
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("voting_bg.jpg")));
        background.setBounds(0,0,1920,1080);
        background.setLayout(null);
        add(background);

        // Transparent Center Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(600,250,400,400);
        panel.setBackground(new Color(255,255,255,120)); // transparent white
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
        background.add(panel);

        Font labelFont = new Font("Segoe UI",Font.BOLD,16);

        JLabel title = new JLabel("Add Voter",JLabel.CENTER);
        title.setFont(new Font("Georgia",Font.BOLD,26));
        title.setBounds(0,10,400,40);
        panel.add(title);

        // Name
        JLabel l1 = new JLabel("Name");
        l1.setBounds(40,70,100,30);
        l1.setFont(labelFont);
        panel.add(l1);

        name = new JTextField();
        name.setBounds(150,70,200,30);
        panel.add(name);

        // Village
        JLabel l2 = new JLabel("Village");
        l2.setBounds(40,110,100,30);
        l2.setFont(labelFont);
        panel.add(l2);

        village = new JTextField();
        village.setBounds(150,110,200,30);
        panel.add(village);

        // Gender
        JLabel l3 = new JLabel("Gender");
        l3.setBounds(40,150,100,30);
        l3.setFont(labelFont);
        panel.add(l3);

        gender = new JComboBox<>(new String[]{"Male","Female"});
        gender.setBounds(150,150,200,30);
        panel.add(gender);

        // Age
        JLabel l4 = new JLabel("Age");
        l4.setBounds(40,190,100,30);
        l4.setFont(labelFont);
        panel.add(l4);

        age = new JTextField();
        age.setBounds(150,190,200,30);
        panel.add(age);

        // Add Button
        add = new JButton("Add Voter");
        add.setBounds(120,250,160,40);
        add.setFont(new Font("Segoe UI",Font.BOLD,16));
        add.setBackground(new Color(0,120,215));
        add.setForeground(Color.WHITE);
        add.setFocusPainted(false);
        panel.add(add);

        add.addActionListener(this);

        // Back Button
        back = new JButton("Back");
        back.setBounds(120,310,160,40);
        back.setFont(new Font("Segoe UI",Font.BOLD,16));
        back.setBackground(new Color(180,180,180));
        back.setForeground(Color.BLACK);
        back.setFocusPainted(false);
        panel.add(back);

        back.addActionListener(e -> {
            new Dashboard(); // Open Dashboard frame
            dispose();       // Close AddCandidate frame
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        try{
            int a = Integer.parseInt(age.getText());

            if(a < 18){
                JOptionPane.showMessageDialog(this,"Voter must be at least 18 years old");
                return;
            }

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "insert into voters(name,village,gender,age) values(?,?,?,?)"
            );

            ps.setString(1,name.getText());
            ps.setString(2,village.getText());
            ps.setString(3,gender.getSelectedItem().toString());
            ps.setInt(4,a);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Candidate Added Successfully");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}