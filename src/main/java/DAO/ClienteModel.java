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

    public static boolean tablaExist;

    // Crear Tabla cliente 
    public void crearTabla() {
        String query = "CREATE TABLE IF NOT EXISTS Cliente ( "
                + "cliente_id INTEGER NOT NULL PRIMARY KEY,"
                + "nombre TEXT NOT NULL,"
                + "apellido TEXT NOT NULL,"
                + "DNI TEXT NOT NULL UNIQUE"
                + ")";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.execute();
            System.out.println("Se ha creado la tabla Cliente exitosamente");
            tablaExist = true;
        } catch (SQLException e) {
            System.out.println("No se pudó crear la tabla Cliente " + e.getMessage());
        }

    }

    public void insertarCliente(String nombre, String apellido, String DNI) {
        String query = "INSERT INTO Cliente(nombre, apellido, DNI) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, DNI);
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
                Cliente cliente = new Cliente(rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"));
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
        String query = "SELECT nombre, apellido, DNI FROM Cliente where DNI = ?";
        Cliente client = null;
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                client = new Cliente(rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"));
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
        return client;
    }

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
    
    public void eliminarCuenta(String DNI){
        Cliente client = obtenerClientePorDNI(DNI);
        if (client != null) {
            String query = "DELETE from Cliente where DNI = ?";
            try(Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
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

}
