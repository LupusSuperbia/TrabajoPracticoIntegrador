/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import AppLogic.MenuCliente;
import DAO.HabitacionesModel;
import DAO.HotelModel;
import DAO.ReservaModel;
import DTO.ClienteDTO;
import DTO.HabitacionDTO;
import DTO.HotelDTO;
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

    public static void main(String[] args) {

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

        serviceCliente.registrarCliente("Ailen", "pmm", "4242", "asdasd@gmail.com");

        ClienteDTO cliente = serviceCliente.iniciarSesion("4242");
        System.out.println(cliente);
        serviceHotel.ingresarHotel("UnCUyo", 3);
        serviceHabitacion.ingresarHabitacion(1, 3);
        serviceHabitacion.ingresarHabitacion(1, 3);
        serviceHabitacion.ingresarHabitacion(1, 3);
        serviceHabitacion.ingresarHabitacion(1, 3);

        HotelDTO hotel = serviceHotel.obtenerHotelesYHabitaciones(1);
        List<HabitacionDTO> habitaciones = hotel.getHabitaciones();
        List<HabitacionDTO> habitacionesSe = serviceHabitacion.obtenerHabitacionesPorHotelId(1);
        for (HabitacionDTO object : habitaciones) {
            System.out.println(object);
        }
    }
}
