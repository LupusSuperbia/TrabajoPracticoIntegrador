/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.HotelModel;
import DTO.HotelDTO;
import Model.Hotel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class ServiceHotel {
    private final HotelModel hotelDAO;

    public ServiceHotel() {
        this.hotelDAO = new HotelModel();
    }
    
    
    public void crearTabla(){
        hotelDAO.crearTabla();
    }
    
        // Utils
    private List<HotelDTO> procesarHoteles(List<Hotel> hotelesModel){
         List<HotelDTO> hotelesDTO = new ArrayList<>();
        
        for (Hotel hotel : hotelesModel) {
            HotelDTO hotelDTO = new HotelDTO(hotel.getNombre(), 
                    hotel.getIdHotel(), 
                    hotel.getEstrellas(), 
                    hotel.getCantidadHabitaciones());
            hotelesDTO.add(hotelDTO);
        }
        
        return hotelesDTO;
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
    
    public List<HotelDTO> obtenerHoteles(){
        List<Hotel> hotelesModel = hotelDAO.obtenerHoteles();
        return procesarHoteles(hotelesModel);
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
        return new HotelDTO(hotel.getNombre(),
                hotel.getIdHotel(),
                hotel.getEstrellas(),
                hotel.getCantidadHabitaciones());
    }
    
    public List<HotelDTO> buscarHotelesPorEstrella(int estrellas){
        if(estrellas < 0 ) {
            throw new IllegalArgumentException("Por favor ingrese un numero mayor a 0");
        }
        
        List<Hotel> hotelesModel = hotelDAO.obtenerHotelesPorEstrella(estrellas);
        return procesarHoteles(hotelesModel);
    }
    
    
    public void eliminarHotel(String nombre){
        if(nombre.isBlank()){
            throw new IllegalArgumentException("Por favor ingrese un nombre");
        }
        hotelDAO.eliminarHotel(nombre);
        System.out.println("Se ha eliminado correctamente el hotel: " + nombre);
    }
    
    
    
    
    
}
