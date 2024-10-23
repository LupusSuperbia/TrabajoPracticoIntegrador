/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Model.Persona;

/**
 *
 * @author asamsu
 */
public class ClienteDTO extends Persona{
    protected int IdCliente;
    protected boolean Reserva = false;
    protected int IdHabitacion;

    public ClienteDTO(int IdCliente, String nombre, String apellido, String DNI) {
        super(nombre, apellido, DNI);
        this.IdCliente = IdCliente;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    public boolean isReserva() {
        return Reserva;
    }

    public void setReserva(boolean Reserva) {
        this.Reserva = Reserva;
    }


    @Override
    public String toString() {
        return "{ Nombre : " + this.nombre + " "
                + "Apellido : " + this.apellido + " "
                + "DNI : "  + this.DNI + "}"; 
    }
}