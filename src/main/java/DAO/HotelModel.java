/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Hotel;
import Util.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class HotelModel {
      // Crear Tabla cliente 
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Hotel ( "
                + "hotel_id INTEGER NOT NULL PRIMARY KEY,"
                + "nombre TEXT NOT NULL UNIQUE,"
                + "estrellas INTEGER NOT NULL,"
                + "habitaciones INTEGER  NULL"
                + ")";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.execute();
            System.out.println("Se ha creado la tabla Hotel exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudó crear la tabla Hotel " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }


    }

    public void insertarHotel(String nombre, int estrellas) {
        String query = "INSERT INTO Hotel(nombre, estrellas) VALUES (?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setInt(2, estrellas);
            pstmt.executeUpdate();
            System.out.println("Se creo un hotel exitosamente");

        } catch (SQLException e) {
            System.out.println("No se pudo insertar el hotel " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }

    public List<Hotel> obtenerHoteles() {
        List<Hotel> hoteles = new ArrayList<>();
        String query = "SELECT nombre, estrellas, habitaciones FROM Hotel";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Hotel hotel = new Hotel(rs.getString("nombre"), rs.getInt("estrellas"), rs.getInt("habitaciones"));
                hoteles.add(hotel);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("No se pudo obtener al hotel " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return hoteles;
    }

    public Hotel obtenerHotelPorNombre(String nombre) {
        String query = "SELECT nombre, estrellas, habitaciones FROM Hotel where nombre = ?";
        Hotel hotel = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                hotel = new Hotel(rs.getString("nombre"), rs.getInt("estrellas"), rs.getInt("habitaciones"));
                rs.close();
            } else {
                System.out.println("No se ha encontrado ningun hotel con ese nombre");
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo insertar el Cliente " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return hotel;
    }
    
    public List<Hotel> obtenerHotelesPorEstrella(int estrellas) {
        List<Hotel> hoteles = new ArrayList<>();
        String query = "SELECT nombre, estrellas, habitaciones FROM Hotel WHERE estrellas = ? ";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setInt(1, estrellas);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Hotel hotel = new Hotel(rs.getString("nombre"), rs.getInt("estrellas"), rs.getInt("habitaciones"));
                hoteles.add(hotel);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("No se pudo obtener al hotel " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return hoteles;
    }
    
    
    /* 
    public Cliente actualizarCliente(String DNIBusqueda, String nombreActualizar, String apellidoActualizar, String DNIActualizar) {
        Cliente client = obtenerClientePorDNI(DNIBusqueda);
        if (client != null) {
            String query = "UPDATE CLIENTE Set nombre = ? , apellido = ?, DNI = ? WHERE DNI = ?";
            try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                pstmt.setString(1, nombreActualizar);
                pstmt.setString(2, apellidoActualizar);
                pstmt.setString(3, DNIActualizar);
                pstmt.setString(4, DNIBusqueda);
                
                int filasAfectadas = pstmt.executeUpdate();
                
                if(filasAfectadas > 0){
                    
                client.setApellido(apellidoActualizar);
                client.setNombre(nombreActualizar);
                client.setDNI(DNIActualizar);
                
                System.out.println("Cliente actualizado correctamente");
                } else {
                    System.out.println("Ninguna fila ha sido modificada");   
                }
                
            } catch (SQLException e) {
                System.out.println("No se pudo insertar el Cliente " + e.getMessage());
            } finally {
                ConnectionBD.getInstance().closeConnection();

            }
            return client;
        }
        return client;
    }
    */ 
    public void eliminarHotel(String nombre){
        Hotel client = obtenerHotelPorNombre(nombre);
        if (client != null) {
            String query = "DELETE from Hotel where nombre = ?";
            try(Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nombre);
                pstmt.executeUpdate();
                System.out.println("Se a eliminado un hotel correctamente");
            } catch (SQLException e) {
                System.out.println("No se ha podido eliminar al hotel " + e.getMessage());
            } finally {
                ConnectionBD.getInstance().closeConnection();
            }
        }
    }
}
