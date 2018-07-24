/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.empleados;

/**
 *
 * @author Luis Andres Gomez
 */
public class Operador extends Empleado {
 
    public Operador(String nombre) {
        super(nombre,1);
    }
    
    
    @Override
    public String toString(){
        return "Operador - Nombre: "+this.getNombre();
    }
    
}
