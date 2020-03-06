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
public class Reserva implements Serializable {
    
    //declaração de variáveis
    
    private String dataDeRetirada;
    private String dataDeEntrega;
    private double valorTotal;
    private Veiculo veiculo;
    private Cliente cliente;
    private Funcionario funcionario;
    private Boolean reservaFinalizada;

    //declaração do construtor
    public Reserva(String dataDeRetirada, String dataDeEntrega, double valorTotal, Veiculo veiculo, Cliente cliente, Funcionario funcionario) throws Exception {
        
        if(LocadoraCarros.validaData(dataDeRetirada)){
            this.dataDeRetirada = dataDeRetirada;
        }else{
            this.dataDeRetirada = null;
            throw new Exception("Data de Retirada Invalida!");
        }
        
        if(LocadoraCarros.validaData(dataDeEntrega)){
            this.dataDeEntrega = dataDeEntrega;
        }else{
            this.dataDeEntrega = null;
            throw new Exception("Data de Entrega Invalida!");
        }
        
        this.valorTotal = valorTotal;
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.funcionario = funcionario;
        
        reservaFinalizada = false;
    }
    
    //declaração de métodos

    public String getDataDeRetirada() {
        return dataDeRetirada;
    }

    public void setDataDeRetirada(String dataDeRetirada) throws Exception {
        if(LocadoraCarros.validaData(dataDeRetirada)){
            this.dataDeRetirada = dataDeRetirada;
        }else{
            this.dataDeRetirada = null;
            throw new Exception("Data de Retirada Invalida!");
        }
    }

    public String getDataDeEntrega() {
        return dataDeEntrega;
    }

    public void setDataDeEntrega(String dataDeEntrega) throws Exception {
        if(LocadoraCarros.validaData(dataDeEntrega)){
            this.dataDeEntrega = dataDeEntrega;
        }else{
            this.dataDeEntrega = null;
            throw new Exception("Data de Entrega Invalida!");
        }
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }  

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Boolean getReservaFinalizada() {
        return reservaFinalizada;
    }
    
    //método responsśvel por finalizar uma reserva, deve ser chamado após a devolução de um veículo
    public void finalizaReserva(){
        reservaFinalizada = true;
    }
    
}
