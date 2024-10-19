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

    protected  int IdHabitacion=1;
    protected int numeroHabitacion=1;
    protected boolean reservado = false;
    protected int cantHuespedes;
    protected int idHotel;
    

    public Habitacion() {
    }

    public Habitacion(int habitacionId, int cantHuespedes, int reservado, int hotelId  ) {
        this.cantHuespedes = cantHuespedes;
        if(reservado == 1){ 
            this.reservado = true;
        } 
        this.idHotel = hotelId;
        //this.numeroHabitacion = numeroHabitacion;
        this.IdHabitacion = habitacionId;
    }

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
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
    
      @Override
    public String toString() {
        return "{ Habitacion cant_husped: " + this.cantHuespedes + " " +
                 "Reservado: " + this.reservado + " " + 
                 "Hotel: " + this.idHotel + "}";  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
