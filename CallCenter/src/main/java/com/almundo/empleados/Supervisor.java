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
public class Supervisor extends Empleado {
    
    public Supervisor(String nombre) {
        super(nombre,2);
    }
    
      @Override
    public String toString(){
        return "Supervisor - Nombre: "+this.getNombre();
    }
}
