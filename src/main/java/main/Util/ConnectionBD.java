/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author asamsu
 */
public class ConnectionBD {
    private static final String DATABASE_URL = "jdbc:sqlite:hotel.db";
    
    private static void connect(){
        try(Connection conn = DriverManager.getConnection(DATABASE_URL)){
            if(conn != null){
                System.out.println("Conexión a la base de datos establecida");
            }
        }catch(SQLException e)
        {
            System.out.println("Error de conexión " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        connect();
    }
}
