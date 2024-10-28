/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AppLogic;

import DAO.HabitacionesDAO;
import DAO.HotelDAO;
import DAO.PersonaDAO;
import DAO.ReservaDAO;
import DTO.ClienteDTO;
import DTO.HabitacionDTO;
import DTO.HotelDTO;
import DTO.ReservaDTO;
import Model.Hotel;
import Model.Reserva;
import Service.ServiceHabitacion;
import Service.ServiceHotel;
import Service.ServiceReserva;
import java.time.LocalDate;
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
    PersonaDAO modeloCliente = new PersonaDAO();
    ServiceReserva serviceReserva = new ServiceReserva();
    List<Hotel> hoteles = new ArrayList<>();
    HabitacionDTO habitacion;
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public void mostrarMenuCliente(ClienteDTO cliente) throws Exception {
        int eleccion;
        do {
            System.out.println("Opciones:");
            System.out.println("0 Para salir");
            System.out.println("1 Mostrar hoteles.");
            System.out.println("2 Mostrar habitaciones.");
            System.out.println("3 Mostrar habitaciones por identificacion del hotel.");
            System.out.println("4 Obtener habitaciones por su id.");
            System.out.println("5 Obtener habitaciones por tamaño.");
            System.out.println("6 Crear reserva.");
            System.out.println("7 Mira tus reservas.");

            eleccion = leer.nextInt();
            int opcion;

            switch (eleccion) {
                case 0:
                    System.out.println("¡Gracias por utilizar nuestro servicio!.");
                    break;
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
                case 5:
                    System.out.println("Ingrese el tamaño del que desea la habitacion.");
                    opcion = leer.nextInt();
                    serviceHabitacion.obtenerHabitacionesPorTamaño(opcion);
                    break;
                case 6:
                    // Las fechas se tienen que añadir de la siguiente forma LocalDate.of(año, mes, dia).
                    CrearReserva(cliente);
                    break;
                case 7:
                    obtenerReservaPorCliente(cliente);
                   break;
                default:
                    System.out.println("Dato invalido.");
                    break;
            }
        } while (eleccion != 0);

    }

    public void mostrarHoteles() {
        List<HotelDTO> hoteles = serviceHotel.obtenerHoteles();
        int contador = 0;
        for (HotelDTO hotel : hoteles) {
            contador += 1;
            System.out.println(contador + " " + hotel);

        }
    }

    public void mostrarHabitaciones(List<HabitacionDTO> habitaciones) {
        for (HabitacionDTO habitacione : habitaciones) {
            System.out.println(habitacione);
        }
    }

    public void CrearReserva(ClienteDTO cliente) throws Exception {

        int anio, mes, dia, anioFin, mesFin, diaFin, id;
        boolean comprobacion = true;
        do {
            System.out.println("Fecha de inicio de su reserva.");
            System.out.println("Ingrese año.");
            anio = leer.nextInt();
            System.out.println("Ingrese mes.");
            mes = leer.nextInt();
            System.out.println("Ingrese dia.");
            dia = leer.nextInt();
            System.out.println("Fecha de finalizacion de su reserva.");
            System.out.println("Ingrese año.");
            anioFin = leer.nextInt();
            System.out.println("Ingrese mes.");
            mesFin = leer.nextInt();
            System.out.println("Ingrese dia.");
            diaFin = leer.nextInt();
            System.out.println("Ingrese el id de su habitacion");
            id = leer.nextInt();
            habitacion = serviceHabitacion.obtenerHabitacionPorId(id);
            comprobacion = serviceReserva.crearReserva(habitacion.getIdHabitacion(), habitacion.getIdHotel(), cliente.getIdCliente(), LocalDate.of(anio, mes, dia), LocalDate.of(anioFin, mesFin, diaFin));
        } while (comprobacion == false);
    }
    
    public void obtenerReservaPorCliente(ClienteDTO cliente){
        List<ReservaDTO> reservas = serviceReserva.obtenerReservasClienteId(cliente.getIdCliente());
        for(ReservaDTO reserva: reservas){
            System.out.println(reserva);
        }
    }
}
