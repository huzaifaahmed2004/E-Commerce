/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package e.commerce;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author huzai
 */
public class Cart extends javax.swing.JPanel {
Connection connection = DatabaseConnectivity.getConnection();
 DefaultTableModel model;
 private String userID;
    /**
     * Creates new form Cart
     */
    public Cart(String UID) {
        userID=UID;
        initComponents();
        Load();
          EditTable(jTable1);
    }
private void Load(){
     int totalQuantity = 0;
    double totalPrice = 0;
    model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    // Retrieve and load the data into the model
    String query = "SELECT p.ProductID, p.Name, p.Price AS UnitPrice, ci.Quantity, (p.Price * ci.Quantity) AS TotalPrice " +
                   "FROM cart c " +
                   "INNER JOIN cartItems ci ON c.cartID = ci.CartID " +
                   "INNER JOIN Product p ON ci.ProductID = p.ProductID " +
                   "WHERE c.userID = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, userID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String PID= resultSet.getString("ProductID");
            String name = resultSet.getString("Name");
            double unitPrice = resultSet.getDouble("UnitPrice");
            int quantity = resultSet.getInt("Quantity");
            int stock=getStock(PID);
            double Price = resultSet.getDouble("TotalPrice");

            // Add a row to the model for each fetched record
            model.addRow(new Object[]{PID,name, unitPrice, quantity,stock, Price});
             totalQuantity += quantity;
            totalPrice += Price;
        }
        jLabel7.setText(totalQuantity+"");
         jLabel5.setText(totalPrice+"");
         
     EditTable(jTable1);
    
    } catch (SQLException e) {
        e.printStackTrace();}
}
public static void EditTable(JTable table) {
        // Custom renderer that centers text and sets background color based on quantity and stock
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Center the text
                setHorizontalAlignment(SwingConstants.CENTER);

                // Get the quantity and stock from the respective columns
                int quantity = (int) table.getValueAt(row, 3); // Assuming quantity is in column 3
                int stock = (int) table.getValueAt(row, 4);    // Assuming stock is in column 4

                // Check if the quantity is greater than the stock and color the whole row
                if (quantity > stock) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.WHITE);
                }

                // Ensure the text color is always black
                c.setForeground(Color.BLACK);

                return c;
            }
        };

        TableColumnModel columnModel = table.getColumnModel();

        // Set the custom renderer for all columns
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(customRenderer);
        }

        // Set column widths
        int[] columnWidths = {120, 250, 150, 140, 140, 170}; // Adjust the values based on your needs
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setMinWidth(columnWidths[i]);
            column.setMaxWidth(columnWidths[i]);
            column.setPreferredWidth(columnWidths[i]);
        }
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

private void checkQuantitiesAndPrompt(DefaultTableModel model) {
    boolean overstockFound = false;
int rowCount = model.getRowCount();
    for (int i = 0; i < rowCount; i++) {
        int quantity = (int) model.getValueAt(i, 3);
        int stock = (int) model.getValueAt(i, 4);
 String productID = model.getValueAt(i, 0).toString();
   String productName = model.getValueAt(i, 1).toString();
        if(stock==0){
                 int choice = JOptionPane.showOptionDialog(null,
                     productName + " is Out of stock",
                    "Out of Stock",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Add to Wishlist", "Cancel"},
                    "Add to Wishlist");
                  if (choice == JOptionPane.YES_OPTION) {
               addToWishlist(userID, productID);
                model.removeRow(i);
                i--; // Adjust for row removal
                 rowCount--; // Adjust total row count
                return;
            } else{
                      return;
                  }
            }else
   if (quantity > stock) {
            
            overstockFound = true;
           
          

            // Prompt the user
            int choice = JOptionPane.showOptionDialog(null,
                    "The quantity for " + productName + " exceeds available stock. What would you like to do?",
                    "Quantity Exceeds Stock",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Set Quantity to Stock", "Add to Wishlist", "Cancel"},
                    "Set Quantity to Stock");

            if (choice == JOptionPane.YES_OPTION) {
                // Set quantity to stock
                model.setValueAt(stock, i, 3);
            } else if (choice == JOptionPane.NO_OPTION) {
                // Add to wishlist and remove from cart
                addToWishlist(userID, productID);
                model.removeRow(i);
                i--; // Adjust for row removal
            } else {
                // Cancel the operation
                return;
            }
        }
    }

    if (overstockFound) {
        // Call placeOrder() again after adjustments
        placeOrder(model);
    } else {
        placeOrder(model);
    }
}
 private void addToWishlist(String userID, String productID) {
    String checkQuery = "SELECT * FROM wishlist WHERE UserID = ? AND ProductID = ?";
    String insertQuery = "INSERT INTO wishlist (UserID, ProductID) VALUES (?, ?)";
    
    try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
        checkStatement.setString(1, userID);
        checkStatement.setString(2, productID);
        
        ResultSet resultSet = checkStatement.executeQuery();
        if (resultSet.next()) {
            // Product is already in the wishlist
            removeCartItem(userID, productID);
            JOptionPane.showMessageDialog(null, "Product is already in wishlist.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, userID);
                insertStatement.setString(2, productID);
                insertStatement.executeUpdate();
                
                // Remove the product from the cart
                removeCartItem(userID, productID);
                JOptionPane.showMessageDialog(null, "Product added to wishlist.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void placeOrder(DefaultTableModel model) {
    String totalQuantity = jLabel7.getText();

    if (!totalQuantity.equals("0")) {
        String Address = askAddress();
        try {
            // Insert into order table
            String insertOrderQuery = "INSERT INTO Orders (UserID, OrderDate, TotalAmount, ShippingAddress, ExpectedShippingDate, Status) VALUES (?, CURDATE(), ?, ?, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'In transit')";
            PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            orderStatement.setString(1, userID);
            orderStatement.setString(2, jLabel5.getText());
            orderStatement.setString(3, Address);
            orderStatement.executeUpdate();

            // Get the auto-generated order ID
            int orderID;
            try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to get auto-generated order ID.");
                }
            }

            // Insert into order details table
            for (int i = 0; i < model.getRowCount(); i++) {
                String productID = model.getValueAt(i, 0).toString();
                int quantity = Integer.parseInt(model.getValueAt(i, 3).toString());
                double unitPrice = Double.parseDouble(model.getValueAt(i, 2).toString());

                String insertOrderDetailsQuery = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
                PreparedStatement orderDetailsStatement = connection.prepareStatement(insertOrderDetailsQuery);
                orderDetailsStatement.setInt(1, orderID);
                orderDetailsStatement.setString(2, productID);
                orderDetailsStatement.setInt(3, quantity);
                orderDetailsStatement.setDouble(4, unitPrice);
                orderDetailsStatement.executeUpdate();
                reduceStock(productID, quantity);
            }

            JOptionPane.showMessageDialog(null, "Order placed successfully.");

            clearCart(); // All Items will be deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(null, "Cart is empty", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void handlePlaceOrder() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    checkQuantitiesAndPrompt(model);
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setForeground(new java.awt.Color(255, 153, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Product ID", "Name", "Unit Price", "Quantity", "Availible Stock", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(255, 153, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Cart Price:");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("jLabel5");

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Order now");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Total Quantity:");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("jLabel5");

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel1.setText("Order Summary");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(54, 54, 54)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18))
        );

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Click on the product to edit it");

        jButton2.setText("Delete all items in Cart");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
handlePlaceOrder();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
clearCart();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
      int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a product to update.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    String productID = model.getValueAt(selectedRow, 0).toString(); // Assuming ProductID is in the first column
    String productName = model.getValueAt(selectedRow, 1).toString(); // Assuming Product Name is in the second column
    int oldQuantity = Integer.parseInt(model.getValueAt(selectedRow, 3).toString()); // Assuming Quantity is in the fourth column
    String newQuantityStr = JOptionPane.showInputDialog(null, "Enter new quantity for " + productName + ":");
    
    if (newQuantityStr == null) {
        // User canceled input
        return;
    }
    
    try {
        int newQuantity = Integer.parseInt(newQuantityStr);
        
        if (newQuantity == 0) {
            // Remove the item from the cart in the database
            removeCartItem(userID, productID);
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Item removed from cart.");
            return;
        }
        
        if (newQuantity < 0) {
            JOptionPane.showMessageDialog(null, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int availableStock = getStock(productID);
        
        if (newQuantity > availableStock) {
            JOptionPane.showMessageDialog(null, "Available stock is " + availableStock, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Update the quantity in the database
        updateCartItemQuantityInDatabase(userID, productID, newQuantity);
        
        // Reflect the changes in the JTable
        
        JOptionPane.showMessageDialog(null, "Item quantity updated successfully.");
          Load();
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jTable1MouseClicked

    private void removeCartItem(String userID, String productID) {
    String deleteQuery = "DELETE FROM CartItems WHERE CartID = (SELECT CartID FROM Cart WHERE UserID = ?) AND ProductID = ?";
    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
        statement.setString(1, userID);
        statement.setString(2, productID);
        statement.executeUpdate();
    
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error removing cart item from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    
    }

    }
 private void updateCartItemQuantityInDatabase(String userID, String productID, int newQuantity) {
    String updateQuery = "UPDATE CartItems SET Quantity = ? WHERE CartID = (SELECT CartID FROM Cart WHERE UserID = ?) AND ProductID = ?";
    try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
        statement.setInt(1, newQuantity);
        statement.setString(2, userID);
        statement.setString(3, productID);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error updating cart item in database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    public void clearCart() {
        try {
            // Delete from cartitems table
            String deleteCartItemsQuery = "DELETE FROM cartitems WHERE CartID IN (SELECT CartID FROM cart WHERE UserID = ?)";
            PreparedStatement cartItemsStatement = connection.prepareStatement(deleteCartItemsQuery);
            cartItemsStatement.setString(1, userID);
            cartItemsStatement.executeUpdate();

            // Delete from cart table
            String deleteCartQuery = "DELETE FROM cart WHERE UserID = ?";
            PreparedStatement cartStatement = connection.prepareStatement(deleteCartQuery);
            cartStatement.setString(1, userID);
            cartStatement.executeUpdate();
Load();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int getStock(String productID) {
        int stock = 0;
        String query = "SELECT Stock FROM Product WHERE ProductID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = resultSet.getInt("Stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }
private String askAddress(){
   String savedAddress = getSavedAddress(userID);
        if (savedAddress != null) {
            String message = "Your saved address: " + savedAddress + "\nDo you want to use this address for the order?";
            int option = JOptionPane.showConfirmDialog(null, message, "Address Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                return savedAddress;
            }
        }
        return JOptionPane.showInputDialog("Please enter your new address:");
}

 private String getSavedAddress(String userID) {
        String query = "SELECT Address FROM User WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public void reduceStock(String productID, int quantity) {
        try {
            String updateQuery = "UPDATE Product SET Stock = Stock - ? WHERE ProductID = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, quantity);
            statement.setString(2, productID);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Stock updated successfully.");
            } else {
                System.out.println("Failed to update stock.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
