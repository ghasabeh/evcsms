package devolon.fi.evcsms.utils.exception;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public class CycleFoundInTreeException extends RuntimeException{
    public CycleFoundInTreeException() {
        super("Illegal Action. Making Cycle in company hierarchy is forbidden!");
    }
}
