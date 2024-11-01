/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Admin;
import Model.Cliente;
import Util.ConnectionBD;
import Util.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.Interface.PersonaDAOInterface;
import java.util.logging.Level;
// TODO: import java.util.logging.ConsoleHandler;

/**
 *
 * @author asamsu
 */
public class PersonaDAO extends BaseDAO implements PersonaDAOInterface {

    /**
     * Metodo para crear la tabla de Persona en la Base de Datos SQLITE Usa un
     * try-with-resources para manejar las conexiónes y que el recurso se cierre
     * automaticamente así evitamos fuga de memoria El uso de PreparedStatement
     * evita inyecciones de SQL y así mantener seguridad.
     *
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Persona ( "
                + "persona_id INTEGER NOT NULL PRIMARY KEY,"
                + "nombre TEXT NOT NULL,"
                + "apellido TEXT NOT NULL, "
                + "email TEXT NOT NULL UNIQUE, "
                + "DNI TEXT NOT NULL UNIQUE,"
                + "ROL TEXT NOT NULL"
                + ")";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.execute();
            logger.info("Se ha creado la tabla Persona exitosamente");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo crear la tabla Persona {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

    }
    /**
     * Metodo para insertar un cliente en la tabla de Persona en la Base de Datos SQLITE
     *
     * @param nombre
     * @param apellido
     * @param DNI
     * @param rol
     * @param email
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    public String obtenerRolPorDNI(String DNI) {
        String Rol = "";
        String query = "SELECT Rol FROM Persona where DNI = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
           Rol = rs.getString("Rol");
                rs.close();
            } else {
                logger.info("No se ha encontrado ningun Rol con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo obtener Rol", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return Rol;
    }
    
    @Override
    public void insertarCliente(String nombre, String apellido, String DNI, String email, Rol rol) {
        insertarPersona(nombre, apellido, DNI, email, rol);
    }
    
     /**
     * Metodo para insertar un admin en la tabla de Persona en la Base de Datos SQLITE
     *
     * @param nombre
     * @param apellido
     * @param DNI
     * @param rol
     * @param email
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    @Override
    public void insertarAdmin(String nombre, String apellido, String DNI, String email, Rol rol) {
        insertarPersona(nombre, apellido, DNI, email, rol);
    }
    
    @Override
    public void insertarPersona(String nombre, String apellido, String DNI, String email, Rol rol) {
        String query = "INSERT INTO Persona (nombre, apellido, DNI, email, ROL) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, DNI);
            pstmt.setString(4, email);
            pstmt.setString(5, rol.toString());

            pstmt.executeUpdate();
            logger.info("Se creo un usuario exitosamente");

        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el usuario. {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
         /**
     * Metodo para obtener un cliente en la tabla de Persona en la Base de Datos SQLITE 
     *
     * 
     * @return Devuelve un List de tipo Cliente  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona WHERE ROL = \"USER\"";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("email"));
                clientes.add(cliente);
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo obtener a los clientes {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return clientes;
    }
    
    /**
     * Metodo para obtener un cliente en la tabla de Persona a través del DNI
     * en la Base de Datos SQLITE 
     *
     * @param DNI el parametro que recibe el metodo para buscar en la BD el cliente
     * 
     * @return Devuelve un List de tipo Cliente  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    @Override
    public Cliente obtenerClientePorDNI(String DNI) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where DNI = ? AND ROL = \"USER\"";
        Cliente cliente = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("email"));
                rs.close();
            } else {
                logger.info("No se ha encontrado ningun cliente con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el Cliente {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return cliente;
    }
        /**
     * Metodo para obtener un cliente en la tabla de Persona a través del cliente_id
     * en la Base de Datos SQLITE 
     *
     * @param cliente_id el parametro que recibe el metodo para buscar en la BD el cliente
     * 
     * @return Devuelve un List de tipo Cliente  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    @Override
    public Cliente obtenerClientePorId(int cliente_id) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email, ROL FROM Persona where persona_id = ? AND ROL = \"USER\"";

        Cliente cliente = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cliente_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("email"));
                rs.close();
            } else {
                logger.info("No se ha encontrado ningun cliente con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el cliente {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return cliente;
    }
    
    /**
     * Metodo para obtener un cliente en la tabla de Persona a través del email
     * en la Base de Datos SQLITE 
     *
     * @param email el parametro que recibe el metodo para buscar en la BD el cliente
     * 
     * @return Devuelve un List de tipo Cliente  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    @Override
    public Cliente obtenerClientePorEmail(String email) {
        String query = "SELECT nombre, apellido, DNI, email FROM Persona where email = ? AND ROL = \"USER\"";
        Cliente cliente = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("persona_id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"), rs.getString("email"));
                rs.close();
            } else {
                logger.info("No se ha encontrado ningun cliente con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el Cliente {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return cliente;
    }
    
       /**
     * Metodo para actualizar un cliente en la tabla de Persona a través del email
     * en la Base de Datos SQLITE 
     *
     * 
     * @param DNIBusqueda este parametro sirve para buscar el Cliente en la bd y confirmar que existen
     * @param email es el parametro que recibe el metodo para actualizar en la columna "email" en la BD el cliente
     * @param apellidoActualizar es el parametro que recibe el metodo para actualizar en la columna "apellido" en la BD el cliente
     * @param DNIActualizar es el parametro que recibe el metodo para actualizar en la columna "DNI" en la BD el cliente
     * @param nombreActualizar es el parametro que recibe el metodo para actualizar en la columna "nombre" en la BD el cliente
     * 
     * @return Devuelve un List de tipo Cliente  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public Cliente actualizarCliente(String DNIBusqueda, String nombreActualizar, String apellidoActualizar, String DNIActualizar, String email) {
        Cliente cliente = obtenerClientePorDNI(DNIBusqueda);
        if (cliente == null) {
            System.out.println("No se ha encontrado ningun cliente con ese DNI");
            return null;
        }
        String query = "UPDATE Persona Set nombre = ? , apellido = ?, email = ? WHERE DNI = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, nombreActualizar);
            pstmt.setString(2, apellidoActualizar);
            pstmt.setString(3, email);
            pstmt.setString(4, DNIBusqueda);
            
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {

                cliente.setApellido(apellidoActualizar);
                cliente.setNombre(nombreActualizar);
                cliente.setEmail(email);
                logger.info("Cliente actualizado correctamente");
            } else {
                logger.info("Ninguna fila ha sido modificada");
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el Cliente {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return cliente;
    }
           /**
     * Metodo para actualizar un cliente en la tabla de Persona a través del email
     * en la Base de Datos SQLITE 
     *
     * 
rametro que recibe el metodo para actualizar en la columna "nombre" en la BD el cliente
     * 
     * @param DNI parametro que sirve para buscar en la base de datos a un cliente con este DNI
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void eliminarCuentaCliente(String DNI) {
        Cliente cliente = obtenerClientePorDNI(DNI);
        if (cliente == null) {
            logger.info("No se ha encontrado ningun cliente con ese DNI");
        }
        String query = "DELETE from Persona where DNI = ? AND ROL = \"USER\"";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            pstmt.executeUpdate();
            logger.info("Se a eliminado correctamente");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se ha podido eliminar al cliente {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    
    /**
     * Metodo para obtener un admin en la tabla de Persona en la Base de Datos SQLITE 
     *
     * 
     * @return Devuelve un List de tipo Admin  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public List<Admin> obtenerAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona WHERE ROL = \"ADMIN\"";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Admin admin = new Admin(rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("email"));
                admins.add(admin);
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo obtener a los administradores {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return admins;
    }
    
        /**
     * Metodo para obtener un Admin en la tabla de Persona a través del DNI
     * en la Base de Datos SQLITE 
     *
     * @param DNI el parametro que recibe el metodo para buscar en la BD el admin
     * 
     * @return Devuelve un tipo Admin  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    @Override
    public Admin obtenerAdminPorDNI(String DNI) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where DNI = ? AND ROL = \"ADMIN\"";
        Admin admin = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                admin = new Admin(rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("email"));
                rs.close();
            } else {
                logger.info("No se ha encontrado ningun administrador con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el administrador {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return admin;
    }
     /**
     * Metodo para obtener un Admin en la tabla de Persona a través del DNI
     * en la Base de Datos SQLITE 
     *
     * @param admin_id el parametro que recibe el metodo para buscar en la BD el admin
     * 
     * @return Devuelve un tipo Admin  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public Admin obtenerAdminPorId(int admin_id) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where cliente_id = ? AND ROL = \"ADMIN\"";
        Admin admin = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, admin_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                admin = new Admin(rs.getInt("persona_id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("DNI"),
                        rs.getString("email"));
                rs.close();
            } else {
                logger.info("No se ha encontrado ningun administrador con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el administrador {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return admin;
    }
    
    
         /**
     * Metodo para obtener un Admin en la tabla de Persona a través del DNI
     * en la Base de Datos SQLITE 
     *
     * @param email el parametro que recibe el metodo para buscar en la BD el admin
     * 
     * @return Devuelve un tipo Admin  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public Admin obtenerAdminPorEmail(String email) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where email = ? AND ROL = \"ADMIN\"";
        Admin admin = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                admin = new Admin(rs.getInt("persona_id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"), rs.getString("email"));
                rs.close();
            } else {
                logger.info("No se ha encontrado ningun administrador con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el administrador {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return admin;
    }
    
             /**
     * Metodo para actualizar un Admin en la tabla de Persona a través del DNI
     * en la Base de Datos SQLITE 
     *
     * @param DNIBusqueda este parametro sirve para buscar el Admin en la bd y confirmar que existen
     * @param nombreActualizar
     * @param email el parametro que recibe el metodo para buscar en la BD el admin
     * @param apellidoActualizar
     * @param DNIActualizar
     * 
     * @return Devuelve un tipo Admin  
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    
    @Override
    public Admin actualizarAdmin(String DNIBusqueda, String nombreActualizar, String apellidoActualizar, String DNIActualizar, String email) {
        Admin admin = obtenerAdminPorDNI(DNIBusqueda);
        if (admin == null) {
            logger.info("No se ha encontrado ningun administrador con ese DNI");
            return null;
        }
        String query = "UPDATE Persona Set nombre = ? , apellido = ?, DNI = ?, email = ? WHERE DNI = ? AND ROL = \"ADMIN\"";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombreActualizar);
            pstmt.setString(2, apellidoActualizar);
            pstmt.setString(3, DNIActualizar);
            pstmt.setString(4, DNIBusqueda);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {

                admin.setApellido(apellidoActualizar);
                admin.setNombre(nombreActualizar);
                admin.setDNI(DNIActualizar);
                admin.setEmail(email);
                logger.info("Administrador actualizado correctamente");
            } else {
                logger.info("Ninguna fila ha sido modificada");
            }

        } catch (SQLException e) {
            logger.log(Level.INFO, "No se pudo insertar el administrador {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return admin;
    }
    
                 /**
     * Metodo para eliminar un Admin en la tabla de Persona a través del DNI
     * en la Base de Datos SQLITE 
     *
     * @param DNI
     * 
     * 
     * 
     * @throw SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
    @Override
    public void eliminarCuentaAdmin(String DNI) {
        Admin admin = obtenerAdminPorDNI(DNI);
        if (admin == null) {
            logger.info("No se ha encontrado ningun administrador con ese DNI");
        }
        String query = "DELETE from Persona where DNI = ? AND ROL =\"ADMIN\"";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            pstmt.executeUpdate();
            logger.info("Se a eliminado correctamente el administrador");
        } catch (SQLException e) {
            logger.log(Level.INFO, "No se ha podido eliminar al administrador {0}", e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    
    

}
