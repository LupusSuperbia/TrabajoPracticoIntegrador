/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PersonaDAO;
import DTO.AdminDTO;
import Exceptions.ServiceExceptions;
import Model.Admin;
import Util.Rol;
import Util.ValidarDatos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asamsu
 */
public class ServiceAdmin extends ServiceBase{

    private final PersonaDAO adminDAO;
    private final ValidarDatos validador;
    
    public ServiceAdmin() {
        this.adminDAO = new PersonaDAO();
        this.validador = new ValidarDatos();
    }
    
     public boolean registrarAdmin(String nombre, String apellido, String DNI, String email) throws ServiceExceptions {
        validador.validarVariosDatosString("Por favor, ingrese correctamente los datos pedidos",
                nombre,
                apellido,
                DNI,
                email);
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ServiceExceptions("No ha ingresado un mail valido");
        }
        Admin admin = adminDAO.obtenerAdminPorDNI(DNI);
        if (admin != null) {
            throw new ServiceExceptions("Ya existe un cliente con ese DNI");
        }
        adminDAO.insertarCliente(nombre, apellido, DNI, email, Rol.ADMIN);
        logger.info("Se ha registrado correctamente el Cliente");
        return true;
    }

    public AdminDTO iniciarSesion(String DNI) throws ServiceExceptions {
        validador.validarDatosString(DNI, "Porfavor ingrese un DNI valido");
        Admin admin = adminDAO.obtenerAdminPorDNI(DNI);

        return retornarAdminDTO(admin);
    }

    public AdminDTO buscarAdminEmail(String email) throws Exception {
        validador.validarDatosString(email, "Porfavor ingrese un DNI valido");
        Admin admin  = adminDAO.obtenerAdminPorEmail(email);
        return retornarAdminDTO(admin);
    }

    public AdminDTO buscarAdminDNI(String DNI) throws Exception {
        validador.validarDatosString(DNI, "Porfavor ingrese un DNI valido");
        Admin admin = adminDAO.obtenerAdminPorDNI(DNI);
        return retornarAdminDTO(admin);
    }

    public List<AdminDTO> obtenerAdmins() {
        List<Admin> admins = adminDAO.obtenerAdmins();
        List<AdminDTO> adminDTO = new ArrayList<>();
        // TODO : MODULAR
        for (Admin admin : admins) {
            AdminDTO clienteDTO = new AdminDTO(admin.getIdAdmin(),
                    admin.getNombre(),
                    admin.getApellido(), admin.getDNI(), admin.getEmail());
            adminDTO.add(clienteDTO);
        }
        return adminDTO;
    }

    public AdminDTO actualizarAdmin(String DNI, String nombreActualizar, String apellidoActualizar, String DNIActualizar, String emailActualizar) throws Exception {
        validador.validarVariosDatosString("Por favor, ingrese correctamente los datos pedidos",
                DNI,
                nombreActualizar,
                apellidoActualizar,
                DNIActualizar,
                emailActualizar);
        Admin admin = adminDAO.actualizarAdmin(DNI, nombreActualizar, apellidoActualizar, DNIActualizar, emailActualizar);

        return retornarAdminDTO(admin);
    }

    public void eliminarAdmin(String DNI) throws ServiceExceptions {
        validador.validarDatosString(DNI, "Porfavor ingrese un DNI valido");
        adminDAO.eliminarCuentaAdmin(DNI);
        logger.info("Se ha eliminado el cliente correctamente");
    }

    // Utils 
    private AdminDTO retornarAdminDTO(Admin admin) throws ServiceExceptions {
        if (admin == null) {
            throw new ServiceExceptions("No se ha encontrado al cliente");
        }

        return new AdminDTO(admin.getIdAdmin(),
                admin.getNombre(),
                admin.getApellido(),
                admin.getDNI(), admin.getEmail());
    }
}
