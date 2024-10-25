/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Util.Rol;

/**
 *
 * @author Juan Cruz
 */
public class Admin extends Persona{
    
    protected int IdAdmin;
    protected String email;
    
    public Admin(int idAdmin, String nombre, String apellido, String DNI, String email) {
        super(nombre, apellido, DNI);
        this.rol = Rol.ADMIN;
        this.IdAdmin = idAdmin;
        this.email = email;
    }
    


    public int getIdAdmin() {
        return IdAdmin;
    }

    public void setIdAdmin(int IdAdmin) {
        this.IdAdmin = IdAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
