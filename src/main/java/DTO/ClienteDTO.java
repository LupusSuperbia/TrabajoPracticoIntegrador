/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Util.Rol;


/**
 *
 * @author asamsu
 */
public class ClienteDTO extends PersonaDTO{
    protected int IdCliente;
    protected boolean Reserva = false;
    protected int IdHabitacion;
    protected  String email;


    public ClienteDTO(int IdCliente, String nombre, String apellido, String DNI,String email, Rol rol) {
        super(nombre, apellido, DNI);
        this.IdCliente = IdCliente;
        this.email = email;
        this.rol = rol;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "{ Nombre : " + this.nombre + " "
                + "Apellido : " + this.apellido + " "
                + "DNI : "  + this.DNI + "}"; 
    }
}
