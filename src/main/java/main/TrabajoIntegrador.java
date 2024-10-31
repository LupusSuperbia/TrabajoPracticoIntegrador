/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import AppLogic.iniciarSesion;
import Exceptions.ServiceExceptions;
import Service.ServiceCliente;
import Service.ServiceHabitacion;
import Service.ServiceHotel;
import Service.ServiceReserva;

/**
 *
 * @author Juan Cruz
 */
//MODIFICAR VERSIÒN DE MAVEN EN /Proyect Files/pom.xml
//Usar comando mvn -v en la consola de comandos!
public class TrabajoIntegrador {

    public static void main(String[] args) throws ServiceExceptions {

        iniciarSesion inicio = new iniciarSesion();
        ServiceCliente serviceCliente = new ServiceCliente();
        ServiceHotel serviceHotel = new ServiceHotel();
        ServiceHabitacion serviceHabitacion = new ServiceHabitacion();
        ServiceReserva serviceReserva = new ServiceReserva();

        serviceCliente.crearTabla();
        serviceHabitacion.crearTabla();
        serviceHotel.crearTabla();
        serviceReserva.crearTabla();
        inicio.IniciarSesion();
        
    }
}
