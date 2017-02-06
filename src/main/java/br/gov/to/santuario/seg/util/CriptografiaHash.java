/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.santuario.seg.util;

import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author alex.santos
 */
public class CriptografiaHash implements Serializable {

    static public String codificar(String valor) {
        return Base64.encodeBase64String(valor.getBytes());
    }

    static public String decodificar(String valor) {
        byte[] decoded = Base64.decodeBase64(valor.getBytes());
        String decodedString = new String(decoded);
        return decodedString;
    }
}
