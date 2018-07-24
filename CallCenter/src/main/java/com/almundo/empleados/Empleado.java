/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.empleados;

import com.almundo.callcenter.Llamada;

/**
 *
 * @author Luis Andres Gomez
 */
public abstract class Empleado extends Thread {
    private String nombre;
    private boolean onCall;
    private Llamada llamadaActual;
    private int prioridadLlamada;
    private int totalLlamadasAtendidas;
    
    
    /**
     * Constructor de empleado
     * @param nombre 
     * @param prioridadLlamada 
     */
    public Empleado(String nombre , int prioridadLlamada){
        this.nombre=nombre;
        this.onCall=false;
        this.prioridadLlamada=prioridadLlamada;
        this.totalLlamadasAtendidas=0;
    }
    
    /**
     * retomar el total de llamadas atendidas
     * @return 
     */
    public int getLTotalLlamadasAtentidas(){
        return totalLlamadasAtendidas;
    }
    
    /**
     * Retoma el rango de prioridad de llamada
     * @return 
     */
    public int getPrioridadLlamada(){
        return prioridadLlamada;
    }
    
    /**
     * 
     * @return OnCall
     */
    public boolean getOnCall(){
        return onCall;
    }
    
    /**
     * Funcion que devuelve el nombre del empleado
     * @return Nombre del empleado
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Metodo para que el empleado pueda recibir la llamada
     * @param llamada
     * @return 
     */
    public boolean recibirLlamada(Llamada llamada){
        boolean recibioLlamada=false;
        
        //Recibe la llamada siempre y cuando no se encuentre en una llamada
        if(!this.onCall){
             //El empleado queda en llamada
            this.onCall=true;
            Thread worker = new Thread(this);
            recibioLlamada=true;
            this.llamadaActual=llamada;
            //Metodo que atiende la llamada del cliente por un tiempo determinado
            worker.start();
        }
        return recibioLlamada;
    }
    
    
    @Override
    public void run() {
        int duracionActual=0;
        int intervalo=1000;
        
        //Espera mientras se atiende la llamada de acuerdo a la duracion
        while (duracionActual<this.llamadaActual.getDuracionLlamada()){
            try {
                Thread.sleep(intervalo);
                duracionActual+= intervalo;      
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Al terminar la llamada se pone como disponible
        totalLlamadasAtendidas+=1;
        System.out.println("Empleado " + this.toString() + " termino de atender llamada N "+llamadaActual.getNumeroLlamada() );
        //Cambiar estado de agente
        this.llamadaActual=null;
        this.onCall=false;
    }
}
