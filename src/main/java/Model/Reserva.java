/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author mateo
 */
public class Reserva {

    protected int IdReserva;
    protected int IdCliente;
    protected int IdHotel;
    protected int idHabitacion;
    protected int CantDias;

    public Reserva() {
    }

    public Reserva(int IdReserva, int IdCliente, int IdHotel, int idHabitacion, int CantDias) {
        this.IdReserva = IdReserva;
        this.IdCliente = IdCliente;
        this.IdHotel = IdHotel;
        this.idHabitacion = idHabitacion;
        this.CantDias = CantDias;
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

    public int getCantDias() {
        return CantDias;
    }

    public void setCantDias(int CantDias) {
        this.CantDias = CantDias;
    }

    
    
}
