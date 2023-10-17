package br.gov.mt.mti.siga.assinador.tools;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * Utilitário para conversão de Hashs
 *
 * @author Ricardo Santos
 */
public class HashTools {

    @SneakyThrows
    public static byte[] toSha1(byte[] content) {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        md.update(content);
        byte[] output = md.digest();
        return output;
    }

    @SneakyThrows
    public static byte[] toSha256(byte[] content) {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(content);
        byte[] output = md.digest();
        return output;
    }

    public static String toBase64Encode(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    public static byte[] toBase64Decode(String content) {
        return Base64.getDecoder().decode(content);
    }
}
