package org.team4.common.exceptions;

public class InvalidPermissionFileException extends Exception{
    public InvalidPermissionFileException(){
        super("Permission file not found");
    }
}
