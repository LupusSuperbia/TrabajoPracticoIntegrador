/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.Interface;

import Model.Admin;
import Model.Cliente;
import Util.Rol;
import java.util.List;

/**
 *
 * @author asamsu
 */
public interface PersonaDAOInterface {
    public void crearTabla();
    public String obtenerRolPorDNI(String DNI);
    public void insertarCliente(String nombre, 
            String apellido, String DNI, 
            String email, 
            Rol rol);
    public void insertarAdmin(String nombre, 
            String apellido, 
            String DNI, 
            String email, 
            Rol rol);
    public void insertarPersona(String nombre, 
            String apellido, 
            String DNI, 
            String email, 
            Rol rol);
    
    public List<Cliente> obtenerClientes();
    public Cliente obtenerClientePorDNI(String DNI);
    public Cliente obtenerClientePorId(int cliente_id) ;
    public Cliente obtenerClientePorEmail(String email);
    public Cliente actualizarCliente(String DNIBusqueda, 
            String nombreActualizar, 
            String apellidoActualizar, 
            String DNIActualizar, 
            String email);
    public void eliminarCuentaCliente(String DNI);
    public List<Admin> obtenerAdmins();
    public Admin obtenerAdminPorDNI(String DNI);
    public Admin obtenerAdminPorId(int admin_id);
    public Admin obtenerAdminPorEmail(String email);
    public Admin actualizarAdmin(String DNIBusqueda, 
            String nombreActualizar, 
            String apellidoActualizar, 
            String DNIActualizar, 
            String email);
    public void eliminarCuentaAdmin(String DNI);
    
    
}
