/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author mateo
 */
public class Hotel {

    protected static int IdHotel=1;
    protected String nombre;
    protected String direccion;
    protected int telefono;
    protected int estrellas;
    protected int cantidadHabitaciones;
    protected List<Habitacion> habitaciones;
    
    public Hotel(String nombre, int estrellas, int habitaciones) {
        this.nombre = nombre;
        this.estrellas = estrellas;
        this.cantidadHabitaciones = habitaciones;
        ++IdHotel; 
    }

    public static int getIdHotel() {
        return IdHotel;
    }

    public static void setIdHotel(int IdHotel) {
        Hotel.IdHotel = IdHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void Reserva() {

    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
       
    }
    
  //  public void agregarHabitacion(){
    //     habitaciones.add(habitacion);
    // }

    @Override
    public String toString() {
        return "{ Hotel Nombre: " + this.nombre + " " +
                 "Estrellas: " + this.estrellas + " " + 
                 "Habitaciones: " + this.cantidadHabitaciones + "}";  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    

}
