package be.ucll.exceptions;

public class ParameterInvalidException extends Exception{
    public ParameterInvalidException(String p) {
        super(p + " is not valid!");
    }

    public ParameterInvalidException(){
        super("This parameter may not be empty");
    }


}
