/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.callcenter;

import com.almundo.empleados.Empleado;
import java.util.ArrayList;

/**
 *
 * @author Luis Andres Gomez
 */
public class Dispatcher extends Thread {
    
    /**
     * Todos los empleados 
     */
    private ArrayList<Empleado> empleados;
    
    /**
     * Llamadas en espera
     */
    private ArrayList<Llamada> llamadasEspera;
    
    /**
     * Se esta ejecutando el proceso de callCenter
     */
    private boolean ejecutando; 
        
    
    /**
     * Tiempo de espera
     */
    private final int tiempoEspera=1000; 
    
    
    private int numLlamadasAtendidas;
    
    /**
     * Constructor de dispatcher
     */
    public Dispatcher(){
        this.empleados=new ArrayList<Empleado>();
        this.llamadasEspera=new ArrayList<Llamada>();
        numLlamadasAtendidas=0;
    }
    
    /**
     * Iniciar ejecucion de hilo de llamadas en espera
     */
    private void iniciarEjecucionLLamadasEspera(){
        this.ejecutando=true;
        Thread worker = new Thread(this);
        worker.start();
    }
    
    /**
     * Retomar si se esta ejecutando
     * @return ejecutando
     */
    public boolean getEjecutando(){
        boolean onCall=false;
                
        //Verificar si estan en llamada los empleados tambien
        for(Empleado emp:empleados){
            if(emp.getOnCall()){
                onCall=true;
                break;
            }
        }
        
        return ejecutando || onCall;
    }
   
    /**
     * Total de llamadas atendidas por todos los agentes
     * @return 
     */
    public int getTotalLLamadasAtendidasAgentes(){
        int totalLlamadas=0;
        
        for(Empleado emp:empleados){
            totalLlamadas+=emp.getLTotalLlamadasAtentidas();
        }
        
        return totalLlamadas;
    }
    
    /**
     * Total de llamadas atentidas por call center
     * @return 
     */
    public int getTotalLLamadasAtendidasCallCenter(){
        return numLlamadasAtendidas;
    }
    
    /**
     * Metodo para terminar ejecucion de dispatcher
     */
    public void terminarEjecucion(){
        this.ejecutando=false;
    }
    
    /**
     * Metodo para agregar empleados al dispatcher
     * @param empleado
     * @return 
     */
    public boolean addEmpleado(Empleado empleado){
        return empleados.add(empleado);
    }
    
    /**
     * Metodo que se encarga de recibir la llamada y atenderla
     * @param llamada 
     */
    public synchronized void dispatchCall(Llamada llamada){
          Empleado empleadoDisponible=null;
          empleadoDisponible=getEmpleadoDisponible();
          numLlamadasAtendidas+=1;
          if(empleadoDisponible!=null){
              System.out.println("Empleado " + empleadoDisponible.toString() + " atendera llamada N "+llamada.getNumeroLlamada() + " duracion de la llamada " + llamada.getDuracionLlamada() );
              empleadoDisponible.recibirLlamada(llamada);
          }else{
              esperar(llamada);
          }
     }
    
    private synchronized void esperar(Llamada llamada){
        System.out.println("Esperando mientras los agentes se desocupan llamada N" + llamada.getNumeroLlamada());
        llamadasEspera.add(llamada);
        if(!ejecutando){
            iniciarEjecucionLLamadasEspera();
        }
    }
    
    /**
     * Retoma el empleado disponible para antender la llamada de acuerdo a la prioridad
     * @return Empleado disponible
     */
    private Empleado getEmpleadoDisponible(){
        Empleado empleado=null;
        ArrayList<Empleado> empleadosDisponibles=getTodosEmpleadosDisponibles();
        
        //Si hay empleados disponibles se prioriza para retomarlo
        if(empleadosDisponibles.size()>0){
            for(Empleado empleadoDisponible:empleadosDisponibles){
                //Si no hay empleado se retoma el primero de todos los que estan disponibles
                if(empleado==null){
                    empleado=empleadoDisponible;
                }else{
                    //De acuerdo a su prioridad se retoma el que tenga menos prioridad
                    if(empleadoDisponible.getPrioridadLlamada()<empleado.getPrioridadLlamada()){
                        empleado=empleadoDisponible;
                    }
                }
            }
        }
        
        return empleado;
    }
    
    /**
     * Funcion que retoma todos los empleados displonibles
     * @return 
     */
    private ArrayList<Empleado> getTodosEmpleadosDisponibles(){
        ArrayList<Empleado> empleadosDisponibles=new ArrayList<Empleado>();
        for(Empleado empleado:empleados){
            if(!empleado.getOnCall()){
                empleadosDisponibles.add(empleado);
            }
        }
        return empleadosDisponibles;
    }
    
    /**
     * Thread para validar si hay llamadas en espera para depurarlas
     */
    @Override
    public void run() {
        while(ejecutando){
            int indexLlamada=0;
            Llamada llamadaEspera=null;
            while(indexLlamada<llamadasEspera.size() && getEmpleadoDisponible()!=null){
                llamadaEspera=llamadasEspera.get(indexLlamada);
                System.out.println("Atendiendo llamada en espera numero" + llamadaEspera.getNumeroLlamada());
                llamadasEspera.remove(llamadaEspera);
                numLlamadasAtendidas-=1;
                dispatchCall(llamadaEspera);
                indexLlamada+=1;
            }
                        
            try {
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            
            //Si ya no hay llamadas en espera pare de ejecutar el thread
            if(llamadasEspera.size()==0){
                ejecutando=false;
            }
            //Si no hay empleados termina la ejecucion
            if(empleados.size()==0){
                ejecutando=false;
                System.out.println("No hay empleados disponibles para recibir la llamada intente en otro horario");
            }
            
        }
    }
}
