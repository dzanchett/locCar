/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author diego
 */
public class LocadoraCarros {

    /**
     * @param args the command line arguments
     */
    
    private static HashMap<String, Agencia> agenciasGlobal;
    
    //Método responsável por limpar a tela
    private static void clearScreen(){
        System.out.print("\033\143");
    }
    
    //Método responsável por alterar a cor da fonte para vermelho
    private static void fonteVermelha(){
        System.out.print("\033[31m");
    }
    
    //Método responsável por alterar a cor da fonte para verde
    private static void fonteVerde(){
        System.out.print("\033[32m");
    }
    
    //Método responsável por alterar a cor da fonte para a cor padrão
    private static void fontePadrao(){
        System.out.print("\033[0m");
    }
    
    //Método responsável por encerrar o programa se a string s for igual ao comando para encerrar "@sair"
    private static void testaSaida(String s){
        if(s.equals("@sair")){
                System.out.println();
                System.out.println("Até logo!");
                System.exit(0);
            }
    }
    
    //Método responsável por imprimir um erro na tela
    private static void imprimeErro(String erro, java.io.Console console){
        if(console != null){
            fonteVermelha();
        }

        System.out.println(erro);
        System.out.println();

        if(console != null){
            fontePadrao();
        }
    }
    
    //Método responsável por imprimir uma mensagem de sucesso na tela
    private static void imprimeSucesso(String mensagem, java.io.Console console){
        if(console != null){
            fonteVerde();
        }

        System.out.println(mensagem);
        System.out.println();

        if(console != null){
            fontePadrao();
        }
    }
    
    //Método responsável por validar a string recebida do usuário correspondente a uma data
    public static Boolean validaData(String data){
        char[] array = data.toCharArray();
        
        if(!data.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
            return false;
        }
        
        return true;
    }
    
    //Método responsável por validar a string recebida do usuário correspondente a um e-mail
    public static Boolean validaEmail(String email){        
        if(email.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")){
            return true;
        }else{
            return false;
        }
    }
    
    //Método responsável por validar a string recebida do usuário correspondente a um C.P.F.
    public static Boolean validaCpf(String cpf){
        char[] array = cpf.toCharArray();
        
        if(!cpf.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}")){
            return false;
        }
        
        int[] digitosVerificadores = {array[12] - '0', array[13] - '0'};
        int[] digitos = {array[0], array[1], array[2], array[4], array[5], array[6], array[8], array[9], array[10], 0};
        
        for(int i = 0; i<9; i++){
            digitos[i] = digitos[i] - '0';
        }
        
        int temp = 0;
        
        for(int i = 0, j = 10; i<9; i++, j--){
            temp += digitos[i] * j;
        }
        
        temp *= 10;
        temp %= 11;
        
        if(temp == 10){
            temp = 0;
        }
        
        if(temp != digitosVerificadores[0]){
            return false;
        }
        
        digitos[9] = temp;
        
        temp = 0;
        
        for(int i = 0, j = 11; i<10; i++, j--){
            temp += digitos[i] * j;
        }
        
        temp *= 10;
        temp %= 11;
        
        if(temp == 10){
            temp = 0;
        }
        
        if(temp != digitosVerificadores[1]){
            return false;
        }
        
        return true;
    }
    
    //Método responsável por validar a string recebida do usuário correspondente a um C.N.P.J.
    public static Boolean validaCnpj(String cnpj){
        char[] array = cnpj.toCharArray();
        
        if(!cnpj.matches("[0-9]{2}.[0-9]{3}.[0-9]{3}\\/[0-9]{4}-[0-9]{2}")){
            return false;
        }
        
        int[] digitosVerificadores = {array[16] - '0', array[17] - '0'};
        int[] digitos = {array[0], array[1], array[3], array[4], array[5], array[7], array[8], array[9], array[11], array[12], array[13], array[14], 0};
        int[] sequenciaValidacao = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        for(int i = 0; i<12; i++){
            digitos[i] = digitos[i] - '0';
        }
        
        int temp = 0;
        
        for(int i = 0, j = 1; i<12; i++, j++){
            temp += digitos[i] * sequenciaValidacao[j];
        }
        
        temp %= 11;
        
        if(temp < 2){
            temp = 0;
        }else{
            temp = 11 - temp;
        }
        
        if(temp != digitosVerificadores[0]){
            return false;
        }
        
        digitos[12] = temp;
        
        temp = 0;
        
        for(int i = 0, j = 0; i<13; i++, j++){
            temp += digitos[i] * sequenciaValidacao[j];
        }
        
        temp %= 11;
        
        if(temp < 2){
            temp = 0;
        }else{
            temp = 11 - temp;
        }
        
        if(temp != digitosVerificadores[1]){
            return false;
        }
        
        return true;
    }
    
    //Método responsável por validar a string recebida do usuário correspondente a uma senha
    public static Boolean validaSenha(String senha){
        
        if(senha.matches("((?=.*?[0-9])(?=.*?[A-Za-z]).{6,})")){
            return true;
        }else{
            return false;
        }
            
    }
    
    //Método responsável por validar a string recebida do usuário correspondente a um tipo de funcionário
    public static Boolean validaTipoFuncionario(char tipoFuncionario){
        
        if(tipoFuncionario == 'G' || tipoFuncionario == 'A'){
            return true;
        }else{
            return false;
        }
            
    }
    
    //Método responsável por validar um valor (double) recebido do usuário correspondente ao valor do salário
    public static Boolean validaSalario(double salario){
        if(salario > 0.0){
            return true;
        }else{
            return false;
        }
    }
    
    //Método responsável por validar um valor (double) recebido do usuário correspondente ao preço por dia de um veículo
    public static Boolean validaPrecoPorDia(double precoPorDia){
        if(precoPorDia > 0.0){
            return true;
        }else{
            return false;
        }
    }
    
    //Método responsável por validar a string recebida do usuário correspondente a uma placa de um veículo
    public static Boolean validaPlaca(String placa){
        char[] array = placa.toCharArray();
        
        if(!placa.matches("[A-Z]{3}-[0-9]{4}")){
            return false;
        }else{
            return true;
        }
    }
    
    //Método responsável por validar um veículo
    private static int validaVeiculo(String dataRetirada, String dataDevolucao, String placa, Agencia agencia) throws ParseException{
        
        ArrayList<Veiculo> veiculosLista = Buscador.buscaVeiculo(1, placa, agencia.getVeiculos());
        
        if(veiculosLista.size() > 0){
            if(veiculosLista.get(0).consultaDisponibilidade(dataRetirada, dataDevolucao) == false){
                return 2;
            }else{
                return 0;
            }
        }else{
            return 1;
        }   
    }
    
    //Método responsável por validar um veículo
    private static int validaVeiculo(String placa, Agencia agencia) throws ParseException{
        
        ArrayList<Veiculo> veiculosLista = Buscador.buscaVeiculo(1, placa, agencia.getVeiculos());
        
        if(veiculosLista.size() > 0){
            return 0;
        }else{
            return 1;
        }   
    }
    
    //Método responsável por validar um cliente
    private static Boolean validaCliente(String cpf, Agencia agencia) throws ParseException{
        
        ArrayList<Cliente> clientesLista = Buscador.buscaCliente(7, cpf, agencia.getClientes());
        
        if(clientesLista.size() > 0){
            return true;
        }else{
            return false;
        }   
    }
    
    private static void salvar(HashMap<String, Agencia> agencias){
        ObjectOutputStream output;
        
        try {
            output = new ObjectOutputStream(new FileOutputStream("dados.loccar"));
            
            output.writeObject(agencias);
            
            output.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Erro ao salvar os dados!");
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println("Erro ao salvar os dados!");
            System.err.println(ex);
        }
    }
    
    public static void salvar(){
        ObjectOutputStream output;
        AlertaPopUp alerta = null;
        
        try {
            output = new ObjectOutputStream(new FileOutputStream("dados.loccar"));
            
            output.writeObject(agenciasGlobal);
            
            output.close();
            
            alerta = new AlertaPopUp("Arquivo salvo com sucesso!");
            
            alerta.pack();
            alerta.setLocationRelativeTo(null);
            alerta.setVisible(true);
        } catch (FileNotFoundException ex) {
            System.err.println("Erro ao salvar os dados!");
            System.err.println(ex);
            alerta = new AlertaPopUp("Erro ao salvar os dados!");
            
            alerta.pack();
            alerta.setLocationRelativeTo(null);
            alerta.setVisible(true);
        } catch (IOException ex) {
            System.err.println("Erro ao salvar os dados!");
            System.err.println(ex);
            alerta = new AlertaPopUp("Erro ao salvar os dados!");
            
            alerta.pack();
            alerta.setLocationRelativeTo(null);
            alerta.setVisible(true);
        }
    }
    
    //Método principal
    public static void main(String[] args) throws ParseException {
        
        //Declaração de Variáveis
        Scanner sc = new Scanner(System.in);
        java.io.Console console = System.console(); 
        
        HashMap<String, Agencia> agencias = new HashMap<>();
        
        String codigoAgencia = null;
        Boolean flagErro = false;
        Boolean flagErro2 = false;
        
        String nome;
        String sobrenome;
        String endereco;
        String dataDeNascimento;
        String email;
        String telefone;
        String cpf;
        String cnh;
        String carteiraTrabalho;
        String dataContratacao;
        String loginCadastro;
        String senhaCadastro;
        char tipoFuncionario;
        double salario;
        
        String login;
        String senha;
        
        String placa;
        String categoria;
        String marca;
        String modelo;
        int ano;
        String motor;
        String cambio;
        String acessorios;
        double precoPorDia;
        
        int ret;
        String mensagemSucesso;
        String temp;
        
        String codigo;
        String CNPJ;
        
        String dataRetirada;
        String dataDevolucao;
        double valorTotal;
        
        int ttt;
        
        Cliente cliente;
        Funcionario funcionario;
        Veiculo veiculo;
        Agencia agencia;
        Reserva reserva;
        
        ArrayList<Agencia> resultadoBuscaAgencia;
        ArrayList<Veiculo> resultadoBuscaVeiculo;
        ArrayList<Cliente> resultadoBuscaCliente;
        ArrayList<Funcionario> resultadoBuscaFuncionario;
        
        ObjectInputStream arquivoEntrada;
        
        try {
            arquivoEntrada = new ObjectInputStream(new FileInputStream("dados.loccar"));
            
            agencias = (HashMap<String, Agencia>) arquivoEntrada.readObject();
            
            arquivoEntrada.close();
        } catch (FileNotFoundException | ClassNotFoundException ex) {
            //Declaração e inserção da agência padrão do sistema
            //É a primeira agência a ser criada para que o primeiro usuário possa acessar o sistema
            Agencia a;
            try {
                a = new Agencia();
            
                a.setNome("LocCar");
                a.setCodigo("AGENCIA");
                a.setEndereco("endereço agencia");
                a.setTelefone("(00) 00000-0000");
                try {
                    a.setCNPJ("81.042.308/0001-00");
                } catch (Exception e) {
                    System.out.println(e);
                }

                agencias.put(a.getCodigo(), a);
            } catch (Exception e) {
                System.err.println("Erro na Criaçao da Agencia Padrao!");
                System.err.println(e);
                System.exit(1);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        agenciasGlobal = agencias;
        
        InterfaceGrafica ui = new InterfaceGrafica(agencias);
        
        if(!(args.length == 1 && args[0].equals("cli"))){
            ui.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            ui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            ui.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    ConfirmacaoPopUp alerta = new ConfirmacaoPopUp("Voce tem certeza que deseja sair?");
                    alerta.pack();
                    alerta.setLocationRelativeTo(null);
                    alerta.setVisible(true);
                }
            });
            
            ui.setVisible(true);
        }else{
            //loop do menu
            while(codigoAgencia == null){
                if(console != null){
                    clearScreen();
                }

                System.out.println("Bem vindo ao sistema LocCar 1.0");
                System.out.println("(Para sair digite @sair e precione enter!)");
                System.out.println();

                if(flagErro == true){
                    imprimeErro("Codigo invalido, tente novamente!", console);
                }

                System.out.println("Informe o código da Agencia:");
                codigoAgencia = sc.nextLine();
                testaSaida(codigoAgencia);

                if(agencias.containsKey(codigoAgencia)){
                    flagErro = false;
                }else{
                    flagErro = true;
                    codigoAgencia = null;
                }
            }

            mensagemSucesso = null;

            while(true){

                if(console != null){
                    clearScreen();
                }

                System.out.println("Bem vindo ao sistema LocCar 1.0");
                System.out.println("(Para sair digite @sair e precione enter!)");
                System.out.println();

                if(flagErro == true){
                    imprimeErro("Login ou senha incorretos, tente novamente!", console);
                }

                System.out.println("Faça login para iniciarmos!");
                System.out.println("Login:");
                login = sc.nextLine();
                testaSaida(login);

                System.out.println("Senha:");

                if(console != null){
                    senha = new String(console.readPassword());
                }else{
                    senha = sc.nextLine();
                }

                testaSaida(senha);

                if(agencias.get(codigoAgencia).validaLogin(login, senha)){
                    flagErro = false;

                    char tipoUsuario = agencias.get(codigoAgencia).getTipoFuncionario(login);
                    String cpfUsuario = agencias.get(codigoAgencia).getCpfFuncionario(login);

                    while(login != null){
                        if(console != null){
                            clearScreen();
                        }

                        int opcaoMenu1;
                        int opcaoMenu2;
                        int opcaoMenu3;

                        System.out.println("Bem vindo ao sistema LocCar 1.0");
                        System.out.println();

                        if(mensagemSucesso != null){
                            imprimeSucesso(mensagemSucesso, console);
                            mensagemSucesso = null;
                        }

                        if(flagErro){
                            imprimeErro("Opção invalida, tente novamente!", console);
                        }

                        System.out.println("Insira um numero refente a uma opção do menu principal:");
                        System.out.println();

                        System.out.println("\t 1 - Cadastro");
                        if(tipoUsuario == 'G') System.out.println("\t 2 - Remoção");
                        System.out.println("\t " + (tipoUsuario != 'G' ? 2 : 3) + " - Busca");
                        System.out.println("\t " + (tipoUsuario != 'G' ? 3 : 4) + " - Locação de veículos");
                        System.out.println("\t " + (tipoUsuario != 'G' ? 4 : 5) + " - Retirada de veículo");
                        System.out.println("\t " + (tipoUsuario != 'G' ? 5 : 6) + " - Devolução de veículo");
                        System.out.println("\t " + (tipoUsuario != 'G' ? 6 : 7) + " - Logout");
                        System.out.println("\t " + (tipoUsuario != 'G' ? 7 : 8) + " - Sair");
                        System.out.println();

                        System.out.print(">>> ");

                        opcaoMenu1 = sc.nextInt();
                        sc.nextLine();

                        if(tipoUsuario != 'G' && opcaoMenu1 > 1){
                            opcaoMenu1++;
                        }

                        flagErro = false;

                        switch(opcaoMenu1){
                            case 1:                            
                                System.out.println();

                                System.out.println("Insira um numero refente a uma opção do menu de cadastro:");
                                System.out.println();

                                System.out.println("\t 1 - Cadastro de cliente");
                                if(tipoUsuario == 'G') System.out.println("\t 2 - Cadastro de funcionário");
                                if(tipoUsuario == 'G') System.out.println("\t 3 - Cadastro de veículo");
                                if(tipoUsuario == 'G') System.out.println("\t 4 - Cadastro de agência");
                                System.out.println("\t " + (tipoUsuario != 'G' ? 2 : 5) + " - voltar");
                                System.out.println();

                                System.out.print(">>> ");

                                opcaoMenu2 = sc.nextInt();
                                sc.nextLine();

                                if(tipoUsuario != 'G' && opcaoMenu2 == 2){
                                    opcaoMenu2 = 5;
                                }

                                switch(opcaoMenu2){
                                    case 1:
                                        if(console != null){
                                            clearScreen();
                                        }
                                        System.out.println("Cadastro de Cliente:");
                                        System.out.println("Insira os dados do cliente:");
                                        System.out.println();

                                        System.out.print("Nome: ");
                                        nome = sc.nextLine();
                                        System.out.print("Sobrenome: ");
                                        sobrenome = sc.nextLine();
                                        System.out.print("Endereço: ");
                                        endereco = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Formato de data incorreto, tente novamente!", console);
                                            }

                                            System.out.print("Data de Nascimento (formato DD/MM/AAAA): ");
                                            dataDeNascimento = sc.nextLine();

                                            flagErro = true;
                                        } while(validaData(dataDeNascimento) != true);

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Formato de email incorreto, tente novamente!", console);
                                            }

                                            System.out.print("E-mail (formato seuemail@email.com): ");
                                            email = sc.nextLine();

                                            flagErro = true;
                                        } while(validaEmail(email) != true);

                                        System.out.print("Telefone: ");
                                        telefone = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("CPF inválido, tente novamente!", console);
                                            }

                                            System.out.print("C.P.F. (formato ###.###.###-##): ");
                                            cpf = sc.nextLine();

                                            flagErro = true;
                                        } while(validaCpf(cpf) != true);

                                        System.out.print("C.N.H.: ");
                                        cnh = sc.nextLine();

                                        {
                                            try {
                                                cliente = new Cliente(cnh, nome, sobrenome, endereco, dataDeNascimento, email, telefone, cpf);
                                            } catch (Exception ex) {
                                                System.err.println("Erro ao Cadastrar Cliente");
                                                System.err.println(ex);
                                                break;
                                            }
                                        }

                                        agencias.get(codigoAgencia).addCliente(cliente);

                                        flagErro = false;

                                        mensagemSucesso = "Cliente cadastrado com sucesso!";

                                        break;

                                    case 2:
                                        if(tipoUsuario != 'G'){
                                            flagErro = true;
                                            break;
                                        }

                                        if(console != null){
                                            clearScreen();
                                        }
                                        System.out.println("Cadastro de Funcionário:");
                                        System.out.println("Insira os dados do funcionário:");
                                        System.out.println();

                                        System.out.print("Nome: ");
                                        nome = sc.nextLine();
                                        System.out.print("Sobrenome: ");
                                        sobrenome = sc.nextLine();
                                        System.out.print("Endereço: ");
                                        endereco = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Formato de data incorreto, tente novamente!", console);
                                            }

                                            System.out.print("Data de Nascimento (formato DD/MM/AAAA): ");
                                            dataDeNascimento = sc.nextLine();

                                            flagErro = true;
                                        } while(validaData(dataDeNascimento) != true);

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Formato de email incorreto, tente novamente!", console);
                                            }

                                            System.out.print("E-mail (formato seuemail@email.com): ");
                                            email = sc.nextLine();

                                            flagErro = true;
                                        } while(validaEmail(email) != true);

                                        System.out.print("Telefone: ");
                                        telefone = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("CPF inválido, tente novamente!", console);
                                            }

                                            System.out.print("C.P.F. (formato ###.###.###-##): ");
                                            cpf = sc.nextLine();

                                            flagErro = true;
                                        } while(validaCpf(cpf) != true);

                                        System.out.print("Carteira de Trabalho: ");
                                        carteiraTrabalho = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Formato de data incorreto, tente novamente!", console);
                                            }

                                            System.out.print("Data de Contratação (formato DD/MM/AAAA): ");
                                            dataContratacao = sc.nextLine();

                                            flagErro = true;
                                        } while(validaData(dataContratacao) != true);

                                        System.out.print("Login: ");
                                        loginCadastro = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Senha inválida, tente novamente!", console);
                                            }

                                            System.out.print("Senha (mínimo 6 carcteres contendo ao menos uma letra e um número): ");

                                            if(console != null){
                                                senhaCadastro = new String(console.readPassword());
                                            }else{
                                                senhaCadastro = sc.nextLine();
                                            }

                                            flagErro = true;
                                        } while(validaSenha(senhaCadastro) != true);

                                        tipoFuncionario = 0;

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Tipo de funcionário inválido, tente novamente!", console);
                                            }

                                            System.out.print("Tipo de funcionário (gerente (G) ou atendente (A)): ");
                                            String tt = sc.nextLine();

                                            if(tt.length() > 1){
                                                flagErro = true;
                                                continue;
                                            }

                                            tt = tt.toUpperCase();
                                            tipoFuncionario = tt.toCharArray()[0];

                                            flagErro = true;
                                        } while(validaTipoFuncionario(tipoFuncionario) != true);

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Salário Inválido, tente novamente!", console);
                                            }

                                            if(console != null){
                                                System.out.print("Salário (formato R$ ##,##): ");
                                            }else{
                                                System.out.print("Salário (formato R$ ##.##): ");
                                            }
                                            salario = sc.nextDouble();
                                            sc.nextLine();

                                            flagErro = true;
                                        } while(validaSalario(salario) != true);

                                        {
                                            try {
                                                funcionario = new Funcionario(carteiraTrabalho, dataContratacao, loginCadastro, senhaCadastro, tipoFuncionario, salario, nome, sobrenome, endereco, dataDeNascimento, email, telefone, cpf);
                                            } catch (Exception ex) {
                                                System.err.println("Erro ao cadastrar funcionario!");
                                                System.err.println(ex);
                                                break;
                                            }
                                        }

                                        agencias.get(codigoAgencia).addFuncionario(funcionario);

                                        flagErro = false;

                                        mensagemSucesso = "Funcionário cadastrado com sucesso!";

                                        break;

                                    case 3:
                                        if(tipoUsuario != 'G'){
                                            flagErro = true;
                                            break;
                                        }

                                        if(console != null){
                                            clearScreen();
                                        }
                                        System.out.println("Cadastro de Veículo:");
                                        System.out.println("Insira os dados do veículo:");
                                        System.out.println();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Placa inválida, tente novamente!", console);
                                            }

                                            System.out.print("Placa: ");
                                            placa = sc.nextLine();

                                            placa = placa.toUpperCase();

                                            flagErro = true;
                                        } while(validaPlaca(placa) != true);

                                        System.out.print("Categoria: ");
                                        categoria = sc.nextLine();

                                        System.out.print("Marca: ");
                                        marca = sc.nextLine();

                                        System.out.print("Modelo: ");
                                        modelo = sc.nextLine();

                                        System.out.print("Ano: ");
                                        ano = sc.nextInt();
                                        sc.nextLine();

                                        System.out.print("Motor: ");
                                        motor = sc.nextLine();

                                        System.out.print("Câmbio: ");
                                        cambio = sc.nextLine();

                                        System.out.print("Acessórios: ");
                                        acessorios = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("Preço por dia inválido, tente novamente!", console);
                                            }

                                            if(console != null){
                                                System.out.print("Preço por dia (formato R$ ##,##): ");
                                            }else{
                                                System.out.print("Preço por dia (formato R$ ##.##): ");
                                            }
                                            precoPorDia = sc.nextDouble();
                                            sc.nextLine();

                                            flagErro = true;
                                        } while(validaPrecoPorDia(precoPorDia) != true);

                                        {
                                            try {
                                                veiculo = new Veiculo(placa, categoria, marca, modelo, ano, motor, cambio, acessorios, precoPorDia);
                                            } catch (Exception ex) {
                                                System.err.println("Erro ao Cadastrar Veiculo!");
                                                System.err.println(ex);
                                                break;
                                            }
                                        }

                                        agencias.get(codigoAgencia).addVeiculo(veiculo);

                                        flagErro = false;

                                        mensagemSucesso = "Veículo cadastrado com sucesso!";

                                        break;

                                    case 4:
                                        if(tipoUsuario != 'G'){
                                            flagErro = true;
                                            break;
                                        }

                                        if(console != null){
                                            clearScreen();
                                        }
                                        System.out.println("Cadastro de Agência:");
                                        System.out.println("Insira os dados do agência:");
                                        System.out.println();

                                        System.out.print("Nome: ");
                                        nome = sc.nextLine();

                                        System.out.print("Código: ");
                                        codigo = sc.nextLine();

                                        System.out.print("Endereço: ");
                                        endereco = sc.nextLine();

                                        System.out.print("Telefone: ");
                                        telefone = sc.nextLine();

                                        flagErro = false;

                                        do{
                                            if(flagErro){
                                                imprimeErro("CNPJ inválido, tente novamente!", console);
                                            }

                                            System.out.print("C.N.P.J. (formato ##.###.###/####-##): ");
                                            CNPJ = sc.nextLine();

                                            flagErro = true;
                                        } while(validaCnpj(CNPJ) != true);

                                        {
                                            try {
                                                agencia = new Agencia(nome, codigo, endereco, telefone, CNPJ);
                                            } catch (Exception ex) {
                                                System.err.println("Erro ao cadastrar agencia!");
                                                System.err.println(ex);
                                                break;
                                            }
                                        }

                                        agencias.put(agencia.getCodigo(), agencia);

                                        flagErro = false;

                                        mensagemSucesso = "Agência cadastrada com sucesso!";

                                        break;

                                    case 5:
                                        break;
                                    default:
                                        flagErro = true;
                                }
                                break;
                            case 2:
                                System.out.println();

                                System.out.println("Insira um numero refente a uma opção do menu de remoção:");
                                System.out.println();

                                System.out.println("\t 1 - Remover cliente");
                                System.out.println("\t 2 - Remover funcionário");
                                System.out.println("\t 3 - Remover veículo");
                                System.out.println("\t 4 - Remover agência");
                                System.out.println("\t 5 - voltar");
                                System.out.println();

                                System.out.print(">>> ");

                                opcaoMenu2 = sc.nextInt();
                                sc.nextLine();

                                switch(opcaoMenu2){
                                    case 1:
                                        flagErro = false;
                                        cpf = null;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Remover Cliente:");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Cliente de C.P.F. " + cpf + " não encontrado, tente novamente!", console);
                                            }

                                            System.out.println("Insira o C.P.F. do cliente que deseja remover:");
                                            System.out.println("(Para voltar digite @voltar e precione enter!)");
                                            System.out.println();

                                            cpf = sc.nextLine();

                                            if(cpf.equals("@voltar")){
                                                flagErro = false;
                                                break;
                                            }

                                            ret = agencias.get(codigoAgencia).removerCliente(cpf);

                                            if(ret == 0){
                                                flagErro = false;
                                                mensagemSucesso = "Cliente removido!";
                                                break;
                                            }else{
                                                flagErro = true;
                                            }
                                        }
                                        break;
                                    case 2:
                                        flagErro = false;
                                        flagErro2 = false;
                                        cpf = null;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Remover Funcionário:");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Funcionário de C.P.F. " + cpf + " não encontrado, tente novamente!", console);
                                            }

                                            if(flagErro2){
                                                imprimeErro("Não é possível remover o funcionário que está logado no momento!", console);
                                            }

                                            System.out.println("Insira o C.P.F. do funcionário que deseja remover:");
                                            System.out.println("(Para voltar digite @voltar e precione enter!)");
                                            System.out.println();

                                            cpf = sc.nextLine();

                                            if(cpf.equals("@voltar")){
                                                flagErro = false;
                                                flagErro2 = false;
                                                break;
                                            }

                                            if(cpf.equals(cpfUsuario)){
                                                flagErro = false;
                                                flagErro2 = true;
                                                continue;
                                            }

                                            ret = agencias.get(codigoAgencia).removerFuncionario(cpf);

                                            if(ret == 0){
                                                flagErro = false;
                                                flagErro2 = false;
                                                mensagemSucesso = "Funcionário removido!";
                                                break;
                                            }else{
                                                flagErro = true;
                                                flagErro2 = false;
                                            }
                                        }
                                        break;
                                    case 3:
                                        flagErro = false;
                                        placa = null;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Remover Veículo:");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Veículo de placa " + placa + " não encontrado, tente novamente!", console);
                                            }

                                            System.out.println("Insira a placa do veículo que deeja remover:");
                                            System.out.println("(Para voltar digite @voltar e precione enter!)");
                                            System.out.println();

                                            placa = sc.nextLine();

                                            if(placa.equals("@voltar")){
                                                flagErro = false;
                                                break;
                                            }

                                            placa.toUpperCase();

                                            ret = agencias.get(codigoAgencia).removerVeiculo(placa);

                                            if(ret == 0){
                                                flagErro = false;
                                                mensagemSucesso = "Veículo removido!";
                                                break;
                                            }else{
                                                flagErro = true;
                                            }
                                        }
                                        break;
                                    case 4:
                                        flagErro = false;
                                        flagErro2 = false;
                                        codigo = null;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Remover Agência:");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Agência de código " + codigo + " não encontrada, tente novamente!", console);
                                            }

                                            if(flagErro2){
                                                imprimeErro("Não é possível remover a agência que está ativa no momento!", console);
                                            }

                                            System.out.println("Insira o código da agência que deseja remover:");
                                            System.out.println("(Para voltar digite @voltar e precione enter!)");
                                            System.out.println();

                                            codigo = sc.nextLine();

                                            if(codigo.equals("@voltar")){
                                                flagErro = false;
                                                flagErro2 = false;
                                                break;
                                            }

                                            if(codigo.equals(codigoAgencia)){
                                                flagErro = false;
                                                flagErro2 = true;
                                                continue;
                                            }

                                            agencia = agencias.remove(codigo);

                                            if(agencia != null){
                                                flagErro = false;
                                                flagErro2 = false;
                                                mensagemSucesso = "Agência removida!";
                                                break;
                                            }else{
                                                flagErro = true;
                                                flagErro2 = false;
                                            }
                                        }
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        flagErro = true;
                                }
                                break;
                            case 3:
                                System.out.println();

                                System.out.println("Insira um numero refente a uma opção do menu de busca:");
                                System.out.println();

                                System.out.println("\t 1 - Buscar agência");
                                System.out.println("\t 2 - Buscar veículo");
                                System.out.println("\t 3 - Buscar cliente");
                                if(tipoUsuario == 'G') System.out.println("\t 4 - Buscar funcionário");
                                System.out.println("\t " + (tipoUsuario != 'G' ? 4 : 5) + " - voltar");
                                System.out.println();

                                System.out.print(">>> ");

                                opcaoMenu2 = sc.nextInt();
                                sc.nextLine();

                                if(tipoUsuario != 'G' && opcaoMenu2 == 4){
                                    opcaoMenu2 = 5;
                                }

                                switch(opcaoMenu2){
                                    case 1:
                                        flagErro = false;
                                        flagErro2 = false;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Buscar Agência");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Atributo inválido, tente novamente!", console);
                                            }

                                            if(flagErro2){
                                                imprimeErro("Nenhuma agência foi encontrada!", console);
                                            }

                                            System.out.println("Escolha um dos atributos abaixo para filtrar sua busca ou retornar ao menu principal:");
                                            System.out.println();

                                            System.out.println("\t 1 - Nome");
                                            System.out.println("\t 2 - Código");
                                            System.out.println("\t 3 - Endereço");
                                            System.out.println("\t 4 - Telefone");
                                            System.out.println("\t 5 - CNPJ");
                                            System.out.println("\t 6 - Retornar ao menu principal");
                                            System.out.println();

                                            System.out.print(">>> ");

                                            opcaoMenu3 = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println();

                                            switch(opcaoMenu3){
                                                case 1:
                                                    System.out.println("Insira o nome da agência que deseja buscar:");
                                                    break;
                                                case 2:
                                                    System.out.println("Insira o código da agência que deseja buscar:");
                                                    break;
                                                case 3:
                                                    System.out.println("Insira o endereço da agência que deseja buscar:");
                                                    break;
                                                case 4:
                                                    System.out.println("Insira o telefone da agência que deseja buscar:");
                                                    break;
                                                case 5:
                                                    System.out.println("Insira o C.N.P.J. da agencia que deseja buscar:");
                                                    break;
                                                case 6:
                                                    flagErro = true;
                                                    flagErro2 = true;
                                                    break;
                                                default:
                                                    flagErro = true;
                                                    flagErro2 = false;
                                            }

                                            if(flagErro == true && flagErro2 == true){
                                                flagErro = false;
                                                flagErro2 = false;
                                                break;
                                            }

                                            if(flagErro == true){
                                                continue;
                                            }

                                            temp = sc.nextLine();
                                            System.out.println();

                                            resultadoBuscaAgencia = Buscador.buscaAgencia(opcaoMenu3, temp, agencias);
                                            opcaoMenu3 = 0;

                                            for(Agencia aa : resultadoBuscaAgencia){
                                                opcaoMenu3++;
                                                System.out.println("Resultado " + opcaoMenu3 + ":");
                                                System.out.println("Nome: " + aa.getNome());
                                                System.out.println("Código: " + aa.getCodigo());
                                                System.out.println("Endereço: " + aa.getEndereco());
                                                System.out.println("Telefone: " + aa.getTelefone());
                                                System.out.println("C.N.P.J.: " + aa.getCNPJ());
                                                System.out.println();
                                            }

                                            if(opcaoMenu3>0){
                                                System.out.println("Fim dos resultados!");
                                                System.out.println();
                                                System.out.println("(Precione enter para voltar ao menu principal!)");
                                                sc.nextLine();
                                                flagErro = false;
                                                flagErro2 = false;
                                            }else{
                                                flagErro = false;
                                                flagErro2 = true;
                                            }

                                            if(flagErro == false && flagErro2 == false){
                                                break;
                                            }
                                        }
                                        break;
                                    case 2:
                                        flagErro = false;
                                        flagErro2 = false;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Buscar Veículo");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Atributo inválido, tente novamente!", console);
                                            }

                                            if(flagErro2){
                                                imprimeErro("Nenhum veículo foi encontrado!", console);
                                            }

                                            System.out.println("Escolha um dos atributos abaixo para filtrar sua busca ou retornar ao menu principal:");
                                            System.out.println();

                                            System.out.println("\t 1 - Placa");
                                            System.out.println("\t 2 - Categoria");
                                            System.out.println("\t 3 - Marca");
                                            System.out.println("\t 4 - Modelo");
                                            System.out.println("\t 5 - Ano");
                                            System.out.println("\t 6 - Motor");
                                            System.out.println("\t 7 - Câmbio");
                                            System.out.println("\t 8 - Acessórios");
                                            System.out.println("\t 9 - Preço por Dia");
                                            System.out.println("\t 10 - Status");
                                            System.out.println("\t 11 - Retornar ao menu principal");
                                            System.out.println();

                                            System.out.print(">>> ");

                                            opcaoMenu3 = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println();

                                            switch(opcaoMenu3){
                                                case 1:
                                                    System.out.println("Insira a placa do veículo que deseja buscar:");
                                                    break;
                                                case 2:
                                                    System.out.println("Insira a categoria do veículo que deseja buscar:");
                                                    break;
                                                case 3:
                                                    System.out.println("Insira a marca do veículo que deseja buscar:");
                                                    break;
                                                case 4:
                                                    System.out.println("Insira o modelo do veículo que deseja buscar:");
                                                    break;
                                                case 5:
                                                    System.out.println("Insira o ano do veículo que deseja buscar:");
                                                    break;
                                                case 6:
                                                    System.out.println("Insira o motor do veículo que deseja buscar:");
                                                    break;
                                                case 7:
                                                    System.out.println("Insira o câmbio do veículo que deseja buscar:");
                                                    break;
                                                case 8:
                                                    System.out.println("Insira o acessório do veículo que deseja buscar:");
                                                    break;
                                                case 9:
                                                    if(console != null){
                                                        System.out.print("Insira o preço por dia do veículo que deseja buscar (formato R$ ##,##):");
                                                    }else{
                                                        System.out.print("Insira o preço por dia do veículo que deseja buscar (formato R$ ##.##):");
                                                    }
                                                    break;
                                                case 10:
                                                    System.out.println("Insira o status do veículo que deseja buscar (na agência (A) ou com cliente (C)):");
                                                    break;
                                                case 11:
                                                    flagErro = true;
                                                    flagErro2 = true;
                                                    break;
                                                default:
                                                    flagErro = true;
                                                    flagErro2 = false;
                                            }

                                            if(flagErro == true && flagErro2 == true){
                                                flagErro = false;
                                                flagErro2 = false;
                                                break;
                                            }

                                            if(flagErro == true){
                                                continue;
                                            }

                                            temp = sc.nextLine();
                                            System.out.println();

                                            resultadoBuscaVeiculo = Buscador.buscaVeiculo(opcaoMenu3, temp, agencias.get(codigoAgencia).getVeiculos());
                                            opcaoMenu3 = 0;

                                            for(Veiculo vv : resultadoBuscaVeiculo){
                                                opcaoMenu3++;
                                                System.out.println("Resultado " + opcaoMenu3 + ":");
                                                System.out.println("Placa: " + vv.getPlaca());
                                                System.out.println("Categoria: " + vv.getCategoria());
                                                System.out.println("Marca: " + vv.getMarca());
                                                System.out.println("Modelo: " + vv.getModelo());
                                                System.out.println("Ano: " + vv.getAno());
                                                System.out.println("Motor: " + vv.getMotor());
                                                System.out.println("Câmbio: " + vv.getCambio());
                                                System.out.println("Acessórios: " + vv.getAcessorios());
                                                System.out.printf("Preço por Dia: R$%.2f\n", vv.getPrecoPorDia());
                                                System.out.println("Status: " + (vv.getStatus() == 'A' ? "Na Agência (A)" : "Com Cliente (C)"));
                                                if(vv.getReservas().size() > 0) System.out.println("Reservas:");
                                                else System.out.println();
                                                ttt = 0;
                                                for(Reserva rr : vv.getReservas()){
                                                    ttt++;
                                                    System.out.println("\tReserva " + ttt + ":");
                                                    System.out.println("\tData de Retirada: " + rr.getDataDeRetirada());
                                                    System.out.println("\tData de Entrega: " + rr.getDataDeEntrega());
                                                    System.out.println("\tC.P.F. do Cliente: " + rr.getCliente().getCpf());
                                                    System.out.printf("\tValor total: %.2f\n", rr.getValorTotal());
                                                    System.out.println();
                                                }
                                            }

                                            if(opcaoMenu3>0){
                                                System.out.println("Fim dos resultados!");
                                                System.out.println();
                                                System.out.println("(Precione enter para voltar ao menu principal!)");
                                                sc.nextLine();
                                                flagErro = false;
                                                flagErro2 = false;
                                            }else{
                                                flagErro = false;
                                                flagErro2 = true;
                                            }

                                            if(flagErro == false && flagErro2 == false){
                                                break;
                                            }
                                        }
                                        break;
                                    case 3:
                                        flagErro = false;
                                        flagErro2 = false;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Buscar Cliente");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Atributo inválido, tente novamente!", console);
                                            }

                                            if(flagErro2){
                                                imprimeErro("Nenhum cliente foi encontrado!", console);
                                            }

                                            System.out.println("Escolha um dos atributos abaixo para filtrar sua busca ou retornar ao menu principal:");
                                            System.out.println();

                                            System.out.println("\t 1 - Nome");
                                            System.out.println("\t 2 - Sobrenome");
                                            System.out.println("\t 3 - Endereço");
                                            System.out.println("\t 4 - Data de Nascimento");
                                            System.out.println("\t 5 - E-mail");
                                            System.out.println("\t 6 - Telefone");
                                            System.out.println("\t 7 - C.P.F.");
                                            System.out.println("\t 8 - C.N.H.");
                                            System.out.println("\t 9 - Retornar ao menu principal");
                                            System.out.println();

                                            System.out.print(">>> ");

                                            opcaoMenu3 = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println();

                                            switch(opcaoMenu3){
                                                case 1:
                                                    System.out.println("Insira o nome do cliente que deseja buscar:");
                                                    break;
                                                case 2:
                                                    System.out.println("Insira o sobrenome do cliente que deseja buscar:");
                                                    break;
                                                case 3:
                                                    System.out.println("Insira o endereço do cliente que deseja buscar:");
                                                    break;
                                                case 4:
                                                    System.out.println("Insira a data de nascimento do cliente que deseja buscar (formato DD/MM/AAAA):");
                                                    break;
                                                case 5:
                                                    System.out.println("Insira o e-mail do cliente que deseja buscar (formato seuemail@email.com):");
                                                    break;
                                                case 6:
                                                    System.out.println("Insira o telefone do cliente que deseja buscar:");
                                                    break;
                                                case 7:
                                                    System.out.println("Insira o C.P.F. do cliente que deseja buscar (formato ###.###.###-##):");
                                                    break;
                                                case 8:
                                                    System.out.println("Insira a C.N.H. do cliente que deseja buscar:");
                                                    break;
                                                case 9:
                                                    flagErro = true;
                                                    flagErro2 = true;
                                                    break;
                                                default:
                                                    flagErro = true;
                                                    flagErro2 = false;
                                            }

                                            if(flagErro == true && flagErro2 == true){
                                                flagErro = false;
                                                flagErro2 = false;
                                                break;
                                            }

                                            if(flagErro == true){
                                                continue;
                                            }

                                            temp = sc.nextLine();
                                            System.out.println();

                                            resultadoBuscaCliente = Buscador.buscaCliente(opcaoMenu3, temp, agencias.get(codigoAgencia).getClientes());
                                            opcaoMenu3 = 0;

                                            for(Cliente cc : resultadoBuscaCliente){
                                                opcaoMenu3++;
                                                System.out.println("Resultado " + opcaoMenu3 + ":");

                                                System.out.println("Nome: " + cc.getNome());
                                                System.out.println("Sobrenome: " + cc.getSobrenome());
                                                System.out.println("Endereço: " + cc.getEndereco());
                                                System.out.println("Data de Nascimento: " + cc.getDataDeNascimento());
                                                System.out.println("E-mail: " + cc.getEmail());
                                                System.out.println("Telefone: " + cc.getTelefone());
                                                System.out.println("C.P.F.: " + cc.getCpf());
                                                System.out.println("C.N.H.: " + cc.getCnh());
                                                if(cc.getReservas().size() > 0) System.out.println("Reservas:");
                                                else System.out.println();
                                                ttt = 0;
                                                for(Reserva rr : cc.getReservas()){
                                                    ttt++;
                                                    System.out.println("\tReserva " + ttt + ":");
                                                    System.out.println("\tData de Retirada: " + rr.getDataDeRetirada());
                                                    System.out.println("\tData de Entrega: " + rr.getDataDeEntrega());
                                                    System.out.println("\tPlaca do Veículo: " + rr.getVeiculo().getPlaca());
                                                    System.out.printf("\tValor total: %.2f\n", rr.getValorTotal());
                                                    System.out.println();
                                                }
                                            }

                                            if(opcaoMenu3>0){
                                                System.out.println("Fim dos resultados!");
                                                System.out.println();
                                                System.out.println("(Precione enter para voltar ao menu principal!)");
                                                sc.nextLine();
                                                flagErro = false;
                                                flagErro2 = false;
                                            }else{
                                                flagErro = false;
                                                flagErro2 = true;
                                            }

                                            if(flagErro == false && flagErro2 == false){
                                                break;
                                            }
                                        }
                                        break;
                                    case 4:
                                        if(tipoUsuario != 'G'){
                                            flagErro = true;
                                            break;
                                        }

                                        flagErro = false;
                                        flagErro2 = false;

                                        while(true){
                                            if(console != null){
                                                clearScreen();
                                            }

                                            System.out.println("Buscar Funcionário");
                                            System.out.println();

                                            if(flagErro){
                                                imprimeErro("Atributo inválido, tente novamente!", console);
                                            }

                                            if(flagErro2){
                                                imprimeErro("Nenhum funcionário foi encontrado!", console);
                                            }

                                            System.out.println("Escolha um dos atributos abaixo para filtrar sua busca ou retornar ao menu principal:");
                                            System.out.println();

                                            System.out.println("\t 1 - Nome");
                                            System.out.println("\t 2 - Sobrenome");
                                            System.out.println("\t 3 - Endereço");
                                            System.out.println("\t 4 - Data de Nascimento");
                                            System.out.println("\t 5 - E-mail");
                                            System.out.println("\t 6 - Telefone");
                                            System.out.println("\t 7 - C.P.F.");
                                            System.out.println("\t 8 - Carteira de Trabalho");
                                            System.out.println("\t 9 - Data de Contratação");
                                            System.out.println("\t 10 - Tipo Funcionário");
                                            System.out.println("\t 11 - Salário");
                                            System.out.println("\t 12 - Retornar ao menu principal");
                                            System.out.println();

                                            System.out.print(">>> ");

                                            opcaoMenu3 = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println();

                                            switch(opcaoMenu3){
                                                case 1:
                                                    System.out.println("Insira o nome do funcionário que deseja buscar:");
                                                    break;
                                                case 2:
                                                    System.out.println("Insira o sobrenome do funcionário que deseja buscar:");
                                                    break;
                                                case 3:
                                                    System.out.println("Insira o endereço do funcionário que deseja buscar:");
                                                    break;
                                                case 4:
                                                    System.out.println("Insira a data de nascimento do funcionário que deseja buscar (formato DD/MM/AAAA):");
                                                    break;
                                                case 5:
                                                    System.out.println("Insira o e-mail do funcionário que deseja buscar (formato seuemail@email.com):");
                                                    break;
                                                case 6:
                                                    System.out.println("Insira o telefone do funcionário que deseja buscar:");
                                                    break;
                                                case 7:
                                                    System.out.println("Insira o C.P.F. do funcionário que deseja buscar (formato ###.###.###-##):");
                                                    break;
                                                case 8:
                                                    System.out.println("Insira a carteira de trabalho do funcionário que deseja buscar:");
                                                    break;
                                                case 9:
                                                    System.out.println("Insira a data de contratação do funcionário que deseja buscar (formato DD/MM/AAAA):");
                                                    break;
                                                case 10:
                                                    System.out.println("Insira o tipo do funcionário que deseja buscar (gerente (G) ou atendente (A)):");
                                                    break;
                                                case 11:
                                                    System.out.println("Insira o salário do funcionário que deseja buscar (formato R$ ##.##):");
                                                    break;
                                                case 12:
                                                    flagErro = true;
                                                    flagErro2 = true;
                                                    break;
                                                default:
                                                    flagErro = true;
                                                    flagErro2 = false;
                                            }

                                            if(flagErro == true && flagErro2 == true){
                                                flagErro = false;
                                                flagErro2 = false;
                                                break;
                                            }

                                            if(flagErro == true){
                                                continue;
                                            }

                                            temp = sc.nextLine();
                                            System.out.println();

                                            resultadoBuscaFuncionario = Buscador.buscaFuncionario(opcaoMenu3, temp, agencias.get(codigoAgencia).getFuncionarios());
                                            opcaoMenu3 = 0;

                                            for(Funcionario ff : resultadoBuscaFuncionario){
                                                opcaoMenu3++;
                                                System.out.println("Resultado " + opcaoMenu3 + ":");

                                                System.out.println("Nome: " + ff.getNome());
                                                System.out.println("Sobrenome: " + ff.getSobrenome());
                                                System.out.println("Endereço: " + ff.getEndereco());
                                                System.out.println("Data de Nascimento: " + ff.getDataDeNascimento());
                                                System.out.println("E-mail: " + ff.getEmail());
                                                System.out.println("Telefone: " + ff.getTelefone());
                                                System.out.println("C.P.F.: " + ff.getCpf());
                                                System.out.println("Carteira de Trabalho: " + ff.getCarteiraTrabalho());
                                                System.out.println("Data de Contratação: " + ff.getDataContratacao());
                                                System.out.println("Tipo de Funcionário: " + (ff.getTipoFuncionario() == 'G' ? "Gerente (G)" : "Atendente (A)"));
                                                System.out.printf("Salário: %.2f\n", ff.getSalario());
                                                System.out.println();
                                            }

                                            if(opcaoMenu3>0){
                                                System.out.println("Fim dos resultados!");
                                                System.out.println();
                                                System.out.println("(Precione enter para voltar ao menu principal!)");
                                                sc.nextLine();
                                                flagErro = false;
                                                flagErro2 = false;
                                            }else{
                                                flagErro = false;
                                                flagErro2 = true;
                                            }

                                            if(flagErro == false && flagErro2 == false){
                                                break;
                                            }
                                        }
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        flagErro = true;
                                }
                                break;
                            case 4:
                                if(console != null){
                                    clearScreen();
                                }
                                System.out.println("Locação de Veículos");
                                System.out.println("(Para voltar digite @voltar e precione enter!)");
                                System.out.println();

                                placa = null;
                                cpf = null;

                                flagErro = false;
                                flagErro2 = false;

                                do{
                                    if(flagErro){
                                        imprimeErro("Formato de data incorreto, tente novamente!", console);
                                    }

                                    System.out.println("Insira a data de retirada (formato DD/MM/AAAA):");
                                    dataRetirada = sc.nextLine();

                                    if(dataRetirada.equals("@voltar")){
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    flagErro = true;
                                } while(validaData(dataRetirada) != true);

                                if(flagErro == true && flagErro2 == true){
                                    flagErro = false;
                                    flagErro2 = false;
                                    break;
                                }

                                flagErro = false;

                                do{
                                    if(flagErro){
                                        imprimeErro("Formato de data incorreto, tente novamente!", console);
                                    }

                                    System.out.println("Insira a data de devolução (formato DD/MM/AAAA):");
                                    dataDevolucao = sc.nextLine();

                                    if(dataDevolucao.equals("@voltar")){
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    flagErro = true;
                                } while(validaData(dataDevolucao) != true);

                                if(flagErro == true && flagErro2 == true){
                                    flagErro = false;
                                    flagErro2 = false;
                                    break;
                                }

                                flagErro = false;
                                flagErro2 = false;

                                ttt = 0;

                                do{
                                    if(flagErro){
                                        imprimeErro("Veículo Inexistente, tente novamente!", console);
                                    }

                                    if(flagErro2){
                                        imprimeErro("Veículo Indisponível para o período solicitado!", console);
                                        System.out.println("(Precione enter para voltar ao menu principal)");
                                        sc.nextLine();
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    System.out.println("Insira a placa do veículo:");
                                    placa = sc.nextLine();

                                    if(placa.equals("@voltar")){
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    flagErro = true;

                                    ttt = validaVeiculo(dataRetirada, dataDevolucao, placa, agencias.get(codigoAgencia));

                                    if(ttt == 1){
                                        flagErro = true;
                                        flagErro2 = false;
                                    }else if(ttt == 2){
                                        flagErro = false;
                                        flagErro2 = true;
                                    }else{
                                        flagErro = false;
                                        flagErro2 = false;
                                    }

                                } while(ttt != 0);

                                if(flagErro == true && flagErro2 == true){
                                    flagErro = false;
                                    flagErro2 = false;
                                    break;
                                }

                                flagErro = false;

                                do{
                                    if(flagErro){
                                        imprimeErro("Cliente Inexistente, tente novamente!", console);
                                    }

                                    System.out.println("Insira o C.P.F. do cliente (formato ###.###.###-##):");
                                    cpf = sc.nextLine();

                                    if(cpf.equals("@voltar")){
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    flagErro = true;
                                } while(validaCliente(cpf, agencias.get(codigoAgencia)) != true);

                                if(flagErro == true && flagErro2 == true){
                                    flagErro = false;
                                    flagErro2 = false;
                                    break;
                                }

                                veiculo = agencias.get(codigoAgencia).getVeiculo(placa);
                                cliente = agencias.get(codigoAgencia).getCliente(cpf);
                                funcionario = agencias.get(codigoAgencia).getFuncionario(login);

                            {
                                try {
                                    valorTotal = agencias.get(codigoAgencia).realizaReserva(dataRetirada, dataDevolucao, veiculo, cliente, funcionario);
                                } catch (Exception ex) {
                                    System.err.println("Erro ao realizar reserva!");
                                    System.err.println(ex);
                                    break;
                                }
                            }

                                flagErro = false;
                                flagErro2 = false;

                                String sss = Double.toString(valorTotal);
                                sss = sss.substring(0, Math.min(sss.indexOf('.')+3, sss.length()));

                                if(sss.indexOf('.') == sss.length()-2){
                                    sss = sss.concat("0");
                                }

                                mensagemSucesso = "Reserva realizada com sucesso!\nValor total: " + sss;

                                break;

                            case 5:
                                if(console != null){
                                    clearScreen();
                                }
                                System.out.println("Retirada de Veículos");
                                System.out.println("(Para voltar digite @voltar e precione enter!)");
                                System.out.println();

                                placa = null;
                                cpf = null;

                                flagErro = false;
                                flagErro2 = false;

                                ttt = 0;

                                do{
                                    if(flagErro){
                                        imprimeErro("Veículo Inexistente, tente novamente!", console);
                                    }

                                    if(flagErro2){
                                        imprimeErro("Veículo Indisponível para o período solicitado!", console);
                                        System.out.println("(Precione enter para voltar ao menu principal)");
                                        sc.nextLine();
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    System.out.println("Insira a placa do veículo:");
                                    placa = sc.nextLine();

                                    if(placa.equals("@voltar")){
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    flagErro = true;

                                    ttt = validaVeiculo(placa, agencias.get(codigoAgencia));

                                    if(ttt == 1){
                                        flagErro = true;
                                        flagErro2 = false;
                                    }else if(ttt == 2){
                                        flagErro = false;
                                        flagErro2 = true;
                                    }else{
                                        flagErro = false;
                                        flagErro2 = false;
                                    }

                                } while(ttt != 0);

                                if(flagErro == true && flagErro2 == true){
                                    flagErro = false;
                                    flagErro2 = false;
                                    break;
                                }

                                flagErro = false;

                                do{
                                    if(flagErro){
                                        imprimeErro("Cliente Inexistente, tente novamente!", console);
                                    }

                                    System.out.println("Insira o C.P.F. do cliente (formato ###.###.###-##):");
                                    cpf = sc.nextLine();

                                    if(cpf.equals("@voltar")){
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    flagErro = true;
                                } while(validaCliente(cpf, agencias.get(codigoAgencia)) != true);

                                if(flagErro == true && flagErro2 == true){
                                    flagErro = false;
                                    flagErro2 = false;
                                    break;
                                }

                                reserva = Buscador.buscaReserva(placa, cpf, agencias.get(codigoAgencia));

                                if(reserva != null){

                                    if(agencias.get(codigoAgencia).getVeiculo(placa).getStatus() == 'A'){
                                        agencias.get(codigoAgencia).retiraVeiculo(placa);

                                        reserva.finalizaReserva();

                                        System.out.println("Retirada realizada com sucesso!");
                                    }else{
                                        imprimeErro("Este veículo não se encontra nesta agência neste momento!", console);
                                    }
                                }else{
                                    imprimeErro("Locação não encontrada!", console);
                                }

                                System.out.println("(Precione enter para retornar ao menu principal)");
                                sc.nextLine();

                                flagErro = false;
                                flagErro2 = false;

                                break;
                            case 6:
                                if(console != null){
                                    clearScreen();
                                }
                                System.out.println("Devolução de Veículos");
                                System.out.println("(Para voltar digite @voltar e precione enter!)");
                                System.out.println();

                                placa = null;

                                flagErro = false;
                                flagErro2 = false;

                                ttt = 0;

                                do{
                                    if(flagErro){
                                        imprimeErro("Veículo Inexistente, tente novamente!", console);
                                    }

                                    System.out.println("Insira a placa do veículo:");
                                    placa = sc.nextLine();

                                    if(placa.equals("@voltar")){
                                        flagErro = true;
                                        flagErro2 = true;
                                        break;
                                    }

                                    flagErro = true;

                                    ttt = validaVeiculo(placa, agencias.get(codigoAgencia));

                                    if(ttt == 1){
                                        flagErro = true;
                                    }else{
                                        flagErro = false;
                                    }

                                } while(ttt == 1);

                                if(flagErro == true && flagErro2 == true){
                                    flagErro = false;
                                    flagErro2 = false;
                                    break;
                                }

                                if(agencias.get(codigoAgencia).getVeiculo(placa).getStatus() == 'C'){
                                    agencias.get(codigoAgencia).devolveVeiculo(placa);

                                    System.out.println("Devolução realizada com sucesso!");
                                }else{
                                    imprimeErro("Este veículo se encontra nesta agência neste momento!", console);
                                }

                                System.out.println("(Precione enter para retornar ao menu principal)");
                                sc.nextLine();

                                flagErro = false;
                                flagErro2 = false;

                                break;
                            case 7:                            
                                if(console != null){
                                    clearScreen();
                                }

                                salvar(agencias);

                                login = null;

                                break;
                            case 8:
                                salvar(agencias);

                                System.out.println();
                                System.out.println("Até logo!");
                                System.exit(0);                            
                                break;
                            default:
                                flagErro = true;
                        }
                    }
                }else{
                    flagErro = true;
                }
            }
        }
    }
}
