/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Model.Habitacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class HotelDTO {
    protected  int IdHotel=1;
    protected String nombre;
    protected int estrellas;
    protected int cantidadHabitaciones;
    protected List<HabitacionDTO> habitaciones;
    
    public HotelDTO(String nombre,int hotel_id, int estrellas, int habitaciones) {
        this.nombre = nombre;
        this.estrellas = estrellas;
        this.cantidadHabitaciones = habitaciones;
        this.IdHotel = hotel_id; 
    }

    public  int getIdHotel() {
        return IdHotel;
    }

    public  void setIdHotel(int IdHotel) {
        this.IdHotel = IdHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }
    
    

    public void setHabitaciones(List<Habitacion> habitaciones) {
        List<HabitacionDTO> habitacionesDTO = new ArrayList<>();
        
        for (Habitacion habitacion : habitaciones) {
            HabitacionDTO habitacionDTO = new HabitacionDTO(habitacion.getIdHabitacion(), habitacion.getCantHuespedes(), 
            habitacion.getIdHotel());
            habitacionesDTO.add(habitacionDTO);
        }
       this.habitaciones = habitacionesDTO;
    }
    
     public List<HabitacionDTO> getHabitaciones() {
          return habitaciones;
    }
    
    public void addHabitaciones(HabitacionDTO habitacion){
        habitaciones.add(habitacion);
    }
    
  //  public void agregarHabitacion(){
    //     habitaciones.add(habitacion);
    // }

    @Override
    public String toString() {
        return "{ Hotel Nombre: " + this.nombre + " "
                + "Hotel ID: "  + this.IdHotel + " " +
                 "Estrellas: " + this.estrellas + " " + 
                 "Habitaciones: " + this.cantidadHabitaciones + "}";  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
