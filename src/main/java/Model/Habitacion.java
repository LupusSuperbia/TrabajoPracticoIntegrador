/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author mateo
 */
public class Habitacion {

    protected int IdHabitacion=1;
    protected int numeroHabitacion=1;
    protected int cantHuespedes;
    protected int idHotel;
    

    public Habitacion() {
    }

    public Habitacion(int habitacionId, int cantHuespedes, int hotelId  ) {
        this.cantHuespedes = cantHuespedes;
        this.idHotel = hotelId;
        //this.numeroHabitacion = numeroHabitacion;
        this.IdHabitacion = habitacionId;
    }


    public int getCantHuespedes() {
        return cantHuespedes;
    }

    public void setCantHuespedes(int cantHuespedes) {
        this.cantHuespedes = cantHuespedes;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public int getIdHabitacion() {
        return IdHabitacion;
    }

    public void setIdHabitacion(int IdHabitacion) {
        this.IdHabitacion = IdHabitacion;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }
    
    
    
      @Override
    public String toString() {
        return "{ Habitacion "
                + "Habitacion Id: " + this.IdHabitacion + " "
                + "Tamaño: " + this.cantHuespedes + " " + 
                 "Hotel Id: " + this.idHotel + "}";  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
