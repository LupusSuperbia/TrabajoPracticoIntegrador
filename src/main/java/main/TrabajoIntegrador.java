/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;
import DAO.ClienteModel;
import DAO.HabitacionesModel;
import DAO.HotelModel;
import Model.Cliente;
import Model.Habitacion;
import Model.Hotel;
import Util.ConnectionBD;
import java.sql.Connection;
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
        HotelModel modelHotel = new HotelModel();
        HabitacionesModel modelHabitacion = new HabitacionesModel();
        modelHabitacion.crearTabla();
        modelHotel.crearTabla();
        modelHotel.insertarHotel("Pasarela", 4);
        modelHotel.insertarHotel("Pasarela2423", 5);
        modelHotel.insertarHotel("Pasarelasdada", 3);
        modelHotel.insertarHotel("HolaBienvenidos", 3);
        modelHabitacion.insertarHabitacion(5, 0, 3);
        modelHabitacion.insertarHabitacion(2, 0, 3);
        modelHabitacion.insertarHabitacion(4, 1, 3);
        modelHabitacion.insertarHabitacion(4, 1, 2);
        modelHabitacion.insertarHabitacion(4, 1, 1);
        modelHotel.eliminarHotel("Pasarela");
       
        
        prueba = modelHotel.obtenerHoteles();
        for (Hotel hotel : prueba) {
            System.out.println(hotel);
        }
        
         
        System.out.println(modelHotel.obtenerHotelPorNombre("Pasarela2423"));
      
        pruebaHabitacion = modelHabitacion.obtenerHabitacionesPorReserva(0);
        for (Habitacion habitacion : pruebaHabitacion) {
            System.out.println(habitacion);
        }
         
    }
}
