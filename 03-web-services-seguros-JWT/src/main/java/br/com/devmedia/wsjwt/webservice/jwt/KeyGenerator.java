package br.com.devmedia.wsjwt.webservice.jwt;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class KeyGenerator {

    public Key generateKey() {
        String keyString = "GyhHEYyLLJUKJeABcqo0YO+QUd6ERarphT2go9S2i94=";  //”DevMedia” após SHA-256 e EncodeBase64;;

        Key key = new SecretKeySpec(keyString.getBytes(),
                0, keyString.getBytes().length, "HmacSHA256");
        return key;
    }

}