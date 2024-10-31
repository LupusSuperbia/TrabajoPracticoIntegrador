/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AppLogic;

import DTO.AdminDTO;
import DTO.ClienteDTO;
import DTO.PersonaDTO;
import Exceptions.ServiceExceptions;
import Service.ServiceAdmin;
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
    ServiceAdmin servicioAdmin = new ServiceAdmin();
    ClienteDTO clienteDato = null;
    AdminDTO AdminDato = null;
    PersonaDTO PersonaDato = null;
    MenuCliente menuCliente = new MenuCliente();
    menuAdmin AdminMenu = new menuAdmin();
    String Rol = "";
    String DNI;
    
    public void IniciarSesion() {
        do {
            System.out.println("¿Tienes cuenta?, si ya tienes presiona un distinto de N.");
            if (!"N".equals(leer.next().toUpperCase())) {
                try {
                    Rol = Inicio();

                    if (Rol != "") {
                        System.out.println("¡Se inició sesion correctamente!");
                        if(Rol == "USER")
                        clienteDato = servicioCliente.buscarClienteDNI(DNI);
                        menuCliente.mostrarMenuCliente(clienteDato);
                        if(Rol == "ADMIN")
                        AdminDato = servicioAdmin.buscarAdminDNI(DNI);
                        AdminMenu.menu(AdminDato);
                    }else{
                    }
                } catch (ServiceExceptions e) {
                    System.out.println(e.getMessage());
                } catch (Exception ex) {
                    Logger.getLogger(iniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    clienteDato = Registro();
                    menuCliente.mostrarMenuCliente(clienteDato);
                } catch (ServiceExceptions e) {
                    System.out.println(e.getMessage());

                } catch (Exception ex) {
                    Logger.getLogger(iniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (clienteDato == null);
    }

    public String Inicio() {
        String Rol = "";
        do {
            try {
                System.out.println("Ingresa tu DNI para ver si estas en el sistema, o 0 para salir.");
                DNI = leer.next();
                Rol = servicioCliente.obtenerRol(DNI);
                if(Rol == "" && DNI != "0"){
                    System.out.println("No se encontro persona por DNI");
                }
            } catch (ServiceExceptions e) {
                System.out.println(e);
            }
        } while (Rol == "" && DNI != "0");
        if(DNI == "0"){
            return "";
        }
        return Rol;
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
            System.out.println("Ingresa el DNI.");
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
