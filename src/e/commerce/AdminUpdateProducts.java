/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package e.commerce;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
/**
 *
 * @author huzai
 */
public class AdminUpdateProducts extends javax.swing.JFrame {
private String productID,subCategory,subCategoryID,categoryID,name,des,price,stock,category,path;
boolean flag;
 Picture pic=new Picture();
  Validator valid=new Validator();
     DatabaseConnectivity db = new DatabaseConnectivity();
 Connection connection = DatabaseConnectivity.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    /**
     * Creates new form AdminUpdateProducts
     */
    public AdminUpdateProducts(String PID) throws IOException {
        initComponents();
        productID=PID;
        LoadCategory();
        loadSubCategory();
        fetchData();
        
    }
    private void loadSubCategory(){
     try {
          fetchCategoryID();
          Statement s= connection.createStatement();
          
          ResultSet rs = s.executeQuery("SELECT * FROM sub_category where parent_categoryID Like "+categoryID);
          Vector v = new Vector();
          
          while (rs.next()) {   
              v.add(rs.getString("Name"));
              
              DefaultComboBoxModel com = new DefaultComboBoxModel(v);
              Category1.setModel(com);
               
          }
          
           
      } catch (SQLException e) {
            System.out.println(e);
      }
}
    
private void fetchData() throws IOException{
    try {
           
            // SQL query to retrieve product details with category name
            String sql = "SELECT p.ProductID, p.CategoryID, p.Name, p.Description, p.Price, p.Stock, " +
             "c.Name AS CategoryName, sc.sub_categoryID, sc.Name AS subCategoryName, p.Picture " +
             "FROM Product p " +
             "INNER JOIN sub_Category sc ON p.CategoryID = sc.sub_categoryID " +
             "LEFT JOIN category c ON c.categoryID = sc.parent_categoryID " +
             "WHERE p.ProductID = ?";


            statement = connection.prepareStatement(sql);
            statement.setString(1, productID); // Set the product ID parameter

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
              
                 name = resultSet.getString("Name");
               des= resultSet.getString("Description");
                 price = resultSet.getString("Price");
                 stock = resultSet.getString("Stock");
                category = resultSet.getString("CategoryName");
                categoryID= resultSet.getString("CategoryID");
                subCategory = resultSet.getString("subCategoryName");
                  subCategoryID= resultSet.getString("sub_CategoryID");
                Blob picture = resultSet.getBlob("Picture");

             jTextField6.setText(productID);
             jTextField1.setText(name);
              jTextArea1.setText(des);
               jTextField2.setText(price);
                jTextField3.setText(stock);
                Category.setSelectedItem(category);
                Category1.setSelectedItem(subCategory);
                pic.fetchFromDB(picture, jLabel1);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } 
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Category = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        Category1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Products");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jButton1.setText("Upload new Photo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Description:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Enter Product's new Details");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Unit Price:");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Availible Stock:");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Category:");

        Category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category" }));
        Category.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CategoryItemStateChanged(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Update Product");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ID:");

        jTextField6.setEditable(false);
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Sub-Category:");

        Category1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Sub-Category" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(6, 6, 6))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel7))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(Category, 0, 323, Short.MAX_VALUE)
                                            .addComponent(jTextField3)
                                            .addComponent(jTextField2)
                                            .addComponent(Category1, 0, 323, Short.MAX_VALUE))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(87, 87, 87)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(11, 11, 11))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jButton2)
                        .addGap(111, 111, 111)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE))
                            .addComponent(jLabel3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(1, 1, 1)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Category1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed
 private void fetchSubCategoryID(){
    try {
        String subCategory=Category1.getSelectedItem().toString();
            // Get database connection
            connection = DatabaseConnectivity.getConnection();
 ResultSet resultSet = null;
            // Query to fetch category ID based on category name
            String categoryIdQuery = "SELECT sub_CategoryID FROM Sub_Category WHERE Name = ?";
            statement = connection.prepareStatement(categoryIdQuery);
            statement.setString(1, subCategory);
            resultSet = statement.executeQuery();

            // Check if the category exists
            if (resultSet.next()) {
                subCategoryID = resultSet.getString("sub_CategoryID");
            }}catch(SQLException e){
                System.out.println(e);
            }
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   try {
       flag =true;
           path= pic.uploadPic(jLabel1);
        } catch (IOException ex) {
            Logger.getLogger(AdminAddProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       name = jTextField1.getText();
des = jTextArea1.getText();
price = jTextField2.getText();
stock = jTextField3.getText();
category = Category.getSelectedItem().toString();
fetchSubCategoryID();
if(valid.isDouble(price)&&valid.isInteger(stock)){
try {
    // Get database connection
    connection = DatabaseConnectivity.getConnection();
    if(flag){
    // SQL query to update product details
    String sql = "UPDATE Product SET Name = ?, Description = ?, Price = ?, Stock = ?, CategoryID = ?, Picture = ? WHERE ProductID = ?";
    statement = connection.prepareStatement(sql);
    statement.setString(1, name);
    statement.setString(2, des);
    statement.setString(3, price);
    statement.setString(4, stock);
    statement.setString(5, subCategoryID);
    statement.setBlob(6, pic.SaveToDB(path)); // Assuming pic.SaveToDB() returns a Blob or byte array
    statement.setString(7, productID); // Assuming productId is the ID of the product to be updated

    // Execute the update query
    int rowsUpdated = statement.executeUpdate();

    if (rowsUpdated > 0) {
        JOptionPane.showMessageDialog(null, "Product updated successfully.");
       
    } else {
        JOptionPane.showMessageDialog(null, "Failed to update product.");
    }}else{
         String sql = "UPDATE Product SET Name = ?, Description = ?, Price = ?, Stock = ?, CategoryID = ? WHERE ProductID = ?";
    statement = connection.prepareStatement(sql);
    statement.setString(1, name);
    statement.setString(2, des);
    statement.setString(3, price);
    statement.setString(4, stock);
    statement.setString(5, subCategoryID);
    statement.setString(6, productID); // Assuming productId is the ID of the product to be updated

    // Execute the update query
    int rowsUpdated = statement.executeUpdate();

    if (rowsUpdated > 0) {
        JOptionPane.showMessageDialog(null, "Product updated successfully.");
             try {
                 fetchData();
             } catch (IOException ex) {
                 Logger.getLogger(AdminUpdateProducts.class.getName()).log(Level.SEVERE, null, ex);
             }
    } else {
        JOptionPane.showMessageDialog(null, "Failed to update product.");
    }
    
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
} catch (FileNotFoundException ex) {
    Logger.getLogger(AdminAddProduct.class.getName()).log(Level.SEVERE, null, ex);
}}else{//incorrect values
      JOptionPane.showMessageDialog(null, "please enter integer values of stock and price");
}

    }//GEN-LAST:event_jButton2ActionPerformed

    private void CategoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CategoryItemStateChanged
 loadSubCategory();
 // TODO add your handling code here:
    }//GEN-LAST:event_CategoryItemStateChanged
private void fetchCategoryID(){
    try {
        category=Category.getSelectedItem().toString();
            // Get database connection
            connection = DatabaseConnectivity.getConnection();
 ResultSet resultSet = null;
            // Query to fetch category ID based on category name
            String categoryIdQuery = "SELECT CategoryID FROM Category WHERE Name = ?";
            statement = connection.prepareStatement(categoryIdQuery);
            statement.setString(1, category);
            resultSet = statement.executeQuery();

            // Check if the category exists
            if (resultSet.next()) {
                categoryID = resultSet.getString("CategoryID");
            }}catch(SQLException e){
                System.out.println(e);
            }
}
   
    private void LoadCategory(){
     try {
          
          Statement s= connection.createStatement();
          
          ResultSet rs = s.executeQuery("SELECT * FROM Category");
          Vector v = new Vector();
          
          while (rs.next()) {   
              v.add(rs.getString("Name"));
              
              DefaultComboBoxModel com = new DefaultComboBoxModel(v);
              Category.setModel(com);
               
          }
          
           
      } catch (SQLException e) {
            System.out.println(e);
      }}
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//       
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                
//                try {
//                    new AdminUpdateProducts("6").setVisible(true);
//                } catch (IOException ex) {
//                    Logger.getLogger(AdminUpdateProducts.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Category;
    private javax.swing.JComboBox<String> Category1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
