/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AppLogic;

import DTO.ClienteDTO;
import Exceptions.ServiceExceptions;
import Service.ServiceCliente;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mateo
 */
public class iniciarSesion {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    ServiceCliente servicioCliente = new ServiceCliente();
    ClienteDTO clienteDato = null;

    public void IniciarSesion() {

        System.out.println("¿Tienes cuenta?, si ya tienes presiona un distinto de N.");
        if (!"N".equals(leer.next().toUpperCase())) {
            Inicio();
        } else {
            Registro();
        }
    }

    public ClienteDTO Inicio() {
        System.out.println("Ingresa tu DNI para ver si estas en el sistema.");
        String DNI = leer.next();
        boolean inicio = true;
        do {
            try {
                clienteDato = servicioCliente.iniciarSesion(DNI);
            } catch (ServiceExceptions e) {
                System.out.println(e);
            }
        } while (clienteDato == null);
        return clienteDato;
    }

    public ClienteDTO Registro() {
        boolean resultado = true;
        String nombre, apellido, email, DNI;
        System.out.println("Crea tu cuenta.");
        do {
            System.out.println("Ingresa el nombre.");
            nombre = leer.next();
            System.out.println("Ingresa el apellido.");
            apellido = leer.next();
            System.out.println("Ingresa el nombre.");
            DNI = leer.next();
            System.out.println("Ingresa tu email.");
            email = leer.next();
            try {
                resultado = servicioCliente.registrarCliente(nombre, apellido, DNI, email);

            } catch (ServiceExceptions e) {
                System.out.println(e);
            }
            if (resultado == false) {
                System.out.println("Intenta de vuelta");
            } else {
                try {
                    clienteDato = servicioCliente.buscarClienteDNI(DNI);
                } catch (Exception ex) {
                    Logger.getLogger(iniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (resultado == false);
        return clienteDato;

    }
}


