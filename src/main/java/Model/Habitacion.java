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

    protected static int IdHabitacion=1;
    protected boolean reservado = false;
    protected int cantHuespedes;

    public Habitacion() {
    }

    public Habitacion(int IdHabitacion, int cantHuespedes) {
        this.IdHabitacion = createId();
        this.cantHuespedes = cantHuespedes;
    }

    public int createId() {

        IdHabitacion = +1;
        return IdHabitacion;        
        
    }

}
