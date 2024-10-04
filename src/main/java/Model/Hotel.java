/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author mateo
 */
public abstract class Hotel {

    protected static int IdHotel=1;
    protected String nombre;
    protected String direccion;
    protected int telefono;

    public Hotel() {
        IdHotel = createId();
    }

    public int createId() {

        IdHotel = +1;
        return IdHotel;

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

}
