/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PersonaModel;
import DAO.HabitacionesModel;
import DAO.HotelModel;
import DAO.ReservaModel;
import Model.Cliente;
import Model.Habitacion;
import Model.Hotel;
import java.time.LocalDate;
/**
 *
 * @author asamsu
 */
public class ServiceReserva {
    private final ReservaModel reservaDAO;
    private final PersonaModel clienteDAO;
    private final HotelModel  hotelDAO;
    private final HabitacionesModel habitacionDAO;
    
    public ServiceReserva(){
        this.reservaDAO = new ReservaModel();
        this.clienteDAO = new PersonaModel();
        this.hotelDAO = new HotelModel();
        this.habitacionDAO = new HabitacionesModel();
    }
    
    public void crearTabla(){
        reservaDAO.crearTabla();
    }
    
    public boolean crearReserva(int habitacion_id, int hotel_id, int cliente_id, LocalDate fecha_inicio, LocalDate fecha_fin) throws Exception{
        if(!reservaDAO.verificarReserva(habitacion_id, fecha_inicio.toString(), fecha_fin.toString())){
            System.out.println("No se puede reservar la habitacion porque ya esta reservada");
            return false;
        }
        Cliente cliente = clienteDAO.obtenerClientePorId(cliente_id);
        Hotel hotel = hotelDAO.obtenerHotelPorId(hotel_id);
        Habitacion habitacion = habitacionDAO.obtenerHabitacionPorHabitacionId(habitacion_id);
        
        if(cliente == null || hotel == null || habitacion == null){
            System.out.println("No se encontraron los datos para crear una reserva");
            return false;
        }
        
        reservaDAO.insertarReserva(habitacion.getIdHotel(), habitacion.getIdHabitacion(), cliente.getIdCliente(), fecha_inicio, fecha_fin, "Activado");
        return true;
    }
    
    
}
