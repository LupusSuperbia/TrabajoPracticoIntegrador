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
import DAO.Interface.HabitacionesDAOInterface;
import java.util.logging.Level;

/**
 *
 * @author estudiante
 */
public class HabitacionesDAO extends BaseDAO implements HabitacionesDAOInterface {

    /**
     * Metodo para crear la tabla de Habitacion en la Base de Datos SQLITE Usa
     * un try-with-resources para manejar las conexiónes y que el recurso se
     * cierre automaticamente así evitamos fuga de memoria El uso de
     * PreparedStatement evita inyecciones de SQL y así mantener seguridad.
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Habitacion( "
                + "habitacion_id INTEGER NOT NULL PRIMARY KEY,"
                + "cant_huesped INTEGER NOT NULL, "
                + "hotel_id INTEGER NOT NULL,"
                + "FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id)"
                + ");";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.execute();
            logger.info("Se ha creado la tabla Habitacion exitosamente");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo crear la tabla Habitacion {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }

    /**
     * Metodo para insertar en la tabla de Habitacion en la Base de Datos SQLITE
     *
     * @param cant_huesped (esto es la cantidad que pueden entrar en una
     * habitacion)
     * @param hotel_id ( Este parametro nos ayuda a asociar diferentes
     * habitaciones con un hotel para hacer más fácil la consultas desde la base
     * de datos) El uso de PreparedStatement evita inyecciones de SQL y así
     * mantener seguridad.
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void insertarHabitacion(int cant_huesped, int hotel_id) {
        String query = "INSERT INTO Habitacion(cant_huesped, hotel_id) VALUES (?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cant_huesped);
            pstmt.setInt(2, hotel_id);
            pstmt.executeUpdate();
            logger.info("Se ha insertado una habitacion exitosamente");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar una Habitacion {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
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
    @Override
    public List<Habitacion> obtenerHabitacionesPorHotelId(int hotel_id) {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, hotel_id FROM Habitacion where hotel_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hotel_id);
            ResultSet rs = pstmt.executeQuery();
            habitaciones = procesarHabitacion(rs);
            logger.info("La consulta obtener habitaciones por hotel_id ha sido un exito");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo obtener habitaciones {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }
    /**
     * Metodo para Obtener Habitaciones que contengan el id correspondiente en la
     * Base de Datos SQLITE
     *
     * @param habitacion_id ( Este parametro nos proporciona que busque todas las
     * habitaciones que contengan habitacion_id) 
     * @return Nos retorna un tipo de clase Habitacion 
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    @Override
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
            logger.info("La consulta obtener habitacion por id ha sido un exito");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo obtener habitaciones {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitacion;
    }
    
        /**
     * Metodo para Obtener Habitaciones que contengan el mismo hotel_id en la
     * Base de Datos SQLITE
     * @return Nos retorna un List -> ArrayList de tipo Habitacion que nos va a
     * contener todas las habitaciones
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public List<Habitacion> obtenerHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, hotel_id FROM Habitacion";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery();) {

            habitaciones = procesarHabitacion(rs);
            logger.info("La consulta obtener habitaciones  ha sido un exito");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo obtener habitaciones {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }
        /**
     * Metodo para Obtener Habitaciones que contengan el tamanio 
     * Base de Datos SQLITE
     *
     * @param tamanio ( Este parametro nos proporciona que busque todas las
     * habitaciones que contengan este tamaño) 
     * @return Nos retorna un List -> ArrayList de tipo Habitacion que nos va a
     * contener todas las habitaciones con el hotel_id que nosotros le
     * proporcionamos a traves de los parametros
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public List<Habitacion> obtenerHabitacionPorTamanio(int tamanio) {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT habitacion_id, cant_huesped, hotel_id FROM Habitacion where cant_huesped = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tamanio);
            ResultSet rs = pstmt.executeQuery();
            habitaciones = procesarHabitacion(rs);
            logger.info("La consulta obtener habitaciones por tamaño ha sido un exito");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo obtener habitaciones {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return habitaciones;
    }
        /**
     * Metodo para Actualizar una Habitacion que contenga la habitacion_idp proporcionada en los parametros 
     * en la
     * Base de Datos SQLITE
     *
     * @param habitacion_id ( Este parametro nos proporciona que busque la habitacion 
     * que contenga el id pasado) 
     * @param tamanio (tamaño al que se desea actualizar la habitacion_id )
     * @return Nos retorna un List -> ArrayList de tipo Habitacion que nos va a
     * contener todas las habitaciones con el hotel_id que nosotros le
     * proporcionamos a traves de los parametros
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public Habitacion actualizarHabitacionTamanio(int habitacion_id, int tamanio) {
        Habitacion habitacion = obtenerHabitacionPorHabitacionId(habitacion_id);
        String query = "UPDATE Habitacion SET cant_huesped = ? WHERE habitacion_id = ?";
        if (habitacion == null) {
            logger.info("No se ha encontrado ninguna habitacion con ese ID");
            return null;
        }
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setObject(1, tamanio);
            pstmt.setInt(2, habitacion_id);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                habitacion.setCantHuespedes(tamanio);
            }
            logger.info("Se ha modificado el tamaño de la habitacion correctamente");
        } catch (SQLException e) {
            logger.log(Level.INFO, "Error al querer actualizar la habitación {0}", e.getSQLState());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return habitacion;
    }
        /**
     * Metodo para eliminar Habitaciones que contenga la habitacion_id en la
     * Base de Datos SQLITE
     *
     * @param habitacion_id ( Este parametro nos proporciona que busque la habitacion 
     * que contenga el id pasado) 
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void eliminarHabitacion(int habitacion_id) {
        Habitacion habitacion = obtenerHabitacionPorHabitacionId(habitacion_id);
        if (habitacion == null) {
            System.out.println("No se ha encontrado ninguna habitacion con ese id");
            return;
        }
        String query = "DELETE from Habitacion where habitacion_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, habitacion_id);
            pstmt.executeUpdate();
            logger.info("Se ha eliminado correctamente la habitacion");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se ha podido eliminar la habitacion{0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    /**
     * Funciones de Utilidad para procesar listas
     * Base de Datos SQLITE
     *
     * @param rs ( Este parametro nos pasa como una especia de lista ) 
     * @return Nos retorna una lista llena de la clase Habitacion
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public List<Habitacion> procesarHabitacion(ResultSet rs) throws SQLException {
        List<Habitacion> habitaciones = new ArrayList<>();
        while (rs.next()) {
            Habitacion habitacion = new Habitacion(rs.getInt("habitacion_id"),
                    rs.getInt("cant_huesped"),
                    rs.getInt("hotel_id"));
            habitaciones.add(habitacion);
        }
        return habitaciones;
    }


}
