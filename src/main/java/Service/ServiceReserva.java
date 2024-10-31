/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PersonaDAO;
import DAO.HabitacionesDAO;
import DAO.HotelDAO;
import DAO.PersonaDAO;
import DAO.ReservaDAO;
import DTO.ReservaDTO;
import Exceptions.ServiceExceptions;
import Model.Cliente;
import Model.Habitacion;
import Model.Hotel;
import Model.Reserva;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author asamsu
 */
public class ServiceReserva extends ServiceBase {

    private final ReservaDAO reservaDAO;
    private final PersonaDAO clienteDAO;
    private final HotelDAO hotelDAO;
    private final HabitacionesDAO habitacionDAO;

    public ServiceReserva() {
        this.reservaDAO = new ReservaDAO();
        this.clienteDAO = new PersonaDAO();
        this.hotelDAO = new HotelDAO();
        this.habitacionDAO = new HabitacionesDAO();
    }

    public void crearTabla() {
        reservaDAO.crearTabla();
    }

    public boolean crearReserva(int habitacion_id, int hotel_id, int cliente_id, LocalDate fecha_inicio, LocalDate fecha_fin) throws ServiceExceptions {
        if (!reservaDAO.verificarReserva(habitacion_id, fecha_inicio.toString(), fecha_fin.toString())) {
            throw new ServiceExceptions("No se puede reservar la habitacion porque ya esta reservada.");
        }
        if (!fecha_inicio.isBefore(fecha_fin)) {
            throw new ServiceExceptions("No se puede reservar la habitacion, ingrese los datos correctamente.");
        }

        Cliente cliente = clienteDAO.obtenerClientePorId(cliente_id);
        Hotel hotel = hotelDAO.obtenerHotelPorId(hotel_id);
        Habitacion habitacion = habitacionDAO.obtenerHabitacionPorHabitacionId(habitacion_id);
        if (cliente == null || hotel == null || habitacion == null) {
            throw new ServiceExceptions("No se encontraron los datos para crear una reserva.");
        }

        reservaDAO.insertarReserva(habitacion.getIdHotel(), habitacion.getIdHabitacion(), cliente.getIdCliente(), fecha_inicio, fecha_fin, "Activado");
        return true;
    }

    public List<ReservaDTO> obtenerReservas() {
        List<Reserva> reservas = reservaDAO.obtenerReservas();

        return procesarLista(reservas);
    }

    public List<ReservaDTO> obtenerReservasClienteId(int cliente_id) {
        List<Reserva> reservas = reservaDAO.obtenerReservasPorCliente(cliente_id);

        return procesarLista(reservas);
    }

    public List<ReservaDTO> obtenerReservasPorHotelId(int hotel_id) {
        List<Reserva> reservas = reservaDAO.obtenerReservasPorHotel(hotel_id);

        return procesarLista(reservas);
    }

    public List<ReservaDTO> obtenerReservasPorEstado(String estado) {
        List<Reserva> reservas = reservaDAO.obtenerReservasPorEstado(estado);

        return procesarLista(reservas);
    }

    public List<ReservaDTO> obtenerReservasPorHabitacion(int habitacion_id) {
        List<Reserva> reservas = reservaDAO.obtenerReservasPorHabitacion(habitacion_id);

        return procesarLista(reservas);
    }

    public void eliminarReserva(int reserva_id) throws ServiceExceptions {
        Reserva reserva = reservaDAO.obtenerReservaPorId(reserva_id);

        if (reserva == null) {
            throw new ServiceExceptions("No se encontro la reserva.");
        }

        reservaDAO.eliminarReserva(reserva.getIdReserva());
        logger.log(Level.INFO, "Se ha eliminado la reserva correctamente reserva_id eliminada : {0}", reserva_id);
    }

    // UTILS 
    public List<ReservaDTO> procesarLista(List<Reserva> reservas) {
        List<ReservaDTO> reservasDTO = new ArrayList<>();
        for (Reserva reserva : reservas) {
            ReservaDTO reservaDTO = new ReservaDTO(reserva.getIdReserva(),
                    reserva.getIdCliente(),
                    reserva.getIdHotel(),
                    reserva.getIdHabitacion(),
                    reserva.getFechaInicio(),
                    reserva.getFechaFin(), reserva.getEstado());
            reservasDTO.add(reservaDTO);
        }
        return reservasDTO;
    }
}
