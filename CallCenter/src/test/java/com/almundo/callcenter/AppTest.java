package com.almundo.callcenter;

import com.almundo.empleados.Director;
import com.almundo.empleados.Empleado;
import com.almundo.empleados.Operador;
import com.almundo.empleados.Supervisor;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private final int tiempoEspera=1000;
    
    /**
     * Test de diez llamadas al call center con 10 agentes
     */
    @Test
    public void DiezLlamadasACallCenter()
    {
        Dispatcher dispatcher = new Dispatcher();
        asociarEmpleados(dispatcher,10);
        hacerLlamadas(dispatcher,10);
        esperar(dispatcher);
        assertEquals(dispatcher.getTotalLLamadasAtendidasCallCenter(),dispatcher.getTotalLLamadasAtendidasAgentes());
    }
    
    /**
     * Test de Treinta llamadas al call center con 10 agentes
     */
    @Test
    public void TreintaLlamadasACallCenter()
    {
        Dispatcher dispatcher = new Dispatcher();
        asociarEmpleados(dispatcher,10);
        hacerLlamadas(dispatcher,30);
        esperar(dispatcher);
        assertEquals(dispatcher.getTotalLLamadasAtendidasCallCenter(),dispatcher.getTotalLLamadasAtendidasAgentes());
    }
    
    /**
     * Test de doble de llamadas por agentes
     */
    @Test
    public void DobleLlamadasACallCenter()
    {
        Dispatcher dispatcher = new Dispatcher();
        asociarEmpleados(dispatcher,5);
        hacerLlamadas(dispatcher,10);
        esperar(dispatcher);
        assertEquals(dispatcher.getTotalLLamadasAtendidasCallCenter(),dispatcher.getTotalLLamadasAtendidasAgentes());
    }
    
    
    /**
     * Test de llamadas sin agentes en el call center
     */
    @Test
    public void LlamadasSinAgentesACallCenter()
    {
        Dispatcher dispatcher = new Dispatcher();
        hacerLlamadas(dispatcher,1);
        esperar(dispatcher);
        assertEquals(0,dispatcher.getTotalLLamadasAtendidasAgentes());
    }
    
    /**
     * Esperar hasta que termine de contestar todas las llamadas
     * @param dispatcher 
     */
    private void esperar(Dispatcher dispatcher){
        while(dispatcher.getEjecutando()){
            try {
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
       
    /***
     * Hacer llamadas al callcenter
     * @param dispatcher
     * @param cantLlamadas 
     */
    private void hacerLlamadas(Dispatcher dispatcher,int cantLlamadas){
        int i=0;
         while(i<cantLlamadas){
            dispatcher.dispatchCall(new Llamada(numeroRandom(5,10)*1000,i));
            i+=1;
        }
    }
    
    /**
     * Asociar empleados a dispatcher
     * @param dispatcher
     * @param numEmpleados 
     */
    private void asociarEmpleados(Dispatcher dispatcher,int numEmpleados){
                   
        Empleado emp=null;
        int i=0;
        int prioridad;
        
        while(i<numEmpleados){
            prioridad=numeroRandom(1,3);
            switch(prioridad) {
                case 1 :
                    emp=new Operador("Empleado " + i);
                    break; // optional
                case 2 :
                    emp=new Supervisor("Empleado " + i);
                    break; // optional
                case 3 :
                    emp=new Director("Empleado " + i);
                    break; // optional
            }
            dispatcher.addEmpleado(emp);
            i+=1;
        }
        
    }
    
    
    private static int numeroRandom(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
}
