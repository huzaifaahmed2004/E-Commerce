/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package e.commerce;
import java.awt.Window;
import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 *
 * @author huzai
 */
public class HomePanel extends javax.swing.JPanel {
 Connection connection = DatabaseConnectivity.getConnection();
 private String category,userID,searching,sorting,filtering,categoryID;
 private static List<Window> openWindows = new ArrayList<>();

 
  Login li=new Login();
    /**
     * Creates new form HomePanel
     */
    public HomePanel(String UID,String search,String sort,String filter) {
        initComponents();
        userID=UID;
        searching=search;
        sorting=sort;
        filtering=filter;
        loadTree();
          load(category,search,sort,filter);
          
        EditTable(jTable1);
        
    }
public void load(String category, String searchKeyword, String sortBy, String filterBy) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Clear existing data

    // Base query
    String query = "SELECT p.ProductID, p.Name, p.Price, " +
                   "CONCAT(c.Name, '-', IFNULL(sc.Name, 'No Subcategory')) AS CategorySubCategory, " +
                   "AVG(r.Rating) AS Rating " +
                   "FROM Product p " +
                   
                   "LEFT JOIN sub_category sc ON p.CategoryID = sc.sub_categoryID " +"LEFT JOIN Category c ON sc.parent_categoryID = c.CategoryID " +
                   "LEFT JOIN Reviews r ON p.ProductID = r.ProductID";

    // Conditions based on user selections
    List<String> conditions = new ArrayList<>();
    List<Object> params = new ArrayList<>();

    // Category filter
    if (category != null && !category.equals("All Categories")) {
        conditions.add("sc.Name = ? and sc.parent_categoryID = ?");
        System.out.println(category+categoryID);
        params.add(category);
        params.add(categoryID);
    }

    // Search keyword filter
    if (searchKeyword != null && !searchKeyword.isEmpty()) {
        conditions.add("p.Name LIKE ?");
        params.add("%" + searchKeyword + "%");
    }

    // Rating filter
    if (filterBy != null && !filterBy.isEmpty()) {
        if (filterBy.equals("Rating 5")) {
            conditions.add("r.Rating = 5");
        } else if (filterBy.equals("Rating 4 and above")) {
            conditions.add("r.Rating >= 4");
        } else if (filterBy.equals("Rating 3 and above")) {
            conditions.add("r.Rating >= 3");
        }
    }

    // Construct the WHERE clause
    String whereClause = "";
    if (!conditions.isEmpty()) {
        whereClause = " WHERE " + String.join(" AND ", conditions);
    }

    // Construct the ORDER BY clause
    String orderByClause = "";
    if (sortBy != null && !sortBy.isEmpty()) {
        if (sortBy.equals("Price Low to high")) {
            orderByClause = " ORDER BY p.Price ASC";
        } else if (sortBy.equals("Price High to low")) {
            orderByClause = " ORDER BY p.Price DESC";
        }
    }

    // Construct the final query
    query += whereClause + " GROUP BY p.ProductID, p.Name, p.Price, c.Name, sc.Name" + orderByClause;

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        // Set parameters for prepared statement
        int paramIndex = 1;
        for (Object param : params) {
            statement.setObject(paramIndex++, param);
        }

        ResultSet resultSet = statement.executeQuery();

        // Iterate through the result set and add product information to the table model
        while (resultSet.next()) {
            int productId = resultSet.getInt("ProductID");
            String name = resultSet.getString("Name");
            String categorySubCategory = resultSet.getString("CategorySubCategory");
            double price = resultSet.getDouble("Price");
            double rating = resultSet.getDouble("Rating");

            // Add product information to the table model
            model.addRow(new Object[]{productId, name, categorySubCategory, price, rating});
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}




public void loadTree() {
    DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel(); // Assuming jTree1 is your JTree component
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Categories");
    
    String query = "SELECT c.Name AS CategoryName, sc.Name AS SubCategoryName " +
                   "FROM Category c " +
                   "LEFT JOIN sub_category sc ON c.CategoryID = sc.parent_categoryID";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        ResultSet resultSet = statement.executeQuery();
        
        // Iterate through the result set and create tree nodes
        while (resultSet.next()) {
            String categoryName = resultSet.getString("CategoryName");
            String subCategoryName = resultSet.getString("SubCategoryName");

            DefaultMutableTreeNode categoryNode = null;

            // Find or create the category node
            for (int i = 0; i < root.getChildCount(); i++) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
                if (node.getUserObject().equals(categoryName)) {
                    categoryNode = node;
                    break;
                }
            }

            // If the category node doesn't exist, create it
            if (categoryNode == null) {
                categoryNode = new DefaultMutableTreeNode(categoryName);
                root.add(categoryNode);
            }

            // Add subcategory node under the category node
            if (subCategoryName != null) {
                DefaultMutableTreeNode subCategoryNode = new DefaultMutableTreeNode(subCategoryName);
                categoryNode.add(subCategoryNode);
            }
        }

        // Refresh the tree model with the updated structure
        model.setRoot(root);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//    
// public void loadTable() {
//        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
//        model.setRowCount(0); // Clear existing data
//
//        String query = "SELECT Name FROM Category";
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            ResultSet resultSet = statement.executeQuery();
//            model.addRow(new Object[]{"All Categories"});
//            
//            // Iterate through the result set and add category names to the table model
//            while (resultSet.next()) {
//                String categoryName = resultSet.getString("Name");
//                model.addRow(new Object[]{categoryName});
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
public static void EditTable(JTable table) {
        // Center the data in each column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
          DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
 int[] columnWidths = {170, 300, 300,250,200}; // Adjust the values based on your needs
int columnIndex = 0; // Start from the first column

for (int width : columnWidths) {
    TableColumn column = table.getColumnModel().getColumn(columnIndex++);
    column.setMinWidth(width);
    column.setMaxWidth(width);
    column.setPreferredWidth(width);
}
        // Get the column model and set the renderer and width for each column
        TableColumnModel columnModel = table.getColumnModel();
        
        // Set column sizes and renderer for column 1
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // Set column sizes and renderer for column 2
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // Set column sizes and renderer for column 3
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
         columnModel.getColumn(3).setCellRenderer(centerRenderer);
 columnModel.getColumn(4).setCellRenderer(centerRenderer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProductID", "Name", "Category", "Price", "Rating"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1AncestorAdded

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int r=jTable1.getSelectedRow();
        String ID=jTable1.getValueAt(r, 0).toString();
     try {
         cartPopup(ID);
     } catch (IOException ex) {
         Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
     }

    }//GEN-LAST:event_jTable1MouseClicked
private void cartPopup(String PID) throws IOException{
             String[] options = {"View Product", "Add to Cart"};
        int choice = JOptionPane.showOptionDialog(this,
                "Choose an action:",
                "Product Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        // Handle the user's choice
        if (choice == 0) {
            // User chose "View Product"
          new UserViewProducts(PID,userID).setVisible(true);
          
        } else if (choice == 1) {
            // User chose "Add to Cart"
            if(userID=="0")
                
            {//if guest user (Raffay)
            JOptionPane.showMessageDialog(null, "Login Required", "Error", JOptionPane.ERROR_MESSAGE);
                      
                     
            }else{  addItemToCart(userID,PID);}
        }
 }


    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered

    }//GEN-LAST:event_jTable1MouseEntered

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
    
    }//GEN-LAST:event_jTree1MouseClicked

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
         DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        
        if (selectedNode == null) {
            return; // Return if no node is selected
        }

        // Get the name of the selected node
        String selectedNodeName = selectedNode.getUserObject().toString();
        
        // Check if the selected node is a category or a subcategory
        if (selectedNode.getLevel() == 0) {
          load(selectedNodeName, searching, sorting, filtering);
        } else if (selectedNode.getLevel() == 2) {
            // It's a Subcategory
            String categoryName = getParentCategory(selectedNode);  // Get the parent category name
            setCategoryIdFromDatabase( categoryName);
//            System.out.println(selectedNodeName+categoryName+categoryID);
            load(selectedNodeName, searching, sorting, filtering);  // Call load with category and subcategory
        }
      
    
    }//GEN-LAST:event_jTree1ValueChanged
 private void setCategoryIdFromDatabase(String categoryName) {
    
    String query = "SELECT CategoryID FROM Category WHERE Name = ?";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, categoryName);  // Set the category name as a parameter
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            categoryID = rs.getString("CategoryID");  // Fetch the Category ID
        }
    } catch (SQLException e) {
        System.out.println("Error fetching Category ID: " + e.getMessage());
    }

   
}
    private String getParentCategory(DefaultMutableTreeNode subCategoryNode) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) subCategoryNode.getParent();
    return parentNode != null ? parentNode.getUserObject().toString() : null;
}
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
    int cartID = getCartIDForUser();
    if (cartID == -1) {
        // Create a new cart for the user
        cartID = createCartForUser();
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

 private void addToWishlist(String userID, String productID) {
    String query = "INSERT INTO Wishlist (user_id, product_id) VALUES (?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, userID);
        statement.setString(2, productID);
        statement.executeUpdate();
        JOptionPane.showMessageDialog(null, "Product added to wishlist.", "Info", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to add product to wishlist.", "Error", JOptionPane.ERROR_MESSAGE);
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
    private int getCartIDForUser() {
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

    private int createCartForUser() {
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

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
