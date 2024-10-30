/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.HabitacionesDAO;
import DAO.HotelDAO;
import DTO.HabitacionDTO;
import Exceptions.ServiceExceptions;
import Model.Habitacion;
import Model.Hotel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author asamsu
 */
public class ServiceHabitacion extends ServiceBase{

    private final HabitacionesDAO habitacionDAO;
    private final HotelDAO hotelDAO;

    public ServiceHabitacion() {
        this.habitacionDAO = new HabitacionesDAO();
        this.hotelDAO = new HotelDAO();
    }

    public void crearTabla() {
        habitacionDAO.crearTabla();
    }

    public boolean ingresarHabitacion(int hotel_id, int tamanio) throws ServiceExceptions {
        Hotel hotel = hotelDAO.obtenerHotelPorId(hotel_id);

        if (hotel == null) {
           throw new ServiceExceptions("El hotel al que quiere ingresar la nueva habitacion "
                    + "no existe");
        }

        habitacionDAO.insertarHabitacion(tamanio, hotel.getIdHotel());
        logger.log(Level.INFO, "Se ha agregado correctamente una habitacion al hotel : {0} {1}", new Object[]{+hotel.getIdHotel(), hotel.getNombre()});
        return true;
    }

    // Utils 
    public List<HabitacionDTO> procesarHabitaciones(List<Habitacion> habitaciones) {
        List<HabitacionDTO> habitacionesDTO = new ArrayList<>();

        for (Habitacion habitacionModel : habitaciones) {
            HabitacionDTO habitacion = new HabitacionDTO(habitacionModel.getIdHabitacion(), habitacionModel.getCantHuespedes(), habitacionModel.getIdHotel());
            habitacionesDTO.add(habitacion);
        }
        return habitacionesDTO;
    }

    public List<HabitacionDTO> obtenerHabitaciones() {
        List<Habitacion> habitacionesModel = habitacionDAO.obtenerHabitaciones();
        return procesarHabitaciones(habitacionesModel);
    }

    public List<HabitacionDTO> obtenerHabitacionesPorHotelId(int hotel_id) {
        List<Habitacion> habitacionesModel = habitacionDAO.obtenerHabitacionesPorHotelId(hotel_id);
        return procesarHabitaciones(habitacionesModel);
    }

    public List<HabitacionDTO> obtenerHabitacionesPorTamaño(int tamanio) throws ServiceExceptions {
        if (tamanio < 0) {
            throw new ServiceExceptions("Por favor, ingrese correctamente los datos pedidos");
        }
        List<Habitacion> habitacionesModel = habitacionDAO.obtenerHabitacionPorTamanio(tamanio);
        return procesarHabitaciones(habitacionesModel);
    }

    public HabitacionDTO obtenerHabitacionPorId(int habitacion_id) throws ServiceExceptions {
        if (habitacion_id < 0) {
            throw new ServiceExceptions("Por favor, ingrese correctamente los datos pedidos");
        }
        Habitacion habitacion = habitacionDAO.obtenerHabitacionPorHabitacionId(habitacion_id);

        return new HabitacionDTO(habitacion.getIdHabitacion(),
                habitacion.getCantHuespedes(),
                habitacion.getIdHotel());
    }

    public HabitacionDTO actualizarHabitacion(int habitacion_id, int tamanio) throws ServiceExceptions {
        if (habitacion_id < 0) {
            throw new ServiceExceptions("Por favor, ingrese correctamente los datos pedidos");
        }
        if (tamanio < 0) {
            throw new ServiceExceptions("Por favor, ingrese correctamente los datos pedidos");
        }
        Habitacion habitacionModificado = habitacionDAO.actualizarHabitacionTamanio(habitacion_id, tamanio);

        return new HabitacionDTO(habitacionModificado.getIdHabitacion(),
                habitacionModificado.getCantHuespedes(),
                habitacionModificado.getIdHotel());

    }

    public void eliminarHabitacion(int habitacion_id) throws ServiceExceptions {
        if (habitacion_id < 0) {
            throw new ServiceExceptions("Por favor, ingrese correctamente los datos pedidos");
        }
        Habitacion habitacion = habitacionDAO.obtenerHabitacionPorHabitacionId(habitacion_id);
        if (habitacion == null) {
            throw new ServiceExceptions("No se ha encontrado ninguna habitacion con ese ID");
        }
        habitacionDAO.eliminarHabitacion(habitacion.getIdHabitacion());

    }

}
