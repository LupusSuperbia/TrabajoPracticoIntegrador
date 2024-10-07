/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;
import DAO.ClienteModel;
import DAO.HotelModel;
import Model.Cliente;
import Model.Hotel;
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
        List<Hotel> prueba = new ArrayList<>();
        HotelModel modelHotel = new HotelModel();
        modelHotel.crearTabla();
        modelHotel.insertarHotel("Pasarela", 4);
        modelHotel.insertarHotel("Pasarela2423", 5);
        modelHotel.insertarHotel("Pasarelasdada", 3);
        modelHotel.insertarHotel("HolaBienvenidos", 3);
        prueba = modelHotel.obtenerHoteles();
        for (Hotel hotel : prueba) {
            System.out.println(hotel);
        }
        modelHotel.eliminarHotel("Pasarela");
        prueba = modelHotel.obtenerHotelesPorEstrella(3);
         for (Hotel hotel : prueba) {
            System.out.println(hotel);
        }
    }
}
