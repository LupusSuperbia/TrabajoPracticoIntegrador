/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Reserva;
import Util.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class ReservaModel {
    // CREATE
    public void crearTabla(){
           String query = "CREATE TABLE IF NOT EXISTS Reserva("
                + "reserva_id INTEGER NOT NULL PRIMARY KEY, "
                + "hotel_id INTEGER NOT NULL, "
                + "habitacion_id INTEGER NOT NULL, "
                + "cliente_id INTEGER NOT NULL, "
                + "fecha_inicio TEXT NOT NULL, "
                + "fecha_fin TEXT NOT NULL, "
                + "estado TEXT NOT NULL, "
                + "FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id), "
                + "FOREIGN KEY (habitacion_id) REFERENCES Habitacion(habitacion_id), "
                + "FOREIGN KEY (cliente_id) REFERENCES Cliente(cliente_id) "
                + " )"; 
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {   
            pstmt.execute();
            System.out.println("Se ha creado la tabla Reserva exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudo crear la tabla Reserva " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    // INSERT
        public void insertarReserva(int hotel_id, int habitacion_id, int cliente_id, LocalDate fecha_inicio, LocalDate fecha_fin, String estado ){
        String query = "INSERT INTO Reserva(hotel_id, habitacion_id, cliente_id, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?, ? )";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hotel_id);
            pstmt.setInt(2, habitacion_id);
            pstmt.setInt(3, cliente_id);
            pstmt.setString(4, fecha_inicio.toString());
            pstmt.setString(5, fecha_fin.toString());
            pstmt.setString(6, estado);
            pstmt.executeUpdate();
            System.out.println("Se ha insertado la reserva exitosamente");
        } catch (SQLException e) {
            System.out.println("No se pudo insertar una Reserva " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }
    // READ
        public List<Reserva> obtenerReservas(){
            List<Reserva> reservaciones = new ArrayList<>();
            String query = "SELECT reserva_id, hotel_id, habitacion_id, cliente_id, fecha_inicio, fecha_fin, estado FROM Reserva";
            try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                String fechaInicioStr = rs.getString("fecha_inicio");
                String fechaFinStr = rs.getString("fecha_fin");
                
                LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
                LocalDate fechaFin = LocalDate.parse(fechaFinStr);
                
                Reserva reserva = new Reserva(rs.getInt("reserva_id"), 
                        rs.getInt("hotel_id"), rs.getInt("habitacion_id"), 
                        rs.getInt("cliente_id"), 
                        fechaInicio, 
                        fechaFin,
                        rs.getString("estado"));
                reservaciones.add(reserva);
            }
            System.out.println("Se obtuvieron las reservas correctamente");
        } catch (SQLException e) {
            System.out.println("No se pudo insertar una Reserva" + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
            return reservaciones;
        }
}
