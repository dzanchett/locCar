/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author diego
 */
public class Buscador{
    
    //Declaração de métodos estáticos:
    //(Está classe é composta somente por métodos estáticos)
    
    public static ArrayList<Agencia> buscaAgencia(int codigoAtributo, String valorAtributo, HashMap<String, Agencia> agencias){
        ArrayList<Agencia> ret = new ArrayList<>();
        
        for(Map.Entry<String, Agencia> it : agencias.entrySet()){
            Boolean flag = false;
            
            switch(codigoAtributo){
                case 1:
                    flag = it.getValue().getNome().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 2:
                    flag = it.getValue().getCodigo().equals(valorAtributo);
                    break;
                case 3:
                    flag = it.getValue().getEndereco().toLowerCase().contains(valorAtributo.toLowerCase());
                    break;
                case 4:
                    flag = it.getValue().getTelefone().equals(valorAtributo);
                    break;
                case 5:
                    flag = it.getValue().getCNPJ().equals(valorAtributo);
                    break;
            }
            
            if(flag){
                ret.add(it.getValue());
            }
        }
        
        return ret;
    }
    
    public static ArrayList<Veiculo> buscaVeiculo(int codigoAtributo, String valorAtributo, HashMap<String, Veiculo> veiculos){
        ArrayList<Veiculo> ret = new ArrayList<>();
        
        for(Map.Entry<String, Veiculo> it : veiculos.entrySet()){
            Boolean flag = false;
            
            switch(codigoAtributo){
                case 1:
                    flag = it.getValue().getPlaca().toUpperCase().equals(valorAtributo.toUpperCase());
                    break;
                case 2:
                    flag = it.getValue().getCategoria().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 3:
                    flag = it.getValue().getMarca().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 4:
                    flag = it.getValue().getModelo().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 5:
                    int tempInt = Integer.valueOf(valorAtributo).intValue();
                    flag = it.getValue().getAno() == tempInt;
                    break;
                case 6:
                    flag = it.getValue().getMotor().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 7:
                    flag = it.getValue().getCambio().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 8:
                    flag = it.getValue().getAcessorios().toLowerCase().contains(valorAtributo.toLowerCase());
                    break;
                case 9:
                    double tempDouble = Double.valueOf(valorAtributo.replace(',', '.')).doubleValue();
                    flag = it.getValue().getPrecoPorDia() == tempDouble;
                    break;
                case 10:
                    char tempChar = valorAtributo.toUpperCase().toCharArray()[0];
                    flag = it.getValue().getStatus() == tempChar;
            }
            
            if(flag){
                ret.add(it.getValue());
            }
        }
        
        return ret;
    }
    
    public static ArrayList<Cliente> buscaCliente(int codigoAtributo, String valorAtributo, HashMap<String, Cliente> clientes){
        ArrayList<Cliente> ret = new ArrayList<>();
        
        for(Map.Entry<String, Cliente> it : clientes.entrySet()){
            Boolean flag = false;
            
            switch(codigoAtributo){
                case 1:
                    flag = it.getValue().getNome().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 2:
                    flag = it.getValue().getSobrenome().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 3:
                    flag = it.getValue().getEndereco().toLowerCase().contains(valorAtributo.toLowerCase());
                    break;
                case 4:
                    flag = it.getValue().getDataDeNascimento().equals(valorAtributo);
                    break;
                case 5:
                    flag = it.getValue().getEmail().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 6:
                    flag = it.getValue().getTelefone().equals(valorAtributo);
                    break;
                case 7:
                    flag = it.getValue().getCpf().equals(valorAtributo);
                    break;
                case 8:
                    flag = it.getValue().getCnh().equals(valorAtributo);
                    break;
            }
            
            if(flag){
                ret.add(it.getValue());
            }
        }
        
        return ret;
    }
    
    public static ArrayList<Funcionario> buscaFuncionario(int codigoAtributo, String valorAtributo, HashMap<String, Funcionario> funcionarios){
        ArrayList<Funcionario> ret = new ArrayList<>();
        
        for(Map.Entry<String, Funcionario> it : funcionarios.entrySet()){
            Boolean flag = false;
            
            switch(codigoAtributo){
                case 1:
                    flag = it.getValue().getNome().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 2:
                    flag = it.getValue().getSobrenome().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 3:
                    flag = it.getValue().getEndereco().toLowerCase().contains(valorAtributo.toLowerCase());
                    break;
                case 4:
                    flag = it.getValue().getDataDeNascimento().equals(valorAtributo);
                    break;
                case 5:
                    flag = it.getValue().getEmail().toLowerCase().equals(valorAtributo.toLowerCase());
                    break;
                case 6:
                    flag = it.getValue().getTelefone().equals(valorAtributo);
                    break;
                case 7:
                    flag = it.getValue().getCpf().equals(valorAtributo);
                    break;
                case 8:
                    flag = it.getValue().getCarteiraTrabalho().equals(valorAtributo);
                    break;
                case 9:
                    flag = it.getValue().getDataContratacao().equals(valorAtributo);
                    break;
                case 10:
                    char tempChar = valorAtributo.toUpperCase().toCharArray()[0];
                    flag = it.getValue().getTipoFuncionario() == tempChar;
                    break;
                case 11:
                    double tempDouble = Double.valueOf(valorAtributo.replace(',', '.')).doubleValue();
                    flag = it.getValue().getSalario() == tempDouble;
                    break;
            }
            
            if(flag){
                ret.add(it.getValue());
            }
        }
        
        return ret;
    }
    
    public static Reserva buscaReserva(String placa, String cpf, Agencia agencia) throws ParseException{
       
        for(Map.Entry<String, Veiculo> it : agencia.getVeiculos().entrySet()){
            for(Reserva r : it.getValue().getReservas()){
                if(r.getVeiculo().getPlaca().equals(placa) && r.getCliente().getCpf().equals(cpf) && r.getReservaFinalizada() == false){
                    Date dataHoje = new Date();
                    
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    
                    Date data1 = format.parse("01/01/2019");
                    
                    long diferenca = dataHoje.getTime() - data1.getTime();
                    
                    long valorComparacao = diferenca / (24 * 60 * 60 * 1000);
                    
                    Data dataRetirada = new Data(r.getDataDeRetirada());
                    Data dataEntrega = new Data(r.getDataDeEntrega());
                    
                    if(dataRetirada.getDataValor() <= valorComparacao && dataEntrega.getDataValor() >= valorComparacao){
                        return r;
                    }
                }
            }
        }
        
        return null;
    }
    
}
