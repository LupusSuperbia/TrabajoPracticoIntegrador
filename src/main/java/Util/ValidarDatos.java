/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Exceptions.ServiceExceptions;

/**
 *
 * @author asamsu
 */
public class ValidarDatos {
    
    public void validarVariosDatosEnteros(String mensaje, int... valor) throws ServiceExceptions{
        for (int i : valor) {
            validarDatosEnteros(i, mensaje);
        }
    }
    public void validarVariosDatosString(String mensaje, String... valor) throws ServiceExceptions{
        for (String string : valor) {
            validarDatosString(string, mensaje);
        }
    }
    
    public void validarDatosEnteros(int valor, String mensaje) throws ServiceExceptions {
        if (valor < 0) {
            throw new ServiceExceptions(mensaje);
        }
    }
    
    public void validarDatosString(String valor, String mensaje) throws ServiceExceptions {
        if (valor.isBlank()) {
            throw new ServiceExceptions(mensaje);
        }
    }
    
}
