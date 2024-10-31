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
import DAO.Interface.HotelDAOInterface;
import java.util.logging.Level;

/**
 *
 * @author asamsu
 */
public class HotelDAO extends BaseDAO implements HotelDAOInterface {

    /**
     * Metodo para crear la tabla de Hotel en la Base de Datos SQLITE Usa un
     * try-with-resources para manejar las conexiónes y que el recurso se cierre
     * automaticamente así evitamos fuga de memoria El uso de PreparedStatement
     * evita inyecciones de SQL y así mantener seguridad.
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Hotel ( "
                + "hotel_id INTEGER NOT NULL PRIMARY KEY,"
                + "nombre TEXT NOT NULL UNIQUE,"
                + "estrellas INTEGER NOT NULL);";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.execute();
            logger.info("Se ha creado la tabla Hotel exitosamente");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudó crear la tabla Hotel {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

    }

    /**
     * Metodo para insertar en la tabla de Hotel en la Base de Datos SQLITE
     *
     * @param nombre (Parametro que ingresamos el nombre que va a tener el
     * hotel)
     * @param estrellas ( nos sirve para proporcionar la cantidad de estrellas
     * que tiene nuestre hotel) El uso de PreparedStatement evita inyecciones de
     * SQL y así mantener seguridad.
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void insertarHotel(String nombre, int estrellas) {
        String query = "INSERT INTO Hotel(nombre, estrellas) VALUES (?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setInt(2, estrellas);
            pstmt.executeUpdate();
            logger.info("Se creo un hotel exitosamente");

        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el hotel {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }





    /**
     * Metodo para Obtener Todos los hoteles en la Base de Datos SQLITE
     *
     * @return Nos retorna un List -> ArrayList de tipo Hotel que nos va a
     * contener todas las habitaciones con el hotel_id que nosotros le
     * proporcionamos a traves de los parametros
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public List<Hotel> obtenerHoteles() {
        List<Hotel> hoteles = new ArrayList<>();
        String query = "SELECT h.hotel_id, h.nombre, h.estrellas, COUNT(ha.habitacion_id) AS habitaciones "
                + "FROM Hotel h "
                + "LEFT JOIN Habitacion ha ON h.hotel_id = ha.hotel_id "
                + "GROUP BY h.nombre, h.estrellas;";

        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            hoteles = procesarHotel(rs);
        } catch (SQLException e) {
            logger.log(Level.INFO, "Error al obtener Hoteles {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return hoteles;
    }

    /**
     * Metodo para Obtener Hoteles que contengan cierto nombre en la tabla Hotel
     * en la Base de Datos SQLITE
     *
     * @param IdHotel ( Este parametro nos proporciona que busque el hotel que
     * contengan este id) El uso de PreparedStatement evita inyecciones de SQL y
     * así mantener seguridad.
     * @return Nos retorna un tipo Hotel que es el que contenga el parametro id
     * que les pasamos
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public Hotel obtenerHotelPorId(int IdHotel) {
        String query = "SELECT h.hotel_id, h.nombre, h.estrellas, COUNT(ha.habitacion_id) AS habitaciones "
                + "FROM Hotel h "
                + "LEFT JOIN Habitacion ha ON h.hotel_id = ha.hotel_id "
                + " WHERE h.hotel_id = ? "
                + "GROUP BY h.nombre, h.estrellas;";
        Hotel hotel = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, IdHotel);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hotel = new Hotel(rs.getString("nombre"),
                            rs.getInt("estrellas"),
                            rs.getInt("habitaciones"),
                            rs.getInt("hotel_id"));
                    return hotel;
                } else {
                    logger.info("No se ha encontrado ningun hotel con ese nombre");
                }
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error al obtener Hoteles {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return hotel;
    }

    /**
     * Metodo para Obtener Hoteles que contengan cierto nombre en la tabla Hotel
     * en la Base de Datos SQLITE
     *
     * @param nombre ( Este parametro nos proporciona que busque el hotel que
     * contengan este nombre) El uso de PreparedStatement evita inyecciones de
     * SQL y así mantener seguridad.
     * @return Nos retorna un tipo Hotel que es el que contenga el parametro
     * nombre
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public Hotel obtenerHotelPorNombre(String nombre) {
        String query = "SELECT h.hotel_id, h.nombre, h.estrellas, COUNT(ha.habitacion_id) AS habitaciones "
                + "FROM Hotel h "
                + "LEFT JOIN Habitacion ha ON h.hotel_id = ha.hotel_id "
                + " WHERE nombre = ?"
                + "GROUP BY h.nombre, h.estrellas;";
        Hotel hotel = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hotel = new Hotel(rs.getString("nombre"),
                            rs.getInt("estrellas"),
                            rs.getInt("habitaciones"),
                            rs.getInt("hotel_id"));
                    return hotel;
                } else {
                    logger.info("No se ha encontrado ningun hotel con ese nombre");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "Error al obtener Hotel {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return hotel;
    }

    /**
     * Metodo para Obtener Hoteles que contengan la cantidad de estrellas en la
     * tabla hotel en la Base de Datos SQLITE
     *
     * @param estrellas ( Este parametro nos proporciona que busque los hoteles
     * que contengan esta cantidad de estrellas) El uso de PreparedStatement
     * evita inyecciones de SQL y así mantener seguridad.
     * @return Nos retorna un List -> ArrayList de tipo Hotel que nos va a
     * contener todas los hoteles con las estrellas que nosotros le
     * proporcionamos a traves de los parametros
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public List<Hotel> obtenerHotelesPorEstrella(int estrellas) {
        List<Hotel> hoteles = new ArrayList<>();
        String query = "SELECT h.hotel_id, nombre, estrellas, COUNT(*) AS habitaciones  "
                + "FROM Hotel h  "
                + "INNER JOIN Habitacion ha "
                + "ON h.hotel_id = ha.hotel_id  "
                + "WHERE estrellas = ? "
                + "GROUP BY h.nombre, h.estrellas; ";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setInt(1, estrellas);
            ResultSet rs = pstmt.executeQuery();
            hoteles = procesarHotel(rs);

        } catch (SQLException e) {
            logger.log(Level.INFO, "Error al obtener hoteles por estrellas{0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return hoteles;
    }
    
        /**
     * Metodo para Actualizar Hoteles que contengan la cantidad de estrellas en la
     * tabla hotel en la Base de Datos SQLITE
     *
     * @param columna (Columna que vamos a querer actualizar)
     * 
     * @param valorModificado (Es de tipo object  para poder pasarlo cualquier tipo de dato
     * que queremos modificar)
     * 
     * @param hotel_id (Parametro para buscar el hotel que queremos modificar)
     * 
     * @return Nos retorna un List -> ArrayList de tipo Hotel que nos va a
     * contener todas los hoteles con las estrellas que nosotros le
     * proporcionamos a traves de los parametros
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */

    @Override
    public Hotel actualizarHotel(String columna, Object valorModificado, int hotel_id) {
        Hotel hotel = obtenerHotelPorId(hotel_id);
        if (hotel == null) {
            logger.info("No se ha encontrado ningun hotel con ese id");
            return null;
        }
        String query = "UPDATE Hotel SET " + columna + " = ? WHERE hotel_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setObject(1, valorModificado);
            pstmt.setInt(2, hotel_id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                hotel = actualizarHotelEnObjeto(hotel, columna, valorModificado);
                logger.info("Cliente actualizado correctamente");
            } else {
                logger.info("Ninguna fila ha sido modificada");
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el hotel {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return hotel;
    }

    /**
     * Metodo para Eliminar un Hotel que contengan el nombre que le pasamos como
     * parametro en la tabla hotel en la Base de Datos SQLITE
     *
     * @param nombre
     */
    @Override
    public void eliminarHotel(String nombre) {
        Hotel client = obtenerHotelPorNombre(nombre);
        if (client != null) {
            String query = "DELETE from Hotel where nombre = ?";
            try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nombre);
                pstmt.executeUpdate();
                logger.info("Se ha eliminado un hotel correctamente");
            } catch (SQLException e) {
                logger.log(Level.INFO, "No se ha podido eliminar al hotel {0}", e.getMessage());
            } finally {
                ConnectionBD.getInstance().closeConnection();
            }
        }
    }
    
    /**
     * Metodo para Eliminar un Hotel que contengan el nombre que le pasamos como
     * parametro en la tabla hotel en la Base de Datos SQLITE
     *
     * @param rs
     * 
     * 
     * @return devuelve una lista de tipo Hotel
     */
    public List<Hotel> procesarHotel(ResultSet rs) throws SQLException {
        List<Hotel> hoteles = new ArrayList<>();
        while (rs.next()) {
            Hotel hotel = new Hotel(rs.getString("nombre"),
                    rs.getInt("estrellas"),
                    rs.getInt("habitaciones"),
                    rs.getInt("hotel_id"));
            hoteles.add(hotel);
        }
        return hoteles;
    }
    
        @Override
    public Hotel actualizarHotelEnObjeto(Hotel hotel, String columna, Object valorModificado) {
        switch (columna) {
            case "nombre" ->
                hotel.setNombre((String) valorModificado);
            case "estrellas" ->
                hotel.setEstrellas((int) valorModificado);
            default ->
                throw new IllegalArgumentException("Columna no válida: " + columna);
        }
        return hotel;
    }
}
