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
      
    
     /**
     * Metodo para crear la tabla de Hotel en la Base de Datos SQLITE
     * Usa un try-with-resources para manejar las conexiónes y que el recurso se cierre automaticamente
     * así evitamos fuga de memoria 
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * 
     * @throw  SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Hotel ( "
                + "hotel_id INTEGER NOT NULL PRIMARY KEY,"
                + "nombre TEXT NOT NULL UNIQUE,"
                + "estrellas INTEGER NOT NULL);";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.execute();
            System.out.println("Se ha creado la tabla Hotel exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudó crear la tabla Hotel " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }


    }
     /**
     * Metodo para insertar en la tabla de Hotel en la Base de Datos SQLITE
     * 
     * @param nombre
     * (Parametro que ingresamos el nombre que va a tener el hotel)
     * @param estrellas
     * ( nos sirve para proporcionar la cantidad de estrellas que tiene nuestre hotel)
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
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
    
    
    /**
     * Metodo para Obtener Todos los hoteles en la Base de Datos SQLITE
     * 
     * @return Nos retorna un List -> ArrayList de tipo Hotel que nos va a contener todas las habitaciones
     * con el hotel_id que nosotros le proporcionamos a traves de los parametros
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
    public List<Hotel> obtenerHoteles() {
        List<Hotel> hoteles = new ArrayList<>();
        String query = "SELECT h.nombre, h.estrellas, COUNT(ha.habitacion_id) AS habitaciones " +
               "FROM Hotel h " +
               "LEFT JOIN Habitacion ha ON h.hotel_id = ha.hotel_id " +
               "GROUP BY h.nombre, h.estrellas;";
                
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
    
    /**
     * Metodo para Obtener Hoteles que contengan cierto nombre en la tabla Hotel en la Base de Datos SQLITE
     * 
     * @param nombre
     * ( Este parametro nos proporciona que busque el hotel que contengan este nombre)
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * @return Nos retorna un tipo Hotel que es el que contenga el parametro nombre
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 

    public Hotel obtenerHotelPorNombre(String nombre) {
         String query = "SELECT h.nombre, h.estrellas, COUNT(ha.habitacion_id) AS habitaciones " +
               "FROM Hotel h " +
               "LEFT JOIN Habitacion ha ON h.hotel_id = ha.hotel_id "
               + " WHERE nombre = ?" +
               "GROUP BY h.nombre, h.estrellas;";
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
            System.out.println("No se pudo encontrar hoteles " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return hotel;
    }
    
     /**
     * Metodo para Obtener Hoteles que contengan la cantidad de estrellas en la tabla hotel en la Base de Datos SQLITE
     * 
     * @param estrellas
     * ( Este parametro nos proporciona que busque los hoteles que contengan esta cantidad de estrellas)
     * El uso de PreparedStatement evita inyecciones de SQL y así mantener
     * seguridad.
     * @return Nos retorna un List -> ArrayList de tipo Hotel que nos va a contener todas los hoteles
     * con las estrellas que nosotros le proporcionamos a traves de los parametros
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */ 
    
    public List<Hotel> obtenerHotelesPorEstrella(int estrellas) {
        List<Hotel> hoteles = new ArrayList<>();
        String query = "SELECT nombre, estrellas, COUNT(*) AS habitaciones  "
                + "FROM Hotel h  "
                + "INNER JOIN Habitacion ha "
                + "ON h.hotel_id = ha.hotel_id  "
                + "WHERE estrellas = ? "
                + "GROUP BY h.nombre, h.estrellas; ";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setInt(1, estrellas);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("habitaciones"));
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

    /**
     * Metodo para Eliminar un Hotel que contengan el nombre que le pasamos como parametro en la tabla hotel en la Base de Datos SQLITE
     * 
     * @param nombre 
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
