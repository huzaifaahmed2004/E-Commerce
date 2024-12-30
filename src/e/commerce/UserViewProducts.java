/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package e.commerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import javax.swing.JOptionPane;

import java.sql.Statement;
/**
 *
 * @author huzai
 */
public class UserViewProducts extends javax.swing.JFrame {
    private String productID,userID;
Picture pic=new Picture();
 Connection connection = DatabaseConnectivity.getConnection();
   PreparedStatement statement = null;
        ResultSet resultSet = null;
    /**
     * Creates new form ViewProducts
     */
    public UserViewProducts(String PID,String UID) throws IOException {
        initComponents();
        productID=PID;
        userID=UID;
        fetchData();
    }
private void fetchData() throws IOException {
    try {
        // SQL query to retrieve product details with category name and average rating
    String sql = "SELECT  p.Name, p.Price, p.Description, p.Picture, AVG(r.Rating) AS AverageRating " +
             "FROM Product p " +
             "LEFT JOIN Reviews r ON p.ProductID = r.ProductID " +
             "WHERE p.ProductID = ? " +
             "GROUP BY p.ProductID, p.Name, p.Price, p.Description, p.Picture";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(productID)); // Set the product ID parameter

        // Execute the query
        resultSet = statement.executeQuery();

        // Process the result set
        if (resultSet.next()) {
           String name = resultSet.getString("Name");
            String des = resultSet.getString("Description");
           String price = resultSet.getString("Price");
          
          
            Blob picture = resultSet.getBlob("Picture");
            double averageRating = resultSet.getDouble("AverageRating");

            jLabel5.setText(name);
            jLabel7.setText(des);
            jLabel6.setText(price);
            jLabel9.setText(String.valueOf(averageRating)); // Assuming you have a text field to display the rating
            pic.fetchFromDB(picture, jLabel1);
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Product");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name :");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Description:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Price:");

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Add to Cart");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Order Now");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("jLabel6");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("jLabel6");

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("View Reviews");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Ratings:");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("jLabel6");

        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(29, 29, 29))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
if(userID=="0")
   { JOptionPane.showMessageDialog(null, "Login Required", "Error", JOptionPane.ERROR_MESSAGE);
      return;
      
   }else     {  addItemToCart(userID,productID);}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
if(userID=="0")
   { JOptionPane.showMessageDialog(null, "Login Required", "Error", JOptionPane.ERROR_MESSAGE);
      return;
      
   }else     {  placeOrder(userID, productID)  ;   }  
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      new ViewReviews(productID).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed
public void addItemToCart(String userID, String productID) {
    // Check if the product ID and user ID are valid
    if (productID == null || productID.isEmpty() || userID == null || userID.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Invalid user or product ID.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Prompt user for quantity
    String quantityStr = JOptionPane.showInputDialog(null, "Enter quantity:");
    if (quantityStr == null) {
        // User canceled input
        return;
    }

    // Parse the quantity input
    int quantity;
    try {
        quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(null, "Quantity must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check the stock of the product
    int stock = getStock(productID);
    if (stock <= 0) {
        JOptionPane.showMessageDialog(null, "Product is out of stock.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check if a cart exists for the user
    int cartID = getCartIDForUser(userID);
    if (cartID == -1) {
        // Create a new cart for the user
        cartID = createCartForUser(userID);
    }

    // Check if the product is already in the cart
    String checkCartQuery = "SELECT Quantity FROM CartItems WHERE CartID = ? AND ProductID = ?";
    try (PreparedStatement checkCartStatement = connection.prepareStatement(checkCartQuery)) {
        checkCartStatement.setInt(1, cartID);
        checkCartStatement.setString(2, productID);
        ResultSet resultSet = checkCartStatement.executeQuery();
        if (resultSet.next()) {
            int existingQuantity = resultSet.getInt("Quantity");
            int newQuantity = existingQuantity + quantity;

            if (newQuantity > stock) {
                int availableQuantity = stock - existingQuantity;
                int response = JOptionPane.showOptionDialog(null,
                    "Insufficient stock. You can only add " + availableQuantity + " items. Do you want to add the available quantity to the cart or add it to the wishlist?",
                    "Insufficient Stock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Add to Cart", "Add to Wishlist"},
                    "Add to Cart");

                if (response == JOptionPane.NO_OPTION) {
                    addToWishlist(userID, productID);
                    return; // Exit the method if the user chooses to add to wishlist
                } else if (response == JOptionPane.YES_OPTION && availableQuantity > 0) {
                    newQuantity = existingQuantity + availableQuantity; // Update quantity to the available stock
                } else {
                    return; // Exit the method if the stock is 0 and user chooses to add to cart
                }
            }

            // Update the quantity in the cart
            String updateCartQuery = "UPDATE CartItems SET Quantity = ? WHERE CartID = ? AND ProductID = ?";
            try (PreparedStatement updateCartStatement = connection.prepareStatement(updateCartQuery)) {
                updateCartStatement.setInt(1, newQuantity);
                updateCartStatement.setInt(2, cartID);
                updateCartStatement.setString(3, productID);
                updateCartStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item quantity updated in cart successfully.");
            }
        } else {
            if (quantity > stock) {
                int response = JOptionPane.showOptionDialog(null,
                    "Insufficient stock. You can only add " + stock + " items. Do you want to add the available quantity to the cart or add it to the wishlist?",
                    "Insufficient Stock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Add to Cart", "Add to Wishlist"},
                    "Add to Cart");

                if (response == JOptionPane.NO_OPTION) {
                    addToWishlist(userID, productID);
                    return; // Exit the method if the user chooses to add to wishlist
                } else if (response == JOptionPane.YES_OPTION && stock > 0) {
                    quantity = stock; // Update quantity to the available stock
                } else {
                    return; // Exit the method if the stock is 0 and user chooses to add to cart
                }
            }

            // Add item to the cartItems table
            String insertCartQuery = "INSERT INTO CartItems (CartID, ProductID, Quantity) VALUES (?, ?, ?)";
            try (PreparedStatement insertCartStatement = connection.prepareStatement(insertCartQuery)) {
                insertCartStatement.setInt(1, cartID);
                insertCartStatement.setString(2, productID);
                insertCartStatement.setInt(3, quantity);
                insertCartStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item added to cart successfully.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error updating cart: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


public void placeOrder(String userID,String productID) {
    // Check if the cart is empty
    
   

    String address = askAddress();
    if (address == null || address.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Invalid shipping address", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

  
   
        String quantityStr = JOptionPane.showInputDialog(null, "Enter quantity:");
        if (quantityStr == null) {
            // User canceled input
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(null, "Quantity must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int stock = getStock(productID);
            if (stock < quantity) {
                int response = JOptionPane.showOptionDialog(null, 
                    "Only " + stock + " items in stock. Do you want to order the available quantity or add it to the wishlist?",
                    "Insufficient Stock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Order Available Quantity", "Add to Wishlist"},
                    "Order Available Quantity");

                if (response == JOptionPane.NO_OPTION) {
                    addToWishlist(userID, productID);
                    return; // Exit the method if the user chooses to add to wishlist
                } else if (response == JOptionPane.YES_OPTION && stock > 0) {
                    quantity = stock; // Update quantity to the available stock
                } else {
                    return; // Exit the method if the stock is 0 and user chooses to add to cart
                }
            }

            double unitPrice = Double.parseDouble(jLabel6.getText());

            // Calculate total amount
            double totalAmount = unitPrice * quantity;

            // Insert into Orders table
            String insertOrderQuery = "INSERT INTO Orders (UserID, OrderDate, TotalAmount, ShippingAddress, ExpectedShippingDate, Status) VALUES (?, CURDATE(), ?, ?, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'In transit')";
            PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            orderStatement.setString(1, userID);
            orderStatement.setDouble(2, totalAmount);
            orderStatement.setString(3, address);
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

            // Insert into OrderDetails table
            String insertOrderDetailsQuery = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
            PreparedStatement orderDetailsStatement = connection.prepareStatement(insertOrderDetailsQuery);
            orderDetailsStatement.setInt(1, orderID);
            orderDetailsStatement.setString(2, productID);
            orderDetailsStatement.setInt(3, quantity);
            orderDetailsStatement.setDouble(4, unitPrice);
            orderDetailsStatement.executeUpdate();
            reduceStock(productID, quantity);

            JOptionPane.showMessageDialog(null, "Order placed successfully.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
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
private void addToWishlist(String userID, String productID) {
    String checkQuery = "SELECT * FROM wishlist WHERE user_id = ? AND product_id = ?";
    String insertQuery = "INSERT INTO wishlist (user_id, product_id) VALUES (?, ?)";
    
    try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
        checkStatement.setString(1, userID);
        checkStatement.setString(2, productID);
        
        ResultSet resultSet = checkStatement.executeQuery();
        if (resultSet.next()) {
           
            JOptionPane.showMessageDialog(null, "Product is already in wishlist.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, userID);
                insertStatement.setString(2, productID);
                insertStatement.executeUpdate();
                
              
                JOptionPane.showMessageDialog(null, "Product added to wishlist.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

private int getCartIDForUser(String userID) {
    int cartID = -1;
    String query = "SELECT CartID FROM Cart WHERE UserID = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, userID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            cartID = resultSet.getInt("CartID");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cartID;
}

private int createCartForUser(String userID) {
    int cartID = -1;
    String query = "INSERT INTO Cart (UserID) VALUES (?)";
    try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, userID);
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            cartID = generatedKeys.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cartID;
}

private void addCartItem(int cartID, String productID, int quantity) {
    String query = "INSERT INTO CartItems (CartID, ProductID, Quantity) VALUES (?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, cartID);
        statement.setString(2, productID);
        statement.setInt(3, quantity);
        statement.executeUpdate();
        JOptionPane.showMessageDialog(null, "Item added to cart successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error adding item to cart: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables
}