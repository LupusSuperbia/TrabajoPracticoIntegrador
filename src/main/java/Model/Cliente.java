/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Util.Rol;

/**
 *
 * @author mateo
 */
public class Cliente extends Persona {

    protected int IdCliente;
    protected boolean Reserva = false;
    protected String email;


    public Cliente(int IdCliente, String nombre, String apellido, String DNI, String email) {
        super(nombre, apellido, DNI);
        this.IdCliente = IdCliente;
        this.email = email;
        this.rol = Rol.USER;
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
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getApellido() {
        return apellido;
    }

    @Override
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String getDNI() {
        return DNI;
    }

    @Override
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    

    @Override
    public String toString() {
        return "{ Nombre : " + this.nombre + ""
                + "Apellido : " + this.apellido + ""
                + "DNI : "  + this.DNI + "}"; 
    }
    
    

}
