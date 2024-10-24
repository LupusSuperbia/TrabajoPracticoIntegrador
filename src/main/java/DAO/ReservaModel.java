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
    public void crearTabla() {
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

    // Utils
    public List<Reserva> procesarReserva(ResultSet rs) throws SQLException {
        List<Reserva> reservaciones = new ArrayList<>();
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
        return reservaciones;
    }

    // INSERT
    public void insertarReserva(int hotel_id, int habitacion_id, int cliente_id, LocalDate fecha_inicio, LocalDate fecha_fin, String estado) {
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
    public List<Reserva> obtenerReservas() {
        List<Reserva> reservaciones = new ArrayList<>();
        String query = "SELECT reserva_id, hotel_id, habitacion_id, cliente_id, fecha_inicio, fecha_fin, estado FROM Reserva";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery();) {
            reservaciones = procesarReserva(rs);
            System.out.println("Se obtuvieron las reservas correctamente");
        } catch (SQLException e) {
            System.out.println("No se pudo insertar una Reserva" + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return reservaciones;
    }

    public List<Reserva> obtenerReservasConCondicion(String condicion, Object valor) {
        List<Reserva> reservaciones = new ArrayList<>();
        String query = "SELECT reserva_id, hotel_id, habitacion_id, cliente_id, fecha_inicio, fecha_fin, estado FROM Reserva WHERE " + condicion + " = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setObject(1, valor);
            ResultSet rs = pstmt.executeQuery();
            reservaciones = procesarReserva(rs);
            System.out.println("Se obtuvieron las reservas correctamente");
        } catch (SQLException e) {
            System.out.println("No se pudo insertar una Reserva" + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return reservaciones;
    }

    public List<Reserva> obtenerReservasPorEstado(String estado) {
        return obtenerReservasConCondicion("estado", estado);
    }

    public List<Reserva> obtenerReservasPorHotel(int hotel_id) {
        return obtenerReservasConCondicion("hotel_id", hotel_id);
    }

    public List<Reserva> obtenerReservasPorCliente(int cliente_id) {
        return obtenerReservasConCondicion("cliente_id", cliente_id);
    }

    public List<Reserva> obtenerReservasPorHabitacion(int habitacion_id) {
        return obtenerReservasConCondicion("habitacion_id", habitacion_id);
    }

    public Reserva obtenerReservaPorId(int reserva_id) {
        Reserva reserva = null;
        String query = "SELECT reserva_id, hotel_id, habitacion_id, cliente_id, fecha_inicio, fecha_fin, estado FROM Reserva WHERE reserva_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setInt(1, reserva_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String fechaInicioStr = rs.getString("fecha_inicio");
                String fechaFinStr = rs.getString("fecha_fin");

                LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
                LocalDate fechaFin = LocalDate.parse(fechaFinStr);

                reserva = new Reserva(rs.getInt("reserva_id"),
                        rs.getInt("hotel_id"), rs.getInt("habitacion_id"),
                        rs.getInt("cliente_id"),
                        fechaInicio,
                        fechaFin,
                        rs.getString("estado"));
            }
            System.out.println("Se obtuvieron las reservas correctamente");
        } catch (SQLException e) {
            System.out.println("No se pudo insertar una Reserva" + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return reserva;
    }

    public boolean verificarReserva(int habitacion_id, String fecha_inicio, String fecha_fin) {
        boolean reservacion = false;
        String query = "SELECT COUNT(*)"
                + "FROM Reserva "
                + "WHERE habitacion_id = ? "
                + "  AND (fecha_inicio < ? AND fecha_fin > ?)";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, habitacion_id);
            pstmt.setString(2, fecha_fin);
            pstmt.setString(3, fecha_inicio);
           ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int reservas = rs.getInt(1);
                reservacion = reservas == 0;
            } 

        } catch (SQLException e) {
            System.out.println("Error al actualizar Reserva " + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }

        return reservacion;
    }

    public Reserva actualizarEstadoReserva(int reserva_id, String estado) {
        Reserva reserva = obtenerReservaPorId(reserva_id);
        if (reserva == null) {
            System.out.println("No se ha obtenido una reserva con el id " + reserva_id);
            return null;
        }
        String query = "UPDATE RESERVA Set estado = ? WHERE reserva_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, estado);
            pstmt.setInt(2, reserva_id);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                reserva.setEstado(estado);
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar Reserva " + e.getSQLState());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
        return reserva;
    }

    public void eliminarReserva(int reserva_id) {
        Reserva reserva = obtenerReservaPorId(reserva_id);
        if (reserva == null) {
            System.out.println("No se ha encontrado ninguna reserva con ese ID");
            return;
        }
        String query = "DELETE from Reserva where reserva_id = ?";
        try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reserva_id);
            pstmt.executeUpdate();
            System.out.println("Se ha eliminado correctamente la reserva");
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar la reserva" + e.getMessage());
        } finally {
            ConnectionBD.getInstance().closeConnection();
        }
    }

//     public Cliente actualizarCliente(String DNIBusqueda, String nombreActualizar, String apellidoActualizar, String DNIActualizar) {
//        Cliente client = obtenerClientePorDNI(DNIBusqueda);
//        if (client != null) {
//            String query = "UPDATE CLIENTE Set nombre = ? , apellido = ?, DNI = ? WHERE DNI = ?";
//            try (Connection conn = ConnectionBD.getInstance().getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//                pstmt.setString(1, nombreActualizar);
//                pstmt.setString(2, apellidoActualizar);
//                pstmt.setString(3, DNIActualizar);
//                pstmt.setString(4, DNIBusqueda);
//
//                int filasAfectadas = pstmt.executeUpdate();
//
//                if (filasAfectadas > 0) {
//
//                    client.setApellido(apellidoActualizar);
//                    client.setNombre(nombreActualizar);
//                    client.setDNI(DNIActualizar);
//
//                    System.out.println("Cliente actualizado correctamente");
//                } else {
//                    System.out.println("Ninguna fila ha sido modificada");
//                }
//
//            } catch (SQLException e) {
//                System.out.println("No se pudo insertar el Cliente " + e.getMessage());
//            } finally {
//                ConnectionBD.getInstance().closeConnection();
//
//            }
//            return client;
//        }
//        return client;
}
