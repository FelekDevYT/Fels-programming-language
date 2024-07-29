package net.felsstudio.fels.exceptions;

public class PanicException extends RuntimeException{
    public PanicException(String message){
        super("Panic: "+message);
    }
}
