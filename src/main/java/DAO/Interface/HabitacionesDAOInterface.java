/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Interface;

import Model.Habitacion;
import java.util.List;

/**
 *
 * @author asamsu
 */
public interface HabitacionesDAOInterface {
    public void crearTabla();
    public void insertarHabitacion(int cant_huesped, int hotel_id);
    public List<Habitacion> obtenerHabitacionesPorHotelId(int hotel_id);
    public Habitacion obtenerHabitacionPorHabitacionId(int habitacion_id);
    public List<Habitacion> obtenerHabitaciones();
    public List<Habitacion> obtenerHabitacionPorTamanio(int tamanio);
    public Habitacion actualizarHabitacionTamanio(int habitacion_id, int tamanio);
    public void eliminarHabitacion(int habitacion_id);
    
}
