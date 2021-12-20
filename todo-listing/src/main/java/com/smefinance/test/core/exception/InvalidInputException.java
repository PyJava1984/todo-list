package com.smefinance.test.core.exception;

import java.util.*;
public final class InvalidInputException extends Exception {

    /**  Add a new error message to this exception. */
    public void add(String aErrorMessage){
        fErrorMessages.add(aErrorMessage);
    }

    /** Return an unmodfiable list of error messages. */
    public List<String> getErrorMessages(){
        return Collections.unmodifiableList(fErrorMessages);
    }

    /** Return <tt>true</tt> only if {@link #add(String)} has been called at least once.*/
    public boolean hasErrors(){
        return ! fErrorMessages.isEmpty();
    }

    // PRIVATE
    private List<String> fErrorMessages = new ArrayList<>();
}
