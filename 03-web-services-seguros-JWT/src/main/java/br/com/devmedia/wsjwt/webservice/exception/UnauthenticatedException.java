package br.com.devmedia.wsjwt.webservice.exception;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException(String s) {
        super(s);
    }


}