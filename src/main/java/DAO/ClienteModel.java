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
            try (Connection conn = ConnectionBD.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                pstmt.execute();
                System.out.println("Se ha creado la tabla Cliente exitosamente");
                tablaExist = true;
            } catch (SQLException e) {
                System.out.println("No se pudó crear la tabla Cliente " + e.getMessage());
            }

    }

    public void insertarCliente(String nombre, String apellido, String DNI) {
        String query = "INSERT INTO Cliente(nombre, apellido, DNI) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

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
    
    public List<Cliente> obtenerClientes(){
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT nombre, apellido, DNI FROM Cliente";
        try (Connection conn = ConnectionBD.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query); 
                ResultSet rs = pstmt.executeQuery()) {
            while(rs.next()){
                Cliente cliente = new Cliente(rs.getString("nombre"), rs.getString("apellido"), rs.getString("DNI"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("No se pudo obtener a los clientes " + e.getMessage());
        } 
        finally {
            ConnectionBD.getInstance().closeConnection();
        }
    
        return clientes;
    }
    
}
