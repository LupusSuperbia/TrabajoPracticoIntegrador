/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author estudiante
 */
public class HabitacionesModel {
    
    public void crearTabla(){
        String query = "CREATE TABLE IF NOT EXISTS Habitacion( "
                + "habitacion_id INTEGER NOT NULL PRIMARY KEY,"
                + "cant_huesped INTEGER NOT NULL, "
                + "reservado INTEGER NOT NULL,"
                + "hotel_id INTEGER NOT NULL,"
                + "FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id)"
                + ");"; 
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.execute();
            System.out.println("Se ha creado la tabla Habitacion exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudo crear la tabla Habitacion " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    public void insertarHabitacion(int cant_huesped, int reservado, int hotel_id){
        String query = "INSERT INTO Habitacion(cant_huesped, reservado, hotel_id) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cant_huesped);
            pstmt.setInt(2, reservado);
            pstmt.setInt(3, hotel_id);
            pstmt.executeUpdate();
            System.out.println("Se ha insertado una habitacion exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudo crear la tabla Habitacion " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        
    }
}
