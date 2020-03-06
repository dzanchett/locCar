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
public class Pessoa implements Serializable {
    
    //declaração de variáveis
    
    private String nome;
    private String sobrenome;
    private String endereco;
    private String dataDeNascimento;
    private String email;
    private String telefone;
    private String cpf;

    //declaração do construtor
    public Pessoa(String nome, String sobrenome, String endereço, String dataDeNascimento, String email, String telefone, String cpf) throws Exception {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereço;
        
        if(LocadoraCarros.validaData(dataDeNascimento)){
            this.dataDeNascimento = dataDeNascimento;
        }else{
            this.dataDeNascimento = null;
            throw new Exception("Data de Nascimento Invalida!");
        }
        
        if(LocadoraCarros.validaEmail(email)){
            this.email = email;
        }else{
            this.email = null;
            throw new Exception("E-mail Invalido!");
        }
        
        this.telefone = telefone;
        
        if(LocadoraCarros.validaCpf(cpf)){
            this.cpf = cpf;
        }else{
            this.cpf = null;
            throw new Exception("C.P.F. Invalido!");
        }
    }
    
    //declaração de métodos
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) throws Exception {
        if(LocadoraCarros.validaData(dataDeNascimento)){
            this.dataDeNascimento = dataDeNascimento;
        }else{
            this.dataDeNascimento = null;
            throw new Exception("Data de Nascimento Invalida!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(LocadoraCarros.validaEmail(email)){
            this.email = email;
        }else{
            this.email = null;
            throw new Exception("E-mail Invalido!");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws Exception {
        if(LocadoraCarros.validaCpf(cpf)){
            this.cpf = cpf;
        }else{
            this.cpf = null;
            throw new Exception("C.P.F. Invalido!");
        }
    }
    
}
