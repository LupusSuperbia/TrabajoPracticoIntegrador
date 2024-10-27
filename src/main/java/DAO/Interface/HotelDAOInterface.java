/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Interface;

import Model.Hotel;
import java.util.List;

/**
 *
 * @author asamsu
 */
public interface HotelDAOInterface {
    public void crearTabla();
    public void insertarHotel(String nombre, int estrellas);
    public Hotel actualizarHotelEnObjeto(Hotel hotel, String columna, Object valorModificado);
    public List<Hotel> obtenerHoteles();
    public Hotel obtenerHotelPorId(int IdHotel);
    public Hotel obtenerHotelPorNombre(String nombre);
    public List<Hotel> obtenerHotelesPorEstrella(int estrellas);
    public Hotel actualizarHotel(String columna, Object valorModificado, int hotel_id);
    public void eliminarHotel(String nombre);
}
