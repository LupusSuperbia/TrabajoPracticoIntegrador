/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;
import DAO.ClienteModel;
import DAO.HabitacionesModel;
import DAO.HotelModel;
import DAO.ReservaModel;
import Model.Cliente;
import Model.Habitacion;
import Model.Hotel;
import Model.Reserva;
import Util.ConnectionBD;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Juan Cruz
 */

//MODIFICAR VERSIÒN DE MAVEN EN /Proyect Files/pom.xml
//Usar comando mvn -v en la consola de comandos!

public class TrabajoIntegrador {

    public static void main(String[] args) {
        ClienteModel model = new ClienteModel();
        List<Hotel> prueba = new ArrayList<>();
        List<Habitacion> pruebaHabitacion = new ArrayList<>();
        List<Reserva> pruebaReserva = new ArrayList<>();
        HotelModel modelHotel = new HotelModel();
        HabitacionesModel modelHabitacion = new HabitacionesModel();
        ReservaModel modelReserva = new ReservaModel();
        modelHabitacion.crearTabla();
        modelHotel.crearTabla();
        modelReserva.crearTabla();
        
        modelHotel.insertarHotel("Pasarela", 4);
        modelHotel.insertarHotel("Pasarela2423", 5);
        modelHotel.insertarHotel("Pasarelasdada", 3);
        modelHotel.insertarHotel("HolaBienvenidos", 3);
        
        modelHabitacion.insertarHabitacion(5, 3);
        modelHabitacion.insertarHabitacion(2, 3);
        modelHabitacion.insertarHabitacion(4, 3);
        modelHabitacion.insertarHabitacion(4, 2);
        modelHabitacion.insertarHabitacion(4, 1);
        
        modelReserva.insertarReserva(3, 2, 3, LocalDate.now(), LocalDate.of(2024, 11, 21), "Activado");
        pruebaReserva = modelReserva.obtenerReservas();
        
        for (Reserva reserva : pruebaReserva) {
            System.out.println(reserva);
        }
        
        prueba = modelHotel.obtenerHoteles();
        for (Hotel hotel : prueba) {
            hotel.setHabitaciones(modelHabitacion.obtenerHabitacionPorHotelId(hotel.getIdHotel()));
            System.out.println(hotel);
        }
        System.out.println("hola");
        List<Habitacion> pruebaHabitacionHotel = new ArrayList<>();
        Hotel hotelprueba = prueba.getLast();
        System.out.println(prueba.size());
        pruebaHabitacionHotel = hotelprueba.getHabitaciones();
        System.out.println(pruebaHabitacionHotel);
        for (Habitacion habitacion : pruebaHabitacionHotel) {
            System.out.println(habitacion);
        }
        
         
      //  System.out.println(modelHotel.obtenerHotelPorNombre("Pasarela2423"));
      
       /* pruebaHabitacion = modelHabitacion.obtenerHabitacionesPorReserva(0);
        for (Habitacion habitacion : pruebaHabitacion) {
            System.out.println(habitacion);
        }
        
        System.out.println(modelHotel.ObtenerHotelPorId(2));
       */  
    }
}
