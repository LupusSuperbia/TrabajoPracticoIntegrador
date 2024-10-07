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
        prueba = model.obtenerClientes();
        for (Cliente cliente : prueba) {
            System.out.println(cliente);
        }
    }
}
