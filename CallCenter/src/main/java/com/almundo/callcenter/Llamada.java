/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.callcenter;

/**
 *
 * @author games
 */
public class Llamada {
    
    /**
     * Duracion de la llamada en MS
     */
    private int duracionLlamada;
    
    /**
     * Identificador de la llamada
     */
    private int numeroLlamada;
 
    /**
     * Constructor de llamada
     * @param duracionLlamada 
     * @param numeroLlamada
     */
    public Llamada(int duracionLlamada,int numeroLlamada){
        this.duracionLlamada=duracionLlamada;
        this.numeroLlamada = numeroLlamada;
    }
    
    /**
     * Funcion para obtener la duracion de la llamada
    */
    public int getDuracionLlamada(){
        return duracionLlamada;
    }
    
      
    /**
     * Funcion para obtener el numero de la llamada
    */
    public int getNumeroLlamada(){
        return numeroLlamada;
    }
}
