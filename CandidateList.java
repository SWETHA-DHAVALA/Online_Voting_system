
package voting_system;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.*;

public class CandidateList extends JFrame {

    CandidateList() {

        setTitle("Candidate List");
        setSize(800,500);

        String columns[] = {"Name","Gender","Age","Voter ID","Status"};

        String data[][] = new String[100][5];

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from voters");

            int i = 0;
            while(rs.next()) {
                data[i][0] = rs.getString("name");
                data[i][1] = rs.getString("gender");
                data[i][2] = rs.getString("age");
                data[i][3] = rs.getString("voter_id");
                data[i][4] = rs.getString("has_voted");
                i++;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        JTable table = new JTable(data, columns);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(30);

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < columns.length; i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Custom renderer for Status column
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = value != null ? value.toString().toLowerCase() : "";

                if(status.equals("voted")){
                    cell.setBackground(new Color(144,238,144)); // light green
                    cell.setForeground(Color.BLACK);
                } else if(status.equals("not voted")){
                    cell.setBackground(new Color(255,102,102)); // light red
                    cell.setForeground(Color.WHITE);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }

                cell.setHorizontalAlignment(JLabel.CENTER);
                return cell;
            }
        });

        // Custom renderer for Name column based on Status
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {

                JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString().toLowerCase() : "";

                if(status.equals("voted")){
                    cell.setBackground(new Color(0,128,0)); // dark green background
                    cell.setForeground(Color.WHITE); // text white for contrast
                } else if(status.equals("not voted")){
                    cell.setBackground(new Color(255,0,0)); // red background
                    cell.setForeground(Color.WHITE);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }

                cell.setHorizontalAlignment(JLabel.CENTER);
                return cell;
            }
        });

        add(new JScrollPane(table));

        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        new CandidateList();
    }
}