/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppLogic;

import DTO.AdminDTO;
import DTO.HotelDTO;
import Exceptions.ServiceExceptions;
import Service.ServiceAdmin;
import Service.ServiceHotel;
import java.util.Scanner;

/**
 *
 * @author Juan Cruz
 */
public class menuAdmin {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    ServiceAdmin servicioAdmin = new ServiceAdmin();
    ServiceHotel servicioHotel = new ServiceHotel();
    boolean flag = true;
    int option = 0;

    public void menu(AdminDTO admin) throws ServiceExceptions {

        do {

            System.out.println("Ingrese la opción deseada: ");

            System.out.println("1 Agregar Hotel.");
            System.out.println("2 Eliminar Hotel.");
            System.out.println("3 Modificar Hotel.");
            System.out.println("4 Cambiar estado de reservas de clientes.");
            System.out.println("5 Eliminar Cliente.");
            System.out.println("6 Crear Usuario Administrador.");
            System.out.println("7 Eliminar Usuario Administrador. ");

            option = leer.nextInt();

            switch (option) {

                case 1:
                    crearHotel();
                case 2:
                    System.out.println("Ingrese el nombre del hotel");
                    String nombre = leer.next();
                    servicioHotel.eliminarHotel(nombre);
                    break;
                case 3:
                    modificarHotel();
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
}
