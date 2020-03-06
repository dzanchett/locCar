/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadoracarros;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class Crypto {
    
    //método static responsável por gerar um hash de uma String através do algoritmo MD5
    public static String MD5(String s){
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            return s;
        }
    }
    
}
