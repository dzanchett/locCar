/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.io.Serializable;

/**
 *
 * @author diego
 */
public class CalendarioReserva implements Serializable {
    
    //Declaração de constante:
    private static final int MAXN = 100000;
    
    //Declaração de variável:
    Boolean[] calendario;
    
    //Construtor:
    public CalendarioReserva(){
        calendario = new Boolean[MAXN];
        
        for(int i = 0; i<MAXN; i++){
            calendario[i] = false;
        }
    }
    
    //Método responsável por atualizar o calendário, marcando algum periodo como ocupado no calendario
    public void atualiza(int inicio, int fim){
        
        for(int i = inicio; i <= fim; i++){
            calendario[i] = true;
        }
        
    }
    //Método responsável por atualizar o calendário, marcando algum periodo como disponivel no calendario
    public void remove(int inicio, int fim){
        
        for(int i = inicio; i <= fim; i++){
            calendario[i] = false;
        }
        
    }
    
    //Método responsável por verifica se determinado periodo está livre no calendário
    public Boolean verificaDisponibilidade(int inicio, int fim){
        Boolean ret = true;
        
        for(int i = inicio; i <= fim; i++){
            if(calendario[i] == true){
                ret = false;
                break;
            }
        }
        
        return ret;
    }
    
}
