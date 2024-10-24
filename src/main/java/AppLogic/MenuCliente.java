/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AppLogic;

import DAO.ClienteModel;
import DAO.HabitacionesModel;
import DAO.HotelModel;
import DAO.ReservaModel;
import DTO.HabitacionDTO;
import DTO.HotelDTO;
import Model.Hotel;
import Service.ServiceHabitacion;
import Service.ServiceHotel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mateo
 */
public class MenuCliente {

    ServiceHotel serviceHotel = new ServiceHotel();
    ServiceHabitacion serviceHabitacion = new ServiceHabitacion();
    ClienteModel modeloCliente = new ClienteModel();
    ReservaModel modeloReserva = new ReservaModel();
    List<Hotel> hoteles = new ArrayList<>();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public void mostrarMenu() {

        System.out.println("Opciones:");
        System.out.println("1 Mostrar Hoteles");
        System.out.println("2 Mostrar habitaciones");
        System.out.println("3 Mostrar Habitaciones por identificacion del hotel");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        int eleccion = leer.nextInt();
        int opcion;
        switch (eleccion) {
            case 1:
                mostrarHoteles();
                break;
            case 2:
                mostrarHabitaciones(serviceHabitacion.obtenerHabitaciones());
                break;
            case 3:
                System.out.println("Ingrese el id del hotel para ver sus habitaciones.");
                opcion = leer.nextInt();
                mostrarHabitaciones(serviceHabitacion.obtenerHabitacionesPorHotelId(opcion));
                break;
            case 4:
                System.out.println("Ingrese el id de la habitacion para ver sus especificaciones.");
                opcion = leer.nextInt();
                serviceHabitacion.obtenerHabitacionPorId(opcion);
                break;

        }
    }

    public void mostrarHoteles() {
        List<HotelDTO> hoteles = serviceHotel.obtenerHoteles();
        for (HotelDTO hotel : hoteles) {
            System.out.println(hotel);
        }
    }

    public void mostrarHabitaciones(List<HabitacionDTO> habitaciones) {
        for (HabitacionDTO habitacione : habitaciones) {
            System.out.println(habitacione);
        }
    }
}
