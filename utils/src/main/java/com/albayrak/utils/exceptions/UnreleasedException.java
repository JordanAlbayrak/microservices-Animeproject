package com.albayrak.utils.exceptions;

public class UnreleasedException extends RuntimeException{
        public UnreleasedException() {}
        public UnreleasedException(String message){super(message);}
        public UnreleasedException(Throwable cause) {super(cause);}
        public UnreleasedException(String message, Throwable cause) {super(message, cause);}

    }

