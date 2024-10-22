/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ClienteModel;
import DTO.ClienteDTO;
import Model.Cliente;

/**
 *
 * @author asamsu
 */
public class ServiceCliente {
    private final ClienteModel clienteDAO ;
    
    public ServiceCliente(){
        this.clienteDAO = new ClienteModel();
    }
    
    public void crearTabla(){
        clienteDAO.crearTabla();
    }
    
    public boolean registrarCliente(String nombre, String apellido, String DNI, String email){
        if(nombre.isBlank() || apellido.isBlank() || DNI.isBlank() || email.isBlank() || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            System.out.println("Por favor, ingrese correctamente los datos pedidos");
            return false;
        }
        Cliente cliente = clienteDAO.obtenerClientePorDNI(DNI);
        if (cliente != null){
            System.out.println("Ya existe un cliente con ese DNI");
            return false;
        }
        System.out.println(nombre + " " + apellido + " " + DNI + " " + email);
        clienteDAO.insertarCliente(nombre, apellido, DNI, email);
        System.out.println("Se ha registrado correctamente el Cliente");
        return true;
    }
    
    public ClienteDTO iniciarSesion(String DNI){
        if(DNI.isBlank()){
            System.out.println("Por favor, ingrese el dato pedido");
        }
        Cliente cliente = clienteDAO.obtenerClientePorDNI(DNI);
        
        if (cliente == null){
            System.out.println("No se ha encontrado ningun cliente con ese DNI");
            return null;
        }
        
        return new ClienteDTO(cliente.getIdCliente(), 
                cliente.getNombre(), 
                cliente.getApellido(), 
                cliente.getDNI());
    }
}
