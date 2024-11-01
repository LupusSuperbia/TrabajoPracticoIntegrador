/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppLogic;

import DAO.ReservaDAO;
import DTO.AdminDTO;
import DTO.ClienteDTO;
import DTO.HotelDTO;
import DTO.ReservaDTO;
import Exceptions.ServiceExceptions;
import Model.Reserva;
import Service.ServiceAdmin;
import Service.ServiceCliente;
import Service.ServiceHotel;
import Service.ServiceReserva;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Cruz
 */
public class menuAdmin {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    ServiceCliente servicioCliente = new ServiceCliente();
    ServiceAdmin servicioAdmin = new ServiceAdmin();
    ServiceHotel servicioHotel = new ServiceHotel();
    ServiceReserva servicioReserva = new ServiceReserva();
    ReservaDAO reservaDAO = new ReservaDAO();
    ReservaDTO reserva;
    boolean flag = true;
    int option = 0;
    AdminDTO admin;

    public void menu(AdminDTO admin) throws ServiceExceptions, Exception {

        do {

            System.out.println("Ingrese la opción deseada: ");

            System.out.println("1 Agregar Hotel.");
            System.out.println("2 Eliminar Hotel.");
            System.out.println("3 Modificar Hotel.");
            System.out.println("4 Cambiar estado de reservas de clientes.");
            System.out.println("5 Eliminar Cliente.");
            System.out.println("6 Crear Usuario Administrador.");
            System.out.println("7 Eliminar Usuario Administrador. ");
            System.out.println("0 Para salir.");

            option = leer.nextInt();

            switch (option) {
                case 0:
                    System.out.println("Gracias por usar el servicio, " + admin.getNombre() + "." );
                    break;
                case 1:
                    crearHotel();
                    break;  
                case 2:
                    System.out.println("Ingrese el nombre del hotel");
                    String nombre = leer.next();
                    servicioHotel.eliminarHotel(nombre);
                    break;
                case 3:
                    modificarHotel();
                    break;
                case 4:
                    modificarEstadoReserva();
                    break;
                case 5:
                    eliminarCliente();
                    break;
                case 6:
                    registrarAdmin();
                    break;
                case 7:
                    eliminarAdmin();
                    break;
                default:
                    System.out.println("Valor ingresado no valido.");
                    break;

            }

        } while (option != 0);

    }

    public void crearHotel() {
        System.out.println("Ingresa el nombre a asignarle al hotel");
        String nombre = leer.next();
        int estrellas;
        System.out.println("Ingresa la cantidad su estrellas.");
        do {
            estrellas = leer.nextInt();
            if (estrellas < 1) {
                System.out.println("Numero invalido, ingresa de nuevo porfavor.");
            }
        } while (estrellas < 1);
        try {
            servicioHotel.ingresarHotel(nombre, estrellas);
        } catch (ServiceExceptions e) {
            System.out.println(e);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void modificarHotel() throws ServiceExceptions {
        String nombre, nuevoNombre;
        int nuevasEstrellas = 0;
        HotelDTO hotel;
        int opcion;
        do {
            System.out.println("Ingresa el hotel que quieres modificar.");
            nombre = leer.next();
            hotel = servicioHotel.buscarHotelPorNombre(nombre);
            if (hotel == null) {
                System.out.println("Nombre de hotel incorrecto, intenta de vuelta.");
            }
        } while (hotel == null);

        do {
            System.out.println("1 Modificar nombre.");
            System.out.println("2 Modificar estrellas.");
            System.out.println("0 Salir.");

            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Ingresa el nombre que deseas ponerle");
                    nuevoNombre = leer.next();
                    servicioHotel.ActualizarNombreHotel(nombre, nuevoNombre);
                    break;
                case 2:
                    System.out.println("Ingresa la cantidad de estrellas que deseas ponerle.");
                    System.out.println("Este hotel tiene " + hotel.getEstrellas() + " estrellas.");
                    nuevasEstrellas = leer.nextInt();
                    if (nuevasEstrellas < 1) {
                        System.out.println("Valor no valido");
                    } else if (nuevasEstrellas == hotel.getEstrellas()) {
                        System.out.println("Este hotel ya tiene estas estrellas.");
                    } else {
                        servicioHotel.ActualizarEstrellasHotel(nombre, nuevasEstrellas);
                    }
                    break;
            }

        } while (opcion != 0);

    }

    public void modificarEstadoReserva() {
        int numeroReserva;
        String estado;
        System.out.println("Ingresa el id de la reserva que necesitas modificar");
        System.out.println("Si no conoces el numero de la reserva, ingresa 0, sino ingresala directamente.");
        numeroReserva = leer.nextInt();
        if (numeroReserva == 0) {
            List<Reserva> lista = reservaDAO.obtenerReservas();
            for (Reserva reserva : lista) {
                System.out.println(reserva);
            }
            System.out.println("Ahora si, elija la reserva que desea cambiar su estado.");
            numeroReserva = leer.nextInt();
        }
        reserva = servicioReserva.obtenerReservaId(numeroReserva);
        if (reserva != null) {
            do {
                System.out.println("1 Cambiar a \"Activado\" .");
                System.out.println("2 Cambiar a \"Desactivado\".");
                System.out.println("0 Salir.");
                                          
                option = leer.nextInt();

                switch (option) {
                    case 1:
                        estado = "Activado";
                        servicioReserva.ActualizarEstadoReserva(reserva, estado);
                        break;
                    case 2:
                        estado = "Desactivado";
                        servicioReserva.ActualizarEstadoReserva(reserva, estado);
                        break;
                    case 0:
                        System.out.println("Eligio salir.");
                        break;
                    default:
                        System.out.println("Valor invalido.");
                        break;

                }

            } while (option < 0 || option > 2);

        }
    }

    public void eliminarAdmin() throws Exception {
        String DNI;
        do {
            System.out.println("Ingrese el DNI del Admin a eliminar, o 0 para salir.");
            DNI = leer.next();
            admin = servicioAdmin.buscarAdminDNI(DNI);
            if (admin != null) {
                servicioAdmin.eliminarAdmin(DNI);
            } else {
                System.out.println("No se encontró admin con ese DNI.");
                DNI = "0";
            }
        } while (DNI != "0");

    }

    public void eliminarCliente() throws ServiceExceptions, Exception {
        String DNI;
        do {
            System.out.println("Ingrese el DNI del cliente a eliminar.");
            DNI = leer.next();
            ClienteDTO cliente = servicioCliente.buscarClienteDNI(DNI);
            if (cliente != null) {
                servicioCliente.eliminarCliente(DNI);
            } else {
                System.out.println("No se ha encontrado cliente con ese DNI.");
            }
        } while (DNI != "0");
    }

    public void registrarAdmin() {
        boolean resultado = true;
        String nombre, apellido, email, DNI;
        System.out.println("Crea tu cuenta.");
        do {
            System.out.println("Ingresa el nombre.");
            nombre = leer.next();
            System.out.println("Ingresa el apellido.");
            apellido = leer.next();
            System.out.println("Ingresa el DNI.");
            DNI = leer.next();
            System.out.println("Ingresa tu email.");
            email = leer.next();
            try {
                resultado = servicioAdmin.registrarAdmin(nombre, apellido, DNI, email);

            } catch (ServiceExceptions e) {
                System.out.println(e);
            }
            if (resultado == false) {
                System.out.println("Intenta de vuelta");
            } else {
                try {
                    admin = servicioAdmin.buscarAdminDNI(DNI);
                } catch (Exception ex) {
                    Logger.getLogger(iniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (resultado == false);
    }
}