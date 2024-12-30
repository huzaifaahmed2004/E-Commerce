/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package e.commerce;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author huzai
 */
public class CustomerCWR extends javax.swing.JFrame {
private String customerID,CustomerNAme;

DatabaseConnectivity db = new DatabaseConnectivity();
 Connection connection = DatabaseConnectivity.getConnection();
        PreparedStatement statement = null;
        
        ResultSet resultSet = null;
    /**
     * Creates new form CustomerCWR
     */
    public CustomerCWR(String CID,String Name) {
        customerID=CID;
        CustomerNAme=Name;
        initComponents();
        Load();
       
        this.setSize(800,500);
    }
private void Load(){
    LoadCart();
    LoadWishList();
    LoadReviews();
     EditTable(jTable1,3);//cart Table
      EditTable(jTable2,2);//Wishlist Table
       EditTable(jTable3,4);//Reviews Table
}
private void LoadCart(){
    String query = "SELECT p.Name, ci.Quantity " +
                       "FROM cart c " +
                       "JOIN cartitems ci ON c.CartID = ci.CartID " +
                       "JOIN Product p ON ci.ProductID = p.ProductID " +
                       "WHERE c.UserID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerID);
           resultSet = statement.executeQuery();

            // Update the JTable with the retrieved data
             DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
          dt.setRowCount(0);

            while (resultSet.next()) {
                
                 Vector v = new Vector();
              
              v.add(CustomerNAme);
              v.add(resultSet.getString("Name"));
              v.add(resultSet.getInt("Quantity"));
              dt.addRow(v);
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}


private void LoadWishList(){
  String query = "SELECT p.Name " +
                       "FROM WishList w " +
                       "JOIN Product p ON w.product_id = p.ProductID " +
                       "WHERE w.User_ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerID);
           resultSet = statement.executeQuery();

            // Update the JTable with the retrieved data
             DefaultTableModel dt = (DefaultTableModel) jTable2.getModel();
          dt.setRowCount(0);

            while (resultSet.next()) {
                
                 Vector v = new Vector();
              
              v.add(CustomerNAme);
              v.add(resultSet.getString("Name"));
              dt.addRow(v);
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
private void LoadReviews(){
  String query = "SELECT p.Name AS ProductName, r.Rating, r.Comment " +
                       "FROM Reviews r " +
                       "JOIN Product p ON r.ProductID = p.ProductID " +
                       "WHERE r.UserID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerID);
           resultSet = statement.executeQuery();

            // Update the JTable with the retrieved data
             DefaultTableModel dt = (DefaultTableModel) jTable3.getModel();
          dt.setRowCount(0);

            while (resultSet.next()) {
                
                 Vector v = new Vector();
              
              v.add(CustomerNAme);
              v.add(resultSet.getString("ProductName"));
                 v.add(resultSet.getString("Rating"));
                    v.add(resultSet.getString("Comment"));
              dt.addRow(v);
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

public static void EditTable(JTable table,int coloums) {
        // Center the data in each column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
          DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        int columnIndex = 0; 
        if(coloums==2){
 int[] columnWidths = {380, 400}; // Adjust the values based on your needs
 for (int width : columnWidths) {
    TableColumn column = table.getColumnModel().getColumn(columnIndex++);
    column.setMinWidth(width);
    column.setMaxWidth(width);
    column.setPreferredWidth(width);
}
        }else if(coloums==3){
 int[] columnWidths = {260, 320, 200}; // Adjust the values based on your needs
 for (int width : columnWidths) {
    TableColumn column = table.getColumnModel().getColumn(columnIndex++);
    column.setMinWidth(width);
    column.setMaxWidth(width);
    column.setPreferredWidth(width);
}
        }else if(coloums==4){
 int[] columnWidths = {170, 170, 120,318}; // Adjust the values based on your needs
 for (int width : columnWidths) {
    TableColumn column = table.getColumnModel().getColumn(columnIndex++);
    column.setMinWidth(width);
    column.setMaxWidth(width);
    column.setPreferredWidth(width);
}
        }
 // Start from the first column


        // Get the column model and set the renderer and width for each column
        TableColumnModel columnModel = table.getColumnModel();
        for(int i=0;i<coloums;i++){
             columnModel.getColumn(i).setCellRenderer(centerRenderer);

        }
        

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jTabbedPane1.setBackground(new java.awt.Color(0, 102, 102));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Customer Name", "Product Name", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(25);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Cart", jPanel2);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Customer Name", "Product Name"
            }
        ));
        jTable2.setRowHeight(25);
        jTable2.setShowGrid(true);
        jScrollPane2.setViewportView(jTable2);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("WishList", jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Customer Name", "Product Name", "Rating Given ", "Comment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setRowHeight(25);
        jTable3.setShowGrid(true);
        jScrollPane3.setViewportView(jTable3);

        jPanel4.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Reviews", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        getContentPane().add(jPanel1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
