/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfinal;

import java.io.IOException;
import java.net.UnknownHostException;
import java.lang.NullPointerException;

/**
 *
 * @author alunos
 */
public class SocketFinal {

 public static void main(String[] args) 
         throws UnknownHostException, IOException {
     // dispara cliente
     new Cliente("127.0.0.1", 12345).executa();
     
   }
}
