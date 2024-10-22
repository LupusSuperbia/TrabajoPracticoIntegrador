/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.HotelModel;
import DTO.HotelDTO;
import Model.Hotel;

/**
 *
 * @author asamsu
 */
public class ServiceHotel {
    private HotelModel hotelDAO;

    public ServiceHotel() {
        this.hotelDAO = new HotelModel();
    }
    
    
    public void crearTabla(){
        hotelDAO.crearTabla();
    }
    

    
    public boolean ingresarHotel(String nombre, int estrellas){
        Hotel hotel = hotelDAO.obtenerHotelPorNombre(nombre);
        if(hotel != null){
            System.out.println("Ya existe un hotel con ese nombre");
            return false;
        }
        hotelDAO.insertarHotel(nombre, estrellas);
        System.out.println("Se ha creado el hotel exitosamente");
        return true;
    }
    
    public HotelDTO buscarHotelPorNombre(String nombre){
        if(nombre.isBlank()){
            System.out.println("Por favor ingrese un nombre valido");
            return null;
        }
        Hotel hotel = hotelDAO.obtenerHotelPorNombre(nombre);
        if(hotel == null){
            System.out.println("No se ha encontrado ningun hotel con ese nombre");
            return null;
        }
        return new HotelDTO(hotel.getNombre(), hotel.getIdHotel(), hotel.getEstrellas(), hotel.getCantidadHabitaciones());
    }
    
    
    
    
}
