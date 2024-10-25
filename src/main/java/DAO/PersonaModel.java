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
// TODO: import java.util.logging.ConsoleHandler;

/**
 *
 * @author asamsu
 */
public class PersonaModel {

    // Crear Tabla cliente 
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
            System.out.println("Se ha creado la tabla Persona exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudó crear la tabla Persona " + e.getMessage());
        } finally {
             ConnectionBD.getInstance().closeConnection();
        }

    }

    public void insertarPersona(String nombre, String apellido, String DNI, String email, Rol rol) {
        String query = "INSERT INTO Persona (nombre, apellido, DNI, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, DNI);
            pstmt.setString(4, email);
            pstmt.setString(5, rol.toString());
            
            pstmt.executeUpdate();
            System.out.println("Se creo un usuario exitosamente");

        } catch (SQLException e) {
            System.out.println("No se pudo insertar el usuario. " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT nombre, apellido, DNI FROM Persona WHERE ROL = USER";
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
            System.out.println("No se pudo obtener a los clientes " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return clientes;
    }

    public Cliente obtenerClientePorDNI(String DNI) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where DNI = ? AND ROL = USER";
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
                System.out.println("No se ha encontrado ningun cliente con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo insertar el Cliente " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return cliente;
    }
    
    
     public Cliente obtenerClientePorId(int cliente_id) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where cliente_id = ? AND ROL = USER";
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
                System.out.println("No se ha encontrado ningun cliente con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo insertar el Cliente " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return cliente;
    }
    
        public Cliente obtenerClientePorEmail(String email) {
        String query = "SELECT nombre, apellido, DNI, email FROM Persona where email = ? AND ROL = USER";
        Cliente cliente = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("persona_id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"), rs.getString("email"));
                rs.close();
            } else {
                System.out.println("No se ha encontrado ningun cliente con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo insertar el Cliente " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return cliente;
    }
    
    public Cliente actualizarCliente(String DNIBusqueda, String nombreActualizar, String apellidoActualizar, String DNIActualizar, String email) {
        Cliente cliente = obtenerClientePorDNI(DNIBusqueda);
        if (cliente == null) {
            System.out.println("No se ha encontrado ningun cliente con ese DNI");
            return null;
        }
        String query = "UPDATE Persona Set nombre = ? , apellido = ?, DNI = ?, email = ? WHERE DNI = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombreActualizar);
            pstmt.setString(2, apellidoActualizar);
            pstmt.setString(3, DNIActualizar);
            pstmt.setString(4, DNIBusqueda);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {

                cliente.setApellido(apellidoActualizar);
                cliente.setNombre(nombreActualizar);
                cliente.setDNI(DNIActualizar);
                cliente.setEmail(email);
                System.out.println("Cliente actualizado correctamente");
            } else {
                System.out.println("Ninguna fila ha sido modificada");
            }

        } catch (SQLException e) {
            System.out.println("No se pudo insertar el Cliente " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return cliente;
    }

    public void eliminarCuentaCliente(String DNI) {
        Cliente cliente = obtenerClientePorDNI(DNI);
        if (cliente == null) {
            System.out.println("No se ha encontrado ningun cliente con ese DNI");
        }
        String query = "DELETE from Persona where DNI = ? AND ROL = USER";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            pstmt.executeUpdate();
            System.out.println("Se a eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar al cliente " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
    public List<Admin> obtenerAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT nombre, apellido, DNI FROM Persona WHERE ROL = ADMIN";
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
            System.out.println("No se pudo obtener a los administradores " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return admins;
    }

    public Admin obtenerAdminPorDNI(String DNI) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where DNI = ? AND ROL = ADMIN";
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
                System.out.println("No se ha encontrado ningun administrador con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo insertar el administrador " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return admin;
    }
    
    public Admin obtenerAdminPorId(int admin_id) {
        String query = "SELECT persona_id, nombre, apellido, DNI, email FROM Persona where cliente_id = ? AND ROL = ADMIN";
        Admin admin= null;
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
                System.out.println("No se ha encontrado ningun administrador con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo insertar el administrador " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return admin;
    }
    
    public Admin obtenerAdminPorEmail(String email) {
        String query = "SELECT nombre, apellido, DNI, email FROM Persona where email = ? AND ROL = ADMIN";
        Admin admin = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                admin = new Admin(rs.getInt("persona_id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"), rs.getString("email"));
                rs.close();
            } else {
                System.out.println("No se ha encontrado ningun administrador con ese DNI");
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("No se pudo insertar el administrador " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();

        }
        return admin;
    }
    
    public Admin actualizarAdmin(String DNIBusqueda, String nombreActualizar, String apellidoActualizar, String DNIActualizar, String email) {
        Admin admin= obtenerAdminPorDNI(DNIBusqueda);
        if (admin == null) {
            System.out.println("No se ha encontrado ningun administrador con ese DNI");
            return null;
        }
        String query = "UPDATE Persona Set nombre = ? , apellido = ?, DNI = ?, email = ? WHERE DNI = ? AND ROL = ADMIN";
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
                System.out.println("Administrador actualizado correctamente");
            } else {
                System.out.println("Ninguna fila ha sido modificada");
            }

        } catch (SQLException e) {
            System.out.println("No se pudo insertar el administrador " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return admin;
    }
    
    public void eliminarCuentaAdmin(String DNI) {
        Admin admin = obtenerAdminPorDNI(DNI);
        if (admin == null) {
            System.out.println("No se ha encontrado ningun administrador con ese DNI");
        }
        String query = "DELETE from Persona where DNI = ? AND ROL = ADMIN";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            pstmt.executeUpdate();
            System.out.println("Se a eliminado correctamente el administrador");
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar al administrador " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    
}
