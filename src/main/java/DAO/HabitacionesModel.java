/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Habitacion;
import Util.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class HabitacionesModel {
    
      /**
     * Metodo para crear la tabla de Habitacion en la Base de Datos SQLITE
     * Usa un try-with-resources para manejar las conexiónes y que el recurso se cierre automaticamente
     * así evitamos fuga de memoria 
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * 
     * @throw  SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
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
    
     /**
     * Metodo para insertar en la tabla de Habitacion en la Base de Datos SQLITE
     * 
     * @param cant_huesped
     * (esto es la cantidad que pueden entrar en una habitacion)
     * @param reservado
     * ( nos sirve para proporcionar el estado de reservacion de la habitacion 1 : True, 0 : False )
     * @param hotel_id
     * ( Este parametro nos ayuda a asociar diferentes habitaciones con un hotel para 
     * hacer más fácil la consultas desde la base de datos)
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
    public void insertarHabitacion(int cant_huesped, int reservado, int hotel_id){
        String query = "INSERT INTO Habitacion(cant_huesped, reservado, hotel_id) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cant_huesped);
            pstmt.setInt(2, reservado);
            pstmt.setInt(3, hotel_id);
            pstmt.executeUpdate();
            System.out.println("Se ha insertado una habitacion exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudo insertar una Habitacion " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    /**
     * Metodo para Obtener Habitaciones que contengan el mismo hotel_id en la Base de Datos SQLITE
     * 
     * @param hotel_id
     * ( Este parametro nos proporciona que busque todas las habitaciones que contengan este hotel_id)
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * @return Nos retorna un List -> ArrayList de tipo Habitacion que nos va a contener todas las habitaciones
     * con el hotel_id que nosotros le proporcionamos a traves de los parametros
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
    
    public List<Habitacion> obtenerHabitacionPorHotelId(int hotel_id){
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, reservado, hotel_id FROM Habitacion where hotel_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hotel_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Habitacion habita = new Habitacion(rs.getInt("habitacion_id"), rs.getInt("cant_huesped"), rs.getInt("reservado"), rs.getInt("hotel_id"));;
                habitaciones.add(habita);
            }
            System.out.println("La consulta obtener habitaciones por id ha sido un exito");
        } catch (SQLException e) {
            System.out.println("No se pudo obtener habitaciones " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }
    
    /**
     * Metodo para Obtener Habitaciones que contengan el mismo hotel_id en la Base de Datos SQLITE
     * 
     * @param reserva
     * ( Este parametro nos proporciona que busque todas las habitaciones que tengan la reservación que pasamos)
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * @return Nos retorna un List -> ArrayList de tipo Habitacion que nos va a contener todas las habitaciones
     * con el estado de reservación que le pasamos (1 : TRUE , 0: FALSE) que nosotros le proporcionamos a traves de los parametros
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
    public List<Habitacion> obtenerHabitacionesPorReserva(int reserva) {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, reservado, hotel_id FROM Habitacion "
                + "WHERE reservado = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reserva);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Habitacion habita = new Habitacion(rs.getInt("habitacion_id"), rs.getInt("cant_huesped"), rs.getInt("reservado"), rs.getInt("hotel_id"));
                habitaciones.add(habita);
            }
            System.out.println("La consulta obtener habitaciones por reserva ha sido un exito");
        } catch (SQLException e) {
            System.out.println("No se pudo obtener habitaciones  " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }
    
    
    
}
