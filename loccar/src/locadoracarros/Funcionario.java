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
public class Funcionario extends Pessoa implements Serializable {
    
    //declaração de variáveis
    
    private String carteiraTrabalho;
    private String dataContratacao;
    private String login;
    private String senha;
    private char tipoFuncionario;
    private double salario;
    
    //declaração do construtor

    public Funcionario(String carteiraTrabalho, String dataContratacao, String login, String senha, char tipoFuncionario, double salario, String nome, String sobrenome, String endereço, String dataDeNascimento, String email, String telefone, String cpf) throws Exception {
        super(nome, sobrenome, endereço, dataDeNascimento, email, telefone, cpf);
        this.carteiraTrabalho = carteiraTrabalho;
        
        if(LocadoraCarros.validaData(dataContratacao)){
            this.dataContratacao = dataContratacao;
        }else{
            this.dataContratacao = null;
            throw new Exception("data da contrataçao invalida!");
        }
        
        this.login = login;
        
        if(LocadoraCarros.validaSenha(senha)){
            this.senha = Crypto.MD5(senha);
        }else{
            this.senha = null;
            throw new Exception("Senha Invalida!");
        }
        
        if(LocadoraCarros.validaTipoFuncionario(tipoFuncionario)){
            this.tipoFuncionario = tipoFuncionario;
        }else{
            this.tipoFuncionario = 'X';
            throw new Exception("Tipo Funcionario Invalido!");
        }
        
        if(LocadoraCarros.validaSalario(salario)){
            this.salario = salario;
        }else{
            this.salario = -1;
            throw new Exception("Salario Invalido!");
        }
    }
    
    //declaração de métodos

    public String getCarteiraTrabalho() {
        return carteiraTrabalho;
    }

    public void setCarteiraTrabalho(String carteiraTrabalho) {
        this.carteiraTrabalho = carteiraTrabalho;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(String dataContratacao) throws Exception {
        if(LocadoraCarros.validaData(dataContratacao)){
            this.dataContratacao = dataContratacao;
        }else{
            this.dataContratacao = null;
            throw new Exception("Data de Contrataçao Invalida!");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) throws Exception {
        if(LocadoraCarros.validaSenha(senha)){
            this.senha = senha;
        }else{
            this.senha = null;
            throw new Exception("Senha Invalida!");
        }
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) throws Exception {
        if(LocadoraCarros.validaSalario(salario)){
            this.salario = salario;
        }else{
            this.salario = -1;
            throw new Exception("Salario Invalido!");
        }
    }

    public char getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(char tipoFuncionario) throws Exception {
        if(LocadoraCarros.validaTipoFuncionario(tipoFuncionario)){
            this.tipoFuncionario = tipoFuncionario;
        }else{
            this.tipoFuncionario = 'X';
            throw new Exception("Tipo de Funcionario Invalido!");
        }
    }
    
    //método responsavel por validar se uma senha está correta
    public boolean verificaSenha(String senha){
        return this.senha.equals(senha);
    }
    
}
