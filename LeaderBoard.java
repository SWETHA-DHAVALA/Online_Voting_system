
package voting_system;

import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class LeaderBoard extends JFrame {

    int votesA = 0, votesB = 0, votesC = 0;
    String winnerParty = "";
    ImageIcon winnerIcon;

    LeaderBoard(){

        setTitle("Voting LeaderBoard");
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(null);

        // Soft pastel background
        getContentPane().setBackground(Color.WHITE); 

        // Logout Button
        JButton logout = new JButton("Logout");
        logout.setBounds(20,20,120,40);
        logout.setFont(new Font("Segoe UI",Font.BOLD,15));
        logout.setBackground(new Color(255,120,120));
        logout.setForeground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,4));
        add(logout);

        logout.addActionListener(e -> dispose());

        // Title
        JLabel title = new JLabel("Voting Results",JLabel.CENTER);
        title.setFont(new Font("Georgia",Font.BOLD,34));
        title.setBounds(0,20,1600,50);
        title.setForeground(new Color(50,50,80));
        add(title);

        try{

            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement()
            .executeQuery("select * from parties");

            int y = 160;

            while(rs.next()){

                String party = rs.getString("party_name");
                int votes = rs.getInt("votes");

                ImageIcon icon = null;

                if(party.equals("Party A")){
                    votesA = votes;
                    icon = new ImageIcon(getClass().getResource("whale.jpg"));
                }

                if(party.equals("Party B")){
                    votesB = votes;
                    icon = new ImageIcon(getClass().getResource("horse.jpg"));
                }

                if(party.equals("Party C")){
                    votesC = votes;
                    icon = new ImageIcon(getClass().getResource("egale.png"));
                }

                Image img = icon.getImage().getScaledInstance(55,55,Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);

                JLabel label = new JLabel(party+" : "+votes+" votes",icon,JLabel.LEFT);
                label.setBounds(140,y,320,65);
                label.setFont(new Font("Segoe UI",Font.BOLD,19));
                label.setForeground(new Color(40,40,40));

                add(label);

                y += 85;
            }

            // Find winner
            int max = Math.max(votesA, Math.max(votesB, votesC));

            if(max == votesA){
                winnerParty = "Party A";
                winnerIcon = new ImageIcon(getClass().getResource("whale.jpg"));
            }
            else if(max == votesB){
                winnerParty = "Party B";
                winnerIcon = new ImageIcon(getClass().getResource("horse.jpg"));
            }
            else{
                winnerParty = "Party C";
                winnerIcon = new ImageIcon(getClass().getResource("egale.png"));
            }

            Image img = winnerIcon.getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH);
            winnerIcon = new ImageIcon(img);

            JLabel winnerLabel = new JLabel("Winner : "+winnerParty, winnerIcon, JLabel.CENTER);
            winnerLabel.setBounds(550,110,420,120);
            winnerLabel.setFont(new Font("Georgia",Font.BOLD,28));
            winnerLabel.setForeground(new Color(60,60,90));

            add(winnerLabel);

        }catch(Exception e){
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void paint(Graphics g){
        super.paint(g);

        int total = votesA + votesB + votesC;
        if(total == 0) return;

        int start = 0;

        int aAngle = (int)((votesA * 360.0)/total);
        int bAngle = (int)((votesB * 360.0)/total);
        int cAngle = 360 - (aAngle + bAngle);

        int x = 600;
        int y = 320;
        int size = 300;

        // --- 3D effect (shadow layer) ---
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(180,180,180));
        g2.fillOval(x+5,y+5,size,size);

        // --- Pastel Colors Pie Chart ---

        // Party A -  Blue
        g2.setColor(new Color(30,144,255));
        g2.fillArc(x,y,size,size,start,aAngle);
        start += aAngle;

        // Party B - yellow
        g2.setColor(new Color(255,215,0));
        g2.fillArc(x,y,size,size,start,bAngle);
        start += bAngle;

        // Party C - Pastel Lavender
        g2.setColor(new Color(220,20,60));
        g2.fillArc(x,y,size,size,start,cAngle);

        // ---- Legend ----

        g2.setFont(new Font("Segoe UI",Font.BOLD,16));

        g2.setColor(new Color(30,144,255));
        g2.fillRect(950,360,28,28);
        g2.setColor(Color.BLACK);
        g2.drawString("Party A",990,380);

        g2.setColor(new Color(255,215,0));
        g2.fillRect(950,410,28,28);
        g2.setColor(Color.BLACK);
        g2.drawString("Party B",990,430);

        g2.setColor(new Color(220,20,60));
        g2.fillRect(950,460,28,28);
        g2.setColor(Color.BLACK);
        g2.drawString("Party C",990,480);
    }
}
