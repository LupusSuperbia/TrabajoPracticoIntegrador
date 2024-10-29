/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppLogic;

import java.util.Scanner;

/**
 *
 * @author Juan Cruz
 */
public class menuAdmin {

    Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    boolean flag = true;
    int option = 0;

    public void menu() {

        while (flag) {
            
            System.out.println("Ingrese la opción deseada: ");
            
            System.out.println("1 Agregar Hotel.");
            System.out.println("2 Eliminar Hotel.");
            System.out.println("3 Modificar Hotel.");
            System.out.println("4 Cambiar estado de reservas de clientes.");
            System.out.println("5 Eliminar Cliente.");
            System.out.println("6 Crear Usuario Administrador.");
            System.out.println("7 Eliminar Usuario Administrador. ");

            option = scanner.nextInt();
            
            switch (option){
                
                case 1: 
                    
                    
                
            }
            
        }

    }
}
