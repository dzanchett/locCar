/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author diego
 */
public class Agencia implements Serializable {
    
    //Declaração de variáveis:
    
    private String nome;
    private String codigo;
    private String endereco;
    private String telefone;
    private String CNPJ;
    private HashMap<String, Funcionario> funcionarios = new HashMap<>();
    private HashMap<String, Cliente> clientes = new HashMap<>();
    private HashMap<String, Veiculo> veiculos = new HashMap<>();
    
    //Declaração dos construtores:
    
    public Agencia() throws Exception{
        Funcionario temp = new Funcionario("abc123", "01/01/2019", "gerente", "abc123", 'G', 800.00, "Gerente Inicial", "Gerente Inicial", "Gerente Inicial", "01/01/2019", "gerente.inicial@empresa.com", "(00) 00000-0000", "428.738.830-57");
        funcionarios.put(temp.getLogin(), temp);
    }

    public Agencia(String nome, String codigo, String endereco, String telefone, String CNPJ) throws Exception {
        this.nome = nome;
        this.codigo = codigo;
        this.endereco = endereco;
        this.telefone = telefone;
        
        if(LocadoraCarros.validaCnpj(CNPJ)){
            this.CNPJ = CNPJ;
        }else{
            this.CNPJ = null;
            throw new Exception("CNPJ invalido!");
        }
        
        Funcionario temp = new Funcionario("abc123", "01/01/2019", "gerente", "abc123", 'G', 800.00, "Gerente Inicial", "Gerente Inicial", "Gerente Inicial", "01/01/2019", "gerente.inicial@empresa.com", "(00) 00000-0000", "989.464.780-47");
        funcionarios.put(temp.getLogin(), temp);
    }

    //declaração de métodos:
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) throws Exception {
        if(LocadoraCarros.validaCnpj(CNPJ)){
            this.CNPJ = CNPJ;
        }else{
            throw new Exception("Cnpj Invalido!");
        }
    }

    public HashMap<String, Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(HashMap<String, Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(HashMap<String, Cliente> clientes) {
        this.clientes = clientes;
    }

    public HashMap<String, Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(HashMap<String, Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
    
    public Cliente getCliente(String cpf){
        return clientes.get(cpf);
    }
    
    public Veiculo getVeiculo(String placa){
        return veiculos.get(placa);
    }
    
    public Funcionario getFuncionario(String login){
        return funcionarios.get(login);
    }
    
    public void addFuncionario(Funcionario f){
        funcionarios.put(f.getLogin(), f);
    }
    
    public int removerFuncionario(String s){
        Funcionario f;
        
        for(Map.Entry<String, Funcionario> it : funcionarios.entrySet()){
            if(it.getValue().getCpf().equals(s)){
                System.out.println("***");
                s = it.getValue().getLogin();
                break;
            }
        }
        
        f = funcionarios.remove(s);
        
        if(f == null){
            return 1;
        }else{
            return 0;
        }
    }
    
    public void addCliente(Cliente c){  
        clientes.put(c.getCpf(), c);
    }
    
    public int removerCliente(String s) throws ParseException{
        Cliente c;
        
        c = clientes.remove(s);
        
        if(c == null){
            return 1;
        }else{
            for(Reserva r : c.getReservas()){
                r.getVeiculo().removeReserva(r);
            }
            
            return 0;
        }
    }
    
    public void addVeiculo(Veiculo v){
        veiculos.put(v.getPlaca(), v);
    }
    
    public int removerVeiculo(String s){
        Veiculo v;
        
        v = veiculos.remove(s);
        
        if(v == null){
            return 1;
        }else{
            for(Reserva r : v.getReservas()){
                r.getCliente().removeReserva(r);
            }
            
            return 0;
        }
    }
    
    public Boolean validaLogin(String login, String senha){
        senha = Crypto.MD5(senha);
        
        if(funcionarios.containsKey(login) && funcionarios.get(login).verificaSenha(senha)){
            return true;
        }else{
            return false;
        }
    }
    
    public char getTipoFuncionario(String login){
        if(funcionarios.containsKey(login)){
            return funcionarios.get(login).getTipoFuncionario();
        }else{
            return 0;
        }
    }
    
    public String getCpfFuncionario(String login){
        if(funcionarios.containsKey(login)){
            return funcionarios.get(login).getCpf();
        }else{
            return null;
        }
    }
    
    public double realizaReserva(String dataDeRetirada, String dataDeEntrega, Veiculo veiculo, Cliente cliente, Funcionario funcionario) throws ParseException, Exception{
        
        Data inicio = new Data(dataDeRetirada);
        Data fim = new Data(dataDeEntrega);
        
        long duracaoReserva = fim.getDataValor() - inicio.getDataValor() + 1;
        
        double valorTotal = duracaoReserva * veiculo.getPrecoPorDia();
        
        Reserva r = new Reserva(dataDeRetirada, dataDeEntrega, valorTotal, veiculo, cliente, funcionario);
        
        veiculo.addReserva(r);
        cliente.addReserva(r);
        
        return valorTotal;
    }
    
    public void retiraVeiculo(String placa){
        veiculos.get(placa).setStatus('C');
    }
    
    public void devolveVeiculo(String placa){
        veiculos.get(placa).setStatus('A');
    }
    
}
