/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Cliente extends Pessoa implements Serializable {
    
    //Declaração de variáveis:
    
    private String cnh;
    private ArrayList<Reserva> reservas;

    //declaração do construtor
    public Cliente(String cnh, String nome, String sobrenome, String endereço, String dataDeNascimento, String email, String telefone, String cpf) throws Exception {
        super(nome, sobrenome, endereço, dataDeNascimento, email, telefone, cpf);
        this.cnh = cnh;
        
        reservas = new ArrayList<>();
    }

    //declaração de métodos:
    
    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }
    
    public ArrayList<Reserva> getReservas(){
        return reservas;
    }
    
    //método responsável por adicionar uma reserva
    public void addReserva(Reserva r){
        reservas.add(r);
    }
    
    //método responsável por remover uma reserva
    public void removeReserva(Reserva r){
        reservas.remove(r);
    }
    
}
