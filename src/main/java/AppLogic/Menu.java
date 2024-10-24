/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AppLogic;

import DAO.ClienteModel;
import DAO.HabitacionesModel;
import DAO.HotelModel;
import DAO.ReservaModel;
import Model.Hotel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mateo
 */
public class Menu {
    
    HotelModel modeloHotel = new HotelModel();
    HabitacionesModel modeloHab = new HabitacionesModel();
    ClienteModel  modeloCliente = new ClienteModel();
    ReservaModel modeloReserva = new ReservaModel();
    List<Hotel> hoteles = new ArrayList<>();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    public void mostrarMenu(){
        
        System.out.println("Opciones:");
        System.out.println("1 Mostrar Hoteles");
        System.out.println("2 Mostrar Habitaciones por identificacion del hotel");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        int eleccion = leer.nextInt();
        int opcion;
        switch(eleccion){
            
            case 1: mostrarHoteles();
            case 2: 
                System.out.println("Ingrese el id del hotel para ver sus habitaciones.");
                opcion = leer.nextInt();
                modeloHab.obtenerHabitacionPorHotelId(opcion);
                
        }
    }
    
    public void mostrarHoteles(){
        hoteles = modeloHotel.obtenerHoteles();
        for(Hotel hotel : hoteles){
            System.out.println(hotel);
        }
            
                    
    }
}
