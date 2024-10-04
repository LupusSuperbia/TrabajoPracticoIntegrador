/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author mateo
 */
public class Cliente extends Persona {

    protected int IdCliente;
    protected boolean Reserva = false;
    protected int IdHabitacion;

    public Cliente(String nombre, String apellido, int DNI) {
        super(nombre, apellido, DNI);
    }

    public Cliente(int IdCliente, String nombre, String apellido, int DNI) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

}
