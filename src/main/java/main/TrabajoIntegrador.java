/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;
import DAO.ClienteModel;
import Model.Cliente;
import Util.ConnectionBD;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Juan Cruz
 */

//MODIFICAR VERSIÒN DE MAVEN EN /Proyect Files/pom.xml
//Usar comando mvn -v en la consola de comandos!

public class TrabajoIntegrador {

    public static void main(String[] args) {
        ClienteModel model = new ClienteModel();
        List<Cliente> prueba = new ArrayList<>();
        model.crearTabla();
        prueba = model.obtenerClientes();
        for (Cliente cliente : prueba) {
            System.out.println(cliente);
        }
        model.insertarCliente("Prueba", "prueba232", "123123");
        Cliente pruebaCliente = model.obtenerClientePorDNI("123123");
        System.out.println(pruebaCliente);
        pruebaCliente = model.actualizarCliente("123123", "Profe34", "imposible21", "123123");
        System.out.println(pruebaCliente);
        Cliente pruebaCliente2 = model.obtenerClientePorDNI("123123");
        System.out.println(pruebaCliente2);
        model.eliminarCuenta("123123");
         prueba = model.obtenerClientes();
        for (Cliente cliente : prueba) {
            System.out.println(cliente);
        }
    }
}
