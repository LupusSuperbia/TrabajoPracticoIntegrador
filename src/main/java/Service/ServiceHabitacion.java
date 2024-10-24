/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.HabitacionesModel;
import DAO.HotelModel;
import DTO.HabitacionDTO;
import Model.Habitacion;
import Model.Hotel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author asamsu
 */
public class ServiceHabitacion {

    private final HabitacionesModel habitacionDAO;
    private final HotelModel hotelDAO;

    public ServiceHabitacion() {
        this.habitacionDAO = new HabitacionesModel();
        this.hotelDAO = new HotelModel();
    }

    public void crearTabla() {
        habitacionDAO.crearTabla();
    }

    public boolean ingresarHabitacion(int hotel_id, int tamanio) {
        Hotel hotel = hotelDAO.obtenerHotelPorId(hotel_id);

        if (hotel == null) {
            System.out.println("El hotel al que quiere ingresar la nueva habitacion "
                    + "no existe");
            return false;
        }

        habitacionDAO.insertarHabitacion(tamanio, hotel.getIdHotel());
        System.out.println("Se ha agregado correctamente una habitacion al hotel : "
                + +hotel.getIdHotel() + " " + hotel.getNombre());
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
        List<HabitacionDTO> habitacionesDTO = new ArrayList<>();
        return procesarHabitaciones(habitacionesModel);
    }

    public List<HabitacionDTO> obtenerHabitacionesPorHotelId(int hotel_id) {
        List<Habitacion> habitacionesModel = habitacionDAO.obtenerHabitacionesPorHotelId(hotel_id);
        List<HabitacionDTO> habitacionesDTO = new ArrayList<>();
        return procesarHabitaciones(habitacionesModel);
    }

    public List<HabitacionDTO> obtenerHabitacionesPorTamaño(int tamanio) {
        if (tamanio < 0) {
            throw new IllegalArgumentException("Por favor, ingrese correctamente los datos pedidos");
        }
        List<Habitacion> habitacionesModel = habitacionDAO.obtenerHabitacionPorTamanio(tamanio);
        return procesarHabitaciones(habitacionesModel);
    }

    public HabitacionDTO obtenerHabitacionPorId(int habitacion_id) {
        if (habitacion_id < 0) {
            throw new IllegalArgumentException("Por favor, ingrese correctamente los datos pedidos");
        }
        Habitacion habitacion = habitacionDAO.obtenerHabitacionPorHabitacionId(habitacion_id);

        return new HabitacionDTO(habitacion.getIdHabitacion(),
                habitacion.getCantHuespedes(),
                habitacion.getIdHotel());
    }

    public HabitacionDTO actualizarHabitacion(int habitacion_id, int tamanio) {
        if (habitacion_id < 0) {
            throw new IllegalArgumentException("Por favor, ingrese correctamente los datos pedidos");
        }
        if (tamanio < 0) {
            throw new IllegalArgumentException("Por favor, ingrese correctamente los datos pedidos");
        }
        Habitacion habitacionModificado = habitacionDAO.actualizarHabitacionTamanio(habitacion_id, tamanio);

        return new HabitacionDTO(habitacionModificado.getIdHabitacion(),
                habitacionModificado.getCantHuespedes(),
                habitacionModificado.getIdHotel());

    }

    public void eliminarHabitacion(int habitacion_id) {
        if (habitacion_id < 0) {
            throw new IllegalArgumentException("Por favor, ingrese correctamente los datos pedidos");
        }
        Habitacion habitacion = habitacionDAO.obtenerHabitacionPorHabitacionId(habitacion_id);
        if (habitacion == null) {
            throw new NoSuchElementException("No se ha encontrado ninguna habitacion con ese ID");
        }
        habitacionDAO.eliminarHabitacion(habitacion.getIdHabitacion());

    }

}
