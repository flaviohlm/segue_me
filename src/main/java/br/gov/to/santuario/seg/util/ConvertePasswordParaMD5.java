package br.gov.to.santuario.seg.util;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author whelmison.rodrigues
 */
 
public class ConvertePasswordParaMD5 {
 
    public static String convertePasswordParaMD5(String password) throws NoSuchAlgorithmException {        
        MessageDigest md = MessageDigest.getInstance("MD5");
 
        BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
         
        return String.format("%32x", hash);
    }
 
}