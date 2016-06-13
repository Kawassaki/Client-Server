/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfinal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.lang.NullPointerException;
import javax.swing.JOptionPane;

public class Cliente {

    private String host;
    private int porta;
    private String usuario;
    private String senha;
    private String loginusuario;
    private String loginsenha;
    String dados;
    Socket cliente;
    PrintStream saida;

    public Cliente(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void executa() throws UnknownHostException, IOException {
        Socket cliente = new Socket(this.host, this.porta);
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------ Trabalho de Redes - Felipe Kawassaki, Matheus Nagata! ------------------");
        System.out.println("-------------------------------------- Autenticação ---------------------------------------");
        System.out.println("Digite o usuário: ");
        setLoginusuario(sc.nextLine());
        System.out.println("Digite a senha: ");
        setLoginsenha(sc.nextLine());
        AutenticacaoServidor();

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

        // lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }

        saida.close();
        teclado.close();
        cliente.close();
    }

    public void AutenticacaoServidor() throws FileNotFoundException {
        Scanner arquivo = new Scanner(new FileReader("C:/Users/Unisistemas/Documents/NetBeansProjects/arquivo.txt")).useDelimiter(",");
        String usuarioarq = "";
        String senhaarq = "";
        boolean conectado = false;
        while (arquivo.hasNext()) {

            String linha = arquivo.nextLine();
            String[] posicoes = linha.split(",");
            usuarioarq = posicoes[0];
            senhaarq = posicoes[1];

            if (usuarioarq.equals(getLoginusuario()) && senhaarq.equals(getLoginsenha())) {
                System.out.println("Usuário Conectado!");
                System.out.println("-------------------------------------------------------------------------------------------");
                System.out.println("Digite seu comando: ");
                conectado = true;
                break;
            }
        }
        if (!conectado) {
            System.out.println("Usuário não encontrado!");
            System.out.println("-------------------------------------------------------------------------------------------");
            System.exit(0);
        }
    }

    public String getLoginusuario() {
        return loginusuario;
    }

    public void setLoginusuario(String loginusuario) {
        this.loginusuario = loginusuario;
    }

    public String getLoginsenha() {
        return loginsenha;
    }

    public void setLoginsenha(String loginsenha) {
        this.loginsenha = loginsenha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
