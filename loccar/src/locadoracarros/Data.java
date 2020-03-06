/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author diego
 */
public class Data implements Serializable {
    
    //declaração de variáveis
    private String data;
    private long dataValor; // variável responsável por armazenar a quantidade de dias passados entre 01/01/2019 até a data armazenada na variável data

    //declaração do construtor
    public Data(String data) throws ParseException {
        this.data = data;
        dataValor = calculaValor(data);
    }

        //declaracao de metodos
    public String getData() {
        return data;
    }

    public void setData(String data) throws ParseException, Exception {
        if(LocadoraCarros.validaData(data)){
            this.data = data;
            dataValor = calculaValor(data);
        }else{
            this.data = null;
            dataValor = -1;
            throw new Exception("Data Invalida!");
        }
    }

    public long getDataValor() {
        return dataValor;
    }
    
    //método responsável por calcular o valor da variável dataValor
    private long calculaValor(String data) throws ParseException{
        long diferenca, ret;
        Date data1, data2;
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        ret = 0;
        
        data1 = format.parse("01/01/2019");
        data2 = format.parse(data);
        
        
        diferenca = data2.getTime() - data1.getTime();
        
        ret = diferenca / (24 * 60 * 60 * 1000);
        
        return ret;
    }
    
}
