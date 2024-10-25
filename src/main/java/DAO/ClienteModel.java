/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Cliente;
import Util.ConnectionBD;
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
public class ClienteModel {

    // Crear Tabla cliente 
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Cliente ( "
                + "cliente_id INTEGER NOT NULL PRIMARY KEY,"
                + "nombre TEXT NOT NULL,"
                + "apellido TEXT NOT NULL, "
                + "email TEXT NOT NULL UNIQUE, "
                + "DNI TEXT NOT NULL UNIQUE"
                + ")";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.execute();
            System.out.println("Se ha creado la tabla Cliente exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudó crear la tabla Cliente " + e.getMessage());
        } finally {
             ConnectionBD.getInstance().closeConnection();
        }

    }

    public void insertarCliente(String nombre, String apellido, String DNI, String email) {
        String query = "INSERT INTO Cliente(nombre, apellido, DNI, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, DNI);
            pstmt.setString(4, email);
            
            pstmt.executeUpdate();
            System.out.println("Se creo un usuario exitosamente");

        } catch (SQLException e) {
            System.out.println("No se pudo insertar el Cliente " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT nombre, apellido, DNI FROM Cliente";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("cliente_id"),
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
        String query = "SELECT cliente_id, nombre, apellido, DNI, email FROM Cliente where DNI = ?";
        Cliente cliente = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("cliente_id"),
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
        String query = "SELECT cliente_id, nombre, apellido, DNI, email FROM Cliente where cliente_id = ?";
        Cliente cliente = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cliente_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("cliente_id"),
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
        String query = "SELECT nombre, apellido, DNI, email FROM Cliente where email = ?";
        Cliente cliente = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("cliente_id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"), rs.getString("email"));
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
        String query = "UPDATE CLIENTE Set nombre = ? , apellido = ?, DNI = ?, email = ? WHERE DNI = ?";
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

    public void eliminarCuenta(String DNI) {
        Cliente cliente = obtenerClientePorDNI(DNI);
        if (cliente == null) {
            System.out.println("No se ha encontrado ningun cliente con ese DNI");
        }
        String query = "DELETE from Cliente where DNI = ?";
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

}
