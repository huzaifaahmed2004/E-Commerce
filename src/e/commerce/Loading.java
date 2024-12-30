package e.commerce;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.SwingWorker;
import java.sql.SQLException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author huzai
 */
public class Loading extends javax.swing.JFrame {
 Connection connection = DatabaseConnectivity.getConnection();
    /**
     * Creates new form NewJFrame
     */
    public Loading() {
        initComponents();
        worker.execute();
        shipOrders();
    }
private void shipOrders(){
    String updateQuery = "UPDATE Orders " +
                         "SET Status = 'Completed' " +
                         "WHERE ExpectedShippingDate <= CURDATE() AND Status = 'In transit'";
    
     String insertQuery = "INSERT INTO notifications (OrderID, UserID, notificationSent) " +
                        "SELECT o.OrderID, o.UserID, 0 " +
                        "FROM Orders o " +
                        "LEFT JOIN notifications n ON o.OrderID = n.OrderID " +
                        "WHERE ExpectedShippingDate <= CURDATE() AND o.Status = 'Completed' " +
                        "AND n.OrderID IS NULL";

    try {
        // Update orders status
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            int rowsAffected = updateStatement.executeUpdate();
            System.out.println(rowsAffected + " orders updated to 'Completed' status.");
        } catch (SQLException e) {
          
        }
        
        // Insert into notifications for shifting orders
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            int rowsInserted = insertStatement.executeUpdate();
            System.out.println(rowsInserted + " notifications inserted for shifting orders.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (Exception e) {
        e.printStackTrace();
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
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Loading");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 204));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo-removebg-preview.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BROWSEBAZAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Loading().setVisible(true);
            }
        });
    }
    
 SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
        @Override
        protected Void doInBackground() throws Exception {
            // Simulate a task by updating progress in a loop
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(25); // Simulate time-consuming task
                publish(i); // Publish progress
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Get the most recent value from chunks and set the progress bar
            int progress = chunks.get(chunks.size() - 1);
            jProgressBar1.setValue(progress);
        }

        @Override
        protected void done() {
            // Close the loading screen
            Loading.this.dispose();
            // Show the main application window
            new UserHomePage("0").setVisible(true);
        }
    };
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}