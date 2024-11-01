/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PersonaDAO;
import DTO.ClienteDTO;
import Exceptions.ServiceExceptions;
import Model.Cliente;
import Util.Rol;
import Util.ValidarDatos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class ServiceCliente extends ServiceBase{

    private final PersonaDAO clienteDAO;
    private final ValidarDatos validador;

    public ServiceCliente() {
        this.clienteDAO = new PersonaDAO();
        this.validador = new ValidarDatos();
    }

    public void crearTabla() {
        clienteDAO.crearTabla();
    }

    public boolean registrarCliente(String nombre, String apellido, String DNI, String email) throws ServiceExceptions {
        validador.validarVariosDatosString("Por favor, ingrese correctamente los datos pedidos",
                nombre,
                apellido,
                DNI,
                email);
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ServiceExceptions("No ha ingresado un mail valido");
        }
        Cliente cliente = clienteDAO.obtenerClientePorDNI(DNI);
        if (cliente != null) {
            throw new ServiceExceptions("Ya existe un cliente con ese DNI");
        }
        clienteDAO.insertarCliente(nombre, apellido, DNI, email, Rol.USER);
        logger.info("Se ha registrado correctamente el Cliente");
        return true;
    }

    public ClienteDTO iniciarSesion(String DNI) throws ServiceExceptions {
        validador.validarDatosString(DNI, "Porfavor ingrese un DNI valido");
        Cliente cliente = clienteDAO.obtenerClientePorDNI(DNI);

        return retornarClienteDTO(cliente);
    }

    public ClienteDTO buscarClienteEmail(String email) throws Exception {
        validador.validarDatosString(email, "Porfavor ingrese un DNI valido");
        Cliente cliente = clienteDAO.obtenerClientePorEmail(email);
        return retornarClienteDTO(cliente);
    }

    public ClienteDTO buscarClienteDNI(String DNI) throws Exception {
        validador.validarDatosString(DNI, "Porfavor ingrese un DNI valido");
        Cliente cliente = clienteDAO.obtenerClientePorDNI(DNI);
        return retornarClienteDTO(cliente);
    }

    public List<ClienteDTO> obtenerClientes() {
        List<Cliente> clientes = clienteDAO.obtenerClientes();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        // TODO : MODULAR
        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = new ClienteDTO(cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getApellido(), cliente.getDNI(),cliente.getEmail(), Rol.USER);
            clientesDTO.add(clienteDTO);
        }
        return clientesDTO;
    }

    public ClienteDTO actualizarCliente(String DNI, String nombreActualizar, String apellidoActualizar, String DNIActualizar, String emailActualizar) throws Exception {
        validador.validarVariosDatosString("Por favor, ingrese correctamente los datos pedidos",
                DNI,
                nombreActualizar,
                apellidoActualizar,
                DNIActualizar,
                emailActualizar);
        Cliente cliente = clienteDAO.actualizarCliente(DNI, nombreActualizar, apellidoActualizar, DNIActualizar, emailActualizar);

        return retornarClienteDTO(cliente);
    }

    public void eliminarCliente(String DNI) throws ServiceExceptions {
        validador.validarDatosString(DNI, "Porfavor ingrese un DNI valido");
        clienteDAO.eliminarCuentaCliente(DNI);
        logger.info("Se ha eliminado el cliente correctamente");
    }

    // Utils 
    private ClienteDTO retornarClienteDTO(Cliente cliente) throws ServiceExceptions {
        if (cliente == null) {
            throw new ServiceExceptions("No se ha encontrado al cliente");
        }

        return new ClienteDTO(cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDNI(), 
               cliente.getEmail(),
                cliente.getRol());
    }
    
    public String obtenerRol(String DNI) throws ServiceExceptions{
        String Rol = clienteDAO.obtenerRolPorDNI(DNI);
        validador.validarDatosString(Rol, "No se encontro persona para este DNI");
        return Rol;
    }

}
