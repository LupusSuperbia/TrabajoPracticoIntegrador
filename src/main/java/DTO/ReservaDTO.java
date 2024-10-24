/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

/**
 *
 * @author asamsu
 */
public class ReservaDTO {
    protected int IdReserva;
    protected int IdCliente;
    protected int IdHotel;
    protected int idHabitacion;
    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected String estado;

    public ReservaDTO(int IdReserva, int IdCliente, int IdHotel, int idHabitacion, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.IdReserva = IdReserva;
        this.IdCliente = IdCliente;
        this.IdHotel = IdHotel;
        this.idHabitacion = idHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdReserva() {
        return IdReserva;
    }

    public void setIdReserva(int IdReserva) {
        this.IdReserva = IdReserva;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    public int getIdHotel() {
        return IdHotel;
    }

    public void setIdHotel(int IdHotel) {
        this.IdHotel = IdHotel;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    

    @Override
    public String toString() {
        return "{Reserva : {"
                + "reserva_id: " + this.IdReserva + " "
                + "hotel_id: " + this.IdHotel + " "
                + "cliente_id: "+ this.IdCliente + " "
                + "habitacion_id: "+ this.idHabitacion + " "
                + "fecha inicio: "+ this.fechaInicio.toString() +  " "
                + "fecha fin: "+ this.fechaFin.toString() +" "
                + "Estado: " + this.estado + "} }";
    }

}
