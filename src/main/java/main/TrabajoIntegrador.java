/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import AppLogic.MenuCliente;
import DAO.HabitacionesDAO;
import DAO.HotelDAO;
import DAO.ReservaDAO;
import DTO.ClienteDTO;
import DTO.HabitacionDTO;
import DTO.HotelDTO;
import Exceptions.ServiceExceptions;
import Model.Cliente;
import Model.Habitacion;
import Model.Hotel;
import Model.Reserva;
import Service.ServiceCliente;
import Service.ServiceHabitacion;
import Service.ServiceHotel;
import Service.ServiceReserva;
import Util.ConnectionBD;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Juan Cruz
 */
//MODIFICAR VERSIÒN DE MAVEN EN /Proyect Files/pom.xml
//Usar comando mvn -v en la consola de comandos!
public class TrabajoIntegrador {

    public static void main(String[] args) throws ServiceExceptions {

//            Menu menu = new Menu();
//            
//            menu.mostrarMenu();
        ServiceCliente serviceCliente = new ServiceCliente();
        ServiceHotel serviceHotel = new ServiceHotel();
        ServiceHabitacion serviceHabitacion = new ServiceHabitacion();
        ServiceReserva serviceReserva = new ServiceReserva();

        serviceCliente.crearTabla();
        serviceHabitacion.crearTabla();
        serviceHotel.crearTabla();
        serviceReserva.crearTabla();
        boolean salir = serviceHotel.ingresarHotel("Anahse", 5);
        HotelDTO hotelPrueba = serviceHotel.buscarHotelPorNombre("Anashe");
        serviceHabitacion.ingresarHabitacion(hotelPrueba.getIdHotel(), 10);
             

    }
}
