/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package e.commerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author huzai
 */
public class sale extends javax.swing.JPanel {
ArrayList<String> invoice = new ArrayList<>();

DatabaseConnectivity db = new DatabaseConnectivity();
 Connection connection = DatabaseConnectivity.getConnection();
     
        ResultSet resultSet = null;
    /**

    /**
     * Creates new form sale
     */
    public sale() 
    {
        initComponents();
    initializeDatePickers();
    addDatePickersListeners();
    load();
    EditTable(jTable1);
    }
    private void initializeDatePickers() 
    {
    from.setDate(getYesterday());
    To.setDate(new Date());


    }

    private Date getYesterday()

    {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    return calendar.getTime();

    }
    private void load(){
         java.sql.Date sqlFromDate = new java.sql.Date(from.getDate().toInstant().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        java.sql.Date sqlToDate = new java.sql.Date(To.getDate().toInstant().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);

         String query = "SELECT p.Name AS ProductName, " +
                       "SUM(od.Quantity) AS QuantitySold, " +
                       "SUM(od.Quantity * od.UnitPrice) AS AmountEarned " +
                       "FROM OrderDetails od " +
                       "JOIN Product p ON od.ProductID = p.ProductID " +
                       "JOIN Orders o ON od.OrderID = o.OrderID " +
                       "WHERE o.OrderDate BETWEEN ? AND ? " +
                       "GROUP BY p.Name";
         
          try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, sqlFromDate);
            statement.setDate(2, sqlToDate);

            resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing data

            int totalQuantity = 0;
            double totalAmount = 0.0;

            while (resultSet.next()) {
                String productName = resultSet.getString("ProductName");
                int quantitySold = resultSet.getInt("QuantitySold");
                double amountEarned = resultSet.getDouble("AmountEarned");

                totalQuantity += quantitySold;
                totalAmount += amountEarned;

                model.addRow(new Object[]{productName, quantitySold, String.format("%.1f", amountEarned)});
            }

            Qty.setText(" " + totalQuantity);
            Amt.setText(String.format("%.1f", totalAmount));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void EditTable(JTable table) {
        // Center the data in each column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
          DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        

        // Get the column model and set the renderer and width for each column
        TableColumnModel columnModel = table.getColumnModel();
        
        // Set column sizes and renderer for column 1
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // Set column sizes and renderer for column 2
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // Set column sizes and renderer for column 3
        columnModel.getColumn(2).setCellRenderer(centerRenderer);
    }
//    
//public void loadData() {
//    ResultSet resultSet = null;
//    PreparedStatement preparedStatement = null;
//
//    try {
//        // Prepare SQL statement
//        String query = "SELECT * FROM orders WHERE OrderDate BETWEEN ? AND ?";
//        preparedStatement =connection.prepareStatement(query);
//
//        // Set parameters
//        java.sql.Date sqlFromDate = new java.sql.Date(from.getDate().toInstant().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
//        java.sql.Date sqlToDate = new java.sql.Date(To.getDate().toInstant().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
//
//        preparedStatement.setDate(1, sqlFromDate);
//        preparedStatement.setDate(2, sqlToDate);
//
//        // Execute query
//        resultSet = preparedStatement.executeQuery();
//
//        int quantity = 0;
//        double amount = 0;
//
//        // Process the result set (modify as needed)
//        while (resultSet.next()) {
//            quantity += resultSet.getInt("totalQty");
//            amount += resultSet.getDouble("discountedAmt");
//            invoice.add(resultSet.getString("INID"));
//
//            // Process the retrieved data as needed
//
//            // Or update your GUI components or data structures with the retrieved data
//        }
//
//        Amt.setText(String.valueOf(amount));
//        Qty.setText(String.valueOf(quantity));
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    } finally {
//        try {
//            // Close resources
//            if (resultSet != null) {
//                resultSet.close();
//            }
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//}
//
private void addDatePickersListeners() {
        from.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    
                    load();
                }
            }
        });

        To.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                  
                   load();
                }
            }
        });
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
        from = new com.toedter.calendar.JDateChooser();
        To = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Amt = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Qty = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 102, 102));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From :");

        from.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fromMouseClicked(evt);
            }
        });
        from.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fromPropertyChange(evt);
            }
        });

        To.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ToMouseClicked(evt);
            }
        });
        To.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ToPropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("To :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(To, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(To, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total Earned :");

        Amt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Amt.setForeground(new java.awt.Color(255, 255, 255));
        Amt.setText("000");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total Quantity Sold :");

        Qty.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Qty.setForeground(new java.awt.Color(255, 255, 255));
        Qty.setText("000");

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Product Name", "Quantity", "Amount Earned"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(304, 304, 304)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Qty, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Amt)
                .addGap(88, 88, 88))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Qty))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Amt))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ToPropertyChange
      if (To.getDate() != null && To.getDate().after(new Date())) {
                    // If it is, reset it to the current date
                    To.setDate(new Date());}
      
    }//GEN-LAST:event_ToPropertyChange

    private void fromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fromPropertyChange
        if (from.getDate() != null && from.getDate().after(new Date())) {
                    // If it is, reset it to the current date
                    from.setDate(new Date());}
        
    }//GEN-LAST:event_fromPropertyChange

    private void fromMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fromMouseClicked
//       loadData();
    }//GEN-LAST:event_fromMouseClicked

    private void ToMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ToMouseClicked
//       loadData();
    }//GEN-LAST:event_ToMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Amt;
    private javax.swing.JLabel Qty;
    private com.toedter.calendar.JDateChooser To;
    private com.toedter.calendar.JDateChooser from;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
