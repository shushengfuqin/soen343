package org.team4.common;


import java.io.IOException;

public class LogException extends Exception {
    public logException(String exp,Throwable throwable){
        super(exp,throwable);
    }
    public logException(String exp){
        super(exp);
    }

}
