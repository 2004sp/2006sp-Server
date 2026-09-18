package com.rs2.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeyGen {
    public static void main(String[] args) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            RSAKeyGen.writeRsaConstantsFile("./data/rsapriv", privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAKeyGen.writeRsaConstantsFile("./data/rsapub", publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void writeRsaConstantsFile(String path, BigInteger modulus, BigInteger exponent) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("private static final BigInteger RSA_MODULUS = new BigInteger(\"" + modulus.toString() + "\");");
            writer.newLine();
            writer.newLine();
            writer.write("private static final BigInteger RSA_EXPONENT = new BigInteger(\"" + exponent.toString() + "\");");
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
