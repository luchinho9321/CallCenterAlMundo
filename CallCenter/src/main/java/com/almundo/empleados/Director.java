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
public class Director extends Empleado {
    
       
    public Director(String nombre) {
        super(nombre,3);
    }
    
    @Override
    public String toString(){
        return "Director - Nombre: "+this.getNombre();
    }
    
}
