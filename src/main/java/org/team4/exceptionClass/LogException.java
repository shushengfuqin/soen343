package org.team4.exceptionClass;


import java.io.IOException;

public class LogException extends Exception {
    public LogException(String exp, Throwable throwable){
        super(exp,throwable);
    }
    public LogException(String exp){
        super(exp);
    }
    public LogException(){super("Invalid Logger information, Please enter the valid information.");}

}
