/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import AppLogic.MenuCliente;
import DAO.ClienteModel;
import DAO.HabitacionesModel;
import DAO.HotelModel;
import DAO.ReservaModel;
import DTO.ClienteDTO;
import DTO.HabitacionDTO;
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
        System.out.println(serviceHotel.buscarHotelPorNombre("UnCUyo"));
        serviceHabitacion.ingresarHabitacion(serviceHotel.buscarHotelPorNombre("UnCUyo").getIdHotel(), 3);
        System.out.println(serviceHotel.buscarHotelPorNombre("UnCUyo"));
        List<HabitacionDTO> habitaciones = serviceHabitacion.obtenerHabitacionesPorHotelId(serviceHotel.buscarHotelPorNombre("UnCUyo").getIdHotel());
        HabitacionDTO habitacionId = habitaciones.get(0);
        try {
            serviceReserva.crearReserva(habitacionId.getIdHabitacion(),habitacionId.getIdHotel(), cliente.getIdCliente(), LocalDate.of(2024, 10, 24), LocalDate.of(2024, 10, 30));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }
}
