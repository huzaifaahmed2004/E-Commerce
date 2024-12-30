/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package e.commerce;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author huzai
 */
public class DatabaseConnectivity {
//public static void main(String[] args){
//        getConnection();
//    }
   
    public static Connection getConnection() {
      Connection connection = null;
        
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/e-commerce", "root", "H1u2Z3a4I5.");
    
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to connect to the database");
            e.printStackTrace();
        }
        
        return connection;
    }
    
}

    
    
