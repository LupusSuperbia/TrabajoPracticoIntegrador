/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.HabitacionesDAO;
import DAO.HotelDAO;
import DTO.HotelDTO;
import Exceptions.ServiceExceptions;
import Model.Hotel;
import Util.ValidarDatos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class ServiceHotel {

    private final HotelDAO hotelDAO;
    private final HabitacionesDAO habitacionDAO;
    private final ValidarDatos validador;
    public ServiceHotel() {
        this.hotelDAO = new HotelDAO();
        this.habitacionDAO = new HabitacionesDAO();
        this.validador = new ValidarDatos();
    }

    public void crearTabla() {
        hotelDAO.crearTabla();
    }

    // Utils
    public boolean ingresarHotel(String nombre, int estrellas) throws ServiceExceptions {
        Hotel hotel = hotelDAO.obtenerHotelPorNombre(nombre);
        if (hotel != null) {
            throw new ServiceExceptions("Ya existe un hotel con ese nombre");
        }
        hotelDAO.insertarHotel(nombre, estrellas);
        System.out.println("Se ha creado el hotel exitosamente");
        return true;
    }

    public List<HotelDTO> obtenerHoteles() {
        List<Hotel> hotelesModel = hotelDAO.obtenerHoteles();
        return procesarHoteles(hotelesModel);
    }

    public HotelDTO buscarHotelPorNombre(String nombre) throws ServiceExceptions {
        validador.validarDatosString(nombre, "Por favor ingrese un nombre valido");
        Hotel hotel = hotelDAO.obtenerHotelPorNombre(nombre);
        if (hotel == null) {
            System.out.println("No se ha encontrado ningun hotel con ese nombre");
            return null;
        }
        return crearHotelDTO(hotel);
    }

    public List<HotelDTO> buscarHotelesPorEstrella(int estrellas) throws ServiceExceptions {
        validador.validarDatosEnteros(estrellas, "Por favor ingrese un numero mayor a 0");

        List<Hotel> hotelesModel = hotelDAO.obtenerHotelesPorEstrella(estrellas);
        return procesarHoteles(hotelesModel);
    }

    public HotelDTO obtenerHotelYHabitaciones(int hotel_id) throws ServiceExceptions {
        validador.validarDatosEnteros(hotel_id, "Por favor ingrese un numero mayor a 0");
        Hotel hotel = hotelDAO.obtenerHotelPorId(hotel_id);

        hotel.setHabitaciones(habitacionDAO.obtenerHabitacionesPorHotelId(hotel_id));

        HotelDTO hotelDTO = crearHotelDTO(hotel);
        hotelDTO.setHabitaciones(hotel.getHabitaciones());
        return hotelDTO;
    }
    
        public HotelDTO obtenerHotelYHabitacionesPorNombre(String nombre) throws ServiceExceptions {
        validador.validarDatosString(nombre, "Por favor ingrese un numero mayor a 0");
        Hotel hotel = hotelDAO.obtenerHotelPorNombre(nombre);

        hotel.setHabitaciones(habitacionDAO.obtenerHabitacionesPorHotelId(hotel.getIdHotel()));

        HotelDTO hotelDTO = crearHotelDTO(hotel);
        hotelDTO.setHabitaciones(hotel.getHabitaciones());
        return hotelDTO;
    }

    public void eliminarHotel(String nombre) throws ServiceExceptions {
        validador.validarDatosString(nombre, "Por favor ingrese un nombre");
        hotelDAO.eliminarHotel(nombre);
        System.out.println("Se ha eliminado correctamente el hotel: " + nombre);
    }

    // Utils

    private List<HotelDTO> procesarHoteles(List<Hotel> hotelesModel) {
        List<HotelDTO> hotelesDTO = new ArrayList<>();

        for (Hotel hotel : hotelesModel) {
            hotelesDTO.add(crearHotelDTO(hotel));
        }

        return hotelesDTO;
    }
    
    private HotelDTO crearHotelDTO(Hotel hotel){
        return new HotelDTO(hotel.getNombre(),
                    hotel.getIdHotel(),
                    hotel.getEstrellas(),
                    hotel.getCantidadHabitaciones());
    }

}
