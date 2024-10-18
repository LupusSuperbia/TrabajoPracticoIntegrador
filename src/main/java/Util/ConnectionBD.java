/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;
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
    // Creamos una instancia para ver si existe o no la conexión
    private static ConnectionBD instance;
    private Connection connection;
    
    private ConnectionBD(){
        try{
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Conexión a SQLite realizada.");
        }catch(SQLException e)
        {
            System.out.println("Error de conexión " + e.getMessage());
        }
    }
    
    public static ConnectionBD getInstance(){
        if (instance == null){
            instance = new ConnectionBD();
        } 
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                instance = null; // Opcional: permite reiniciar la conexión si es necesario
                System.out.println("Conexión a SQLite cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            } 
        }
    }
    
}
