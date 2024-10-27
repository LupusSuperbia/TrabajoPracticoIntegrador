/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;


import DAO.PersonaDAO;
import DTO.ClienteDTO;
import Model.Cliente;
import Util.Rol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class ServiceCliente {
    private final PersonaDAO clienteDAO ;
    
    public ServiceCliente(){
        this.clienteDAO = new PersonaDAO();
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
        clienteDAO.insertarCliente(nombre, apellido, DNI, email, Rol.USER);
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
    
    
    public ClienteDTO buscarClienteEmail(String email){
        if(email.isBlank()){
            System.out.println("Por favor, ingrese el dato pedido");
        }
        Cliente cliente = clienteDAO.obtenerClientePorEmail(email);
        
        if (cliente == null){
            System.out.println("No se ha encontrado ningun cliente con ese email");
            return null;
        }
        
        return new ClienteDTO(cliente.getIdCliente(), 
                cliente.getNombre(), 
                cliente.getApellido(), 
                cliente.getDNI());
    }
    
    public ClienteDTO buscarClienteDNI(String DNI){
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
    
    public List<ClienteDTO> obtenerClientes(){
        List<Cliente> clientes = clienteDAO.obtenerClientes();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        // TODO : MODULAR
        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = new ClienteDTO(cliente.getIdCliente(), 
                    cliente.getNombre(),
                    cliente.getApellido(), cliente.getDNI());
            clientesDTO.add(clienteDTO);
        }
        return clientesDTO; 
    }
    
     public ClienteDTO actualizarCliente(String DNI, String nombreActualizar, String apellidoActualizar, String DNIActualizar, String emailActualizar){
        if(DNI.isBlank() || nombreActualizar.isBlank() || apellidoActualizar.isBlank() || DNIActualizar.isBlank() || emailActualizar.isBlank()){
            throw new IllegalArgumentException("Por favor ingrese los datos pedidos");
        }
        Cliente cliente = clienteDAO.actualizarCliente(DNI, nombreActualizar, apellidoActualizar, DNIActualizar, emailActualizar);
        
        if (cliente == null){
            System.out.println("No se ha encontrado ningun cliente con ese DNI");
            return null;
        }
        
        return new ClienteDTO(cliente.getIdCliente(), 
                cliente.getNombre(), 
                cliente.getApellido(), 
                cliente.getDNI());
    }
     
    public void eliminarCliente(String DNI){
        if(DNI.isBlank()){
            throw new IllegalArgumentException("Por favor ingrese los datos pedidos");
        }
        clienteDAO.eliminarCuentaCliente(DNI);
        System.out.println("Se ha eliminado el cliente correctamente");
    }
    
    
    
    
}
