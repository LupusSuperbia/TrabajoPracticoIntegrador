/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.Interface;

import Model.Reserva;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author asamsu
 */
public interface ReservaDAOInterface {
    public void crearTabla();
    public void insertarReserva(int hotel_id, 
            int habitacion_id, 
            int cliente_id, 
            LocalDate fecha_inicio, 
            LocalDate fecha_fin, 
            String estado);
    
    public List<Reserva> obtenerReservas();
    List<Reserva> obtenerReservasConCondicion(String condicion, Object valor);
    public List<Reserva> obtenerReservasPorEstado(String estado);
    public List<Reserva> obtenerReservasPorHotel(int hotel_id);
    public List<Reserva> obtenerReservasPorCliente(int cliente_id);
    public List<Reserva> obtenerReservasPorHabitacion(int habitacion_id);
    public Reserva obtenerReservaPorId(int reserva_id);
    public boolean verificarReserva(int habitacion_id, String fecha_inicio, String fecha_fin);
    public Reserva actualizarEstadoReserva(int reserva_id, String estado);
    public void eliminarReserva(int reserva_id);
}
