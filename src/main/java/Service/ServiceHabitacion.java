/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.HabitacionesModel;
import DAO.HotelModel;
import Model.Hotel;

/**
 *
 * @author asamsu
 */
public class ServiceHabitacion {
    private final HabitacionesModel habitacionDAO;
    private final HotelModel hotelDAO;
    
    
    public ServiceHabitacion(){
        this.habitacionDAO = new HabitacionesModel();
        this.hotelDAO = new HotelModel();
    }
    
    public void crearTabla(){
        habitacionDAO.crearTabla();
    }
    
    public boolean ingresarHabitacion(int hotel_id, int tamanio){
        Hotel hotel = hotelDAO.obtenerHotelPorId(hotel_id);
        
        if(hotel == null){
            System.out.println("El hotel al que quiere ingresar la nueva habitacion "
                    + "no existe");
            return false;
        }
        
        habitacionDAO.insertarHabitacion(tamanio, hotel.getIdHotel());
        System.out.println("Se ha agregado correctamente una habitacion al hotel : " +
          + hotel.getIdHotel() + " " + hotel.getNombre() );
        return true;
    }
    
}
