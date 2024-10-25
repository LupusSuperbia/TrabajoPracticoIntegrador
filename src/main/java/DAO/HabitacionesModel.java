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
     * Metodo para crear la tabla de Habitacion en la Base de Datos SQLITE Usa
     * un try-with-resources para manejar las conexiónes y que el recurso se
     * cierre automaticamente así evitamos fuga de memoria El uso de
     * PreparedStatement evita inyecciones de SQL y así mantener seguridad.
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Habitacion( "
                + "habitacion_id INTEGER NOT NULL PRIMARY KEY,"
                + "cant_huesped INTEGER NOT NULL, "
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
     * @param cant_huesped (esto es la cantidad que pueden entrar en una
     * habitacion)
     * @param reservado ( nos sirve para proporcionar el estado de reservacion
     * de la habitacion 1 : True, 0 : False )
     * @param hotel_id ( Este parametro nos ayuda a asociar diferentes
     * habitaciones con un hotel para hacer más fácil la consultas desde la base
     * de datos) El uso de PreparedStatement evita inyecciones de SQL y así
     * mantener seguridad.
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void insertarHabitacion(int cant_huesped, int hotel_id) {
        String query = "INSERT INTO Habitacion(cant_huesped, hotel_id) VALUES (?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cant_huesped);
            pstmt.setInt(2, hotel_id);
            pstmt.executeUpdate();
            System.out.println("Se ha insertado una habitacion exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudo insertar una Habitacion " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    // Utils
    public List<Habitacion> procesarHabitacion(ResultSet rs) throws SQLException{
        List<Habitacion> habitaciones = new ArrayList<>();
         while (rs.next()) {
                Habitacion habitacion = new Habitacion(rs.getInt("habitacion_id"),
                        rs.getInt("cant_huesped"),
                        rs.getInt("hotel_id"));
                habitaciones.add(habitacion);
            }
         return habitaciones;
    }
    

    /**
     * Metodo para Obtener Habitaciones que contengan el mismo hotel_id en la
     * Base de Datos SQLITE
     *
     * @param hotel_id ( Este parametro nos proporciona que busque todas las
     * habitaciones que contengan este hotel_id) El uso de PreparedStatement
     * evita inyecciones de SQL y así mantener seguridad.
     * @return Nos retorna un List -> ArrayList de tipo Habitacion que nos va a
     * contener todas las habitaciones con el hotel_id que nosotros le
     * proporcionamos a traves de los parametros
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public List<Habitacion> obtenerHabitacionesPorHotelId(int hotel_id) {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, hotel_id FROM Habitacion where hotel_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hotel_id);
            ResultSet rs = pstmt.executeQuery();
            habitaciones = procesarHabitacion(rs);
            System.out.println("La consulta obtener habitaciones por id ha sido un exito");
        } catch (SQLException e) {
            System.out.println("No se pudo obtener habitaciones " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }
    
    
    

    public Habitacion obtenerHabitacionPorHabitacionId(int habitacion_id) {
        Habitacion habitacion = null;
        String query = "SELECT habitacion_id, cant_huesped, hotel_id FROM Habitacion where habitacion_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, habitacion_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                habitacion = new Habitacion(rs.getInt("habitacion_id"),
                        rs.getInt("cant_huesped"),
                        rs.getInt("hotel_id"));
            }
            System.out.println("La consulta obtener habitacion por id ha sido un exito");
        } catch (SQLException e) {
            System.out.println("No se pudo obtener habitaciones " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitacion;
    }
    
    public List<Habitacion> obtenerHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, hotel_id FROM Habitacion";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); 
                ResultSet rs = pstmt.executeQuery();) {
            
            habitaciones = procesarHabitacion(rs);
            System.out.println("La consulta obtener habitaciones por id ha sido un exito");
        } catch (SQLException e) {
            System.out.println("No se pudo obtener habitaciones " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }


    public List<Habitacion> obtenerHabitacionPorTamanio(int tamanio) {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, hotel_id FROM Habitacion where cant_huesped = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tamanio);
            ResultSet rs = pstmt.executeQuery();
            habitaciones = procesarHabitacion(rs);
            System.out.println("La consulta obtener habitaciones por id ha sido un exito");
        } catch (SQLException e) {
            System.out.println("No se pudo obtener habitaciones " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }

    public Habitacion actualizarHabitacionTamanio(int habitacion_id, int tamanio) {
        Habitacion habitacion = obtenerHabitacionPorHabitacionId(habitacion_id);
        String query = "UPDATE Habitacion SET cant_huesped = ? WHERE habitacion_id = ?";
        if(habitacion == null){
            System.out.println("No se ha encontrado ninguna habitacion con ese ID");
            return null;
        } 
        try(Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, tamanio);
            pstmt.setInt(2, habitacion_id);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if(filasAfectadas > 0 ){
                habitacion.setCantHuespedes(tamanio);
            } 
            System.out.println("Se ha modificado el tamaño de la habitacion correctamente");
        } catch (SQLException e) {
            System.out.println("Error al querer actualizar la habitación " + e.getSQLState());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return habitacion;
    }
    
    
    public void eliminarHabitacion(int habitacion_id) {
        Habitacion habitacion = obtenerHabitacionPorHabitacionId(habitacion_id);
        if (habitacion == null){
            System.out.println("No se ha encontrado ninguna habitacion con ese id");
            return;
        } 
        String query = "DELETE from Habitacion where habitacion_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, habitacion_id);
            pstmt.executeUpdate();
            System.out.println("Se ha eliminado correctamente la habitacion");
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar la habitacion" + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    
}
