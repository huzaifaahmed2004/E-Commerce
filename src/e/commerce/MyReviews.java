/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package e.commerce;

import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.Date; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author huzai
 */
public class MyReviews extends javax.swing.JPanel {
 Connection connection = DatabaseConnectivity.getConnection();
 String userID;
    /**
     * Creates new form MyReviews
     */
    public MyReviews(String UID) {
        initComponents();
       userID=UID;
       loadOrders();
       loadReviewed();
       EditTable(jTable1 ,0);
       EditTable(jTable2 ,1);
    }
  private void loadOrders() {
          DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing data
            
        String query = "SELECT o.OrderID,od.OrderDetailID ,p.Name, p.Price " +
                       "FROM Orders o " +
                       "JOIN OrderDetails od ON o.OrderID = od.OrderID " +
                       "LEFT JOIN Reviews r ON od.OrderDetailID = r.OrderDetailsID " +
                       "JOIN Product p ON od.ProductID = p.ProductID " +
                       "WHERE o.Status = 'Completed' AND r.OrderDetailsID IS NULL AND o.UserID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int orderdeatilID=resultSet.getInt("OrderDetailID");
                String productName = resultSet.getString("Name");
                double productPrice = resultSet.getDouble("Price");
                model.addRow(new Object[]{orderID,orderdeatilID, productName, productPrice});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load orders.", "Error", JOptionPane.ERROR_MESSAGE);
        }}
private void loadReviewed(){
   
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0); // Clear existing data

    String query = "SELECT o.OrderID, p.Name AS ProductName, r.Rating, r.Comment " +
                   "FROM Reviews r " +
                   "JOIN OrderDetails od ON r.OrderDetailsID = od.OrderDetailID " +
                   "JOIN Orders o ON od.OrderID = o.OrderID " +
                   "JOIN Product p ON od.ProductID = p.ProductID"
            + " where o.UserID = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        
          statement.setString(1, userID);
          ResultSet resultSet = statement.executeQuery();

        // Iterate over the result set and add rows to the DefaultTableModel
        while (resultSet.next()) {
            int orderID = resultSet.getInt("OrderID");
            String productName = resultSet.getString("ProductName");
            int rating = resultSet.getInt("Rating");
            String comment = resultSet.getString("Comment");

            model.addRow(new Object[]{orderID, productName, rating, comment});
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static void EditTable(JTable table,int x) {
        // Center the data in each column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
          DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
       if(x==0){ 
 int[] columnWidths = {270,270, 360, 290}; // Adjust the values based on your needs
int columnIndex = 0; // Start from the first column

for (int width : columnWidths) {
    TableColumn column = table.getColumnModel().getColumn(columnIndex++);
    column.setMinWidth(width);
    column.setMaxWidth(width);
    column.setPreferredWidth(width);
}}else{
           int[] columnWidths = {220,270, 220, 470}; // Adjust the values based on your needs
int columnIndex = 0; // Start from the first column

for (int width : columnWidths) {
    TableColumn column = table.getColumnModel().getColumn(columnIndex++);
    column.setMinWidth(width);
    column.setMaxWidth(width);
    column.setPreferredWidth(width);
       }}
        // Get the column model and set the renderer and width for each column
        TableColumnModel columnModel = table.getColumnModel();
        
        // Set column sizes and renderer for column 1
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // Set column sizes and renderer for column 2
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // Set column sizes and renderer for column 3
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
         columnModel.getColumn(3).setCellRenderer(centerRenderer);
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
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("My Reviews");

        jTabbedPane1.setBackground(new java.awt.Color(102, 102, 102));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Order Number", "Sub Order Number", "Product Name", "Prouct Price "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("To review", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Order Number", "Product Name", "Rating Given ", "Comment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setRowHeight(30);
        jTable2.setShowGrid(true);
        jScrollPane2.setViewportView(jTable2);

        jTabbedPane1.addTab("History", jScrollPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedRowIndex = jTable1.getSelectedRow();
        String ratingString = JOptionPane.showInputDialog(null, "Please rate from 1 to 5:");
        
        // Convert rating to integer
        int rating = Integer.parseInt(ratingString);
        
        // Check if rating is within range
        if (rating >= 1 && rating <= 5) {
            // Prompt user for comment
            String comment = JOptionPane.showInputDialog(null, "Optional comment (if any):");
          String productName = (String) jTable1.getValueAt(selectedRowIndex, 2); // Assuming product name is at index 0

    // Fetch the ProductID corresponding to the product name from the database
    int productID = getProductIDFromDatabase(productName);
    String orderDetailsID =  jTable1.getValueAt(selectedRowIndex, 1).toString(); // Assuming OrderDetailsID is at index 1
  
    String query = "INSERT INTO Reviews (OrderDetailsID, ProductID, UserID, Rating, Comment, ReviewDate) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        // Set the values for the parameters in the prepared statement
        statement.setString(1, orderDetailsID);
        statement.setInt(2, productID);
        statement.setString(3, userID);
        statement.setInt(4, rating);
        statement.setString(5, comment);
        statement.setDate(6, new Date(System.currentTimeMillis())); // Use the current date as the ReviewDate

        // Execute the SQL INSERT statement
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Review added successfully.");
            // Optionally, you can refresh the jTable to reflect the changes
            loadOrders();
       loadReviewed();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add review.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
            
        } else {
            // If rating is out of range, show an error message
            JOptionPane.showMessageDialog(null, "Invalid rating! Please rate from 1 to 5.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTable1MouseClicked
private int getProductIDFromDatabase(String productName) {
    int productID = -1;
    String query = "SELECT ProductID FROM Product WHERE Name = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, productName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            productID = resultSet.getInt("ProductID");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return productID;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
