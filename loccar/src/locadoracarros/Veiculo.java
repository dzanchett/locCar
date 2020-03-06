/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Veiculo implements Serializable {
    
    //declaração de variáveis
    
    private String placa;
    private String categoria;
    private String marca;
    private String modelo;
    private int ano;
    private String motor;
    private String cambio;
    private String acessorios;
    private double precoPorDia;
    private char status;
    private ArrayList<Reserva> reservas;
    private CalendarioReserva calendarioReserva;

    //declaração do construtor
    public Veiculo(String placa, String categoria, String marca, String modelo, int ano, String motor, String cambio, String acessorios, double precoPorDia) throws Exception {
        
        if(LocadoraCarros.validaPlaca(placa)){
            this.placa = placa;
        }else{
            this.placa = null;
            throw new Exception("Placa Invalida!");
        }
        
        this.categoria = categoria;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.motor = motor;
        this.cambio = cambio;
        this.acessorios = acessorios;
        
        if(LocadoraCarros.validaPrecoPorDia(precoPorDia)){
            this.precoPorDia = precoPorDia;
        }else{
            this.precoPorDia = -1;
            throw new Exception("Preço por Dia Invalido!");
        }
        
        
        status = 'A';
        reservas = new ArrayList<>();
        calendarioReserva = new CalendarioReserva();
    }
    
    //declaração de métodos

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) throws Exception {
        if(LocadoraCarros.validaPlaca(placa)){
            this.placa = placa;
        }else{
            this.placa = null;
            throw new Exception("Placa Invalida!");
        }
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public String getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(String acessorios) {
        this.acessorios = acessorios;
    }

    public double getPrecoPorDia() {
        return precoPorDia;
    }

    public void setPrecoPorDia(double precoPorDia) throws Exception {
        if(LocadoraCarros.validaPrecoPorDia(precoPorDia)){
            this.precoPorDia = precoPorDia;
        }else{
            this.precoPorDia = -1;
            throw new Exception("Preço por Dia Invalido!");
        }
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
    
    public ArrayList<Reserva> getReservas(){
        return reservas;
    }
    
    //método responsável por adicionar uma reserva ao calendarioReserva
    public void addReserva(Reserva r) throws ParseException{
        reservas.add(r);
        
        Data inicio, fim;
        
        inicio = new Data(r.getDataDeRetirada());
        fim = new Data(r.getDataDeEntrega());
        
        calendarioReserva.atualiza((int) inicio.getDataValor(), (int) fim.getDataValor());
    }
    
    //método responsável por remover uma reserva do calendarioReserva
    public void removeReserva(Reserva r) throws ParseException{
        reservas.remove(r);
        
        Data inicio, fim;
        
        inicio = new Data(r.getDataDeRetirada());
        fim = new Data(r.getDataDeEntrega());
        
        calendarioReserva.remove((int) inicio.getDataValor(), (int) fim.getDataValor());
    }
    
    //método responsável por consultar se determinado período está disponível no calendarioReserva
    public Boolean consultaDisponibilidade(String inicio, String fim) throws ParseException{
        Data dataInicio, dataFim;
        
        dataInicio = new Data(inicio);
        dataFim = new Data(fim);
        
        return calendarioReserva.verificaDisponibilidade((int) dataInicio.getDataValor(), (int) dataFim.getDataValor());
    }
    
}
