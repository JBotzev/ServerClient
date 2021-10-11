import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode;

public class CipherSample {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String id;
    private String password;
    private String encrId;
    private String encrPass;
    private static final String PRIVATE_KEY_STR_PASS = "asd";
    private static final String PUBLIC_KEY_STR_ID = "asd";

    public CipherSample() {
    }

    public CipherSample(String id, String password) throws Exception {
        System.out.println(id + " " + password);
        this.id = id;
        this.password = password;
        System.out.println("TESTTTTTTTTTTt");
        encrypt();
    }
    public void encrypt() throws Exception {
        System.out.println("ENCRYPTIIIING");
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generating the pair of keys
        KeyPair pair1 = keyPairGen.generateKeyPair();
        KeyPair pair2 = keyPairGen.generateKeyPair();

        publicKey = pair2.getPublic();
        privateKey = pair2.getPrivate();



        //Creating a Cipher object
        Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        Cipher cipher2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //Initializing a Cipher object
        cipher1.init(Cipher.ENCRYPT_MODE, pair1.getPublic());
        cipher2.init(Cipher.ENCRYPT_MODE, pair2.getPublic());

        //Adding data to the cipher
        byte[] idBytes = this.id.getBytes();
        byte[] passwordBytes = this.password.getBytes();
        cipher1.update(idBytes);
        cipher2.update(passwordBytes);

        //encrypting the data
        byte[] cipherText1 = cipher1.doFinal();
        byte[] cipherText2 = cipher2.doFinal();

        encrId = new String(cipherText1, "UTF8");
        encrPass = new String(cipherText2, "UTF8");
        System.out.println(new String(cipherText1, "UTF8"));
        System.out.println(new String(cipherText2, "UTF8"));

//        //Initializing the same cipher for decryption
//        cipher1.init(Cipher.DECRYPT_MODE, pair1.getPrivate());
//        cipher2.init(Cipher.DECRYPT_MODE, pair2.getPrivate());
//
//        //Decrypting the text
//        byte[] decipheredText1 = cipher1.doFinal(cipherText1);
//        byte[] decipheredText2 = cipher2.doFinal(cipherText2);
//        System.out.println(new String(decipheredText1));
//        System.out.println(new String(decipheredText2));
    }

    public void initFromStrings(){
        try{
            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY_STR_ID));
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(PRIVATE_KEY_STR_PASS));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpecPublic);
            privateKey = keyFactory.generatePrivate(keySpecPrivate);
        }catch (Exception ignored){}
    }

    public String getEncrId(){
        return encrId;
    }

    public String getEncrPass(){
        return encrPass;
    }



}