package com.projectapp.config;

public class CustomExceptions {

    public static class VeiculoException extends RuntimeException {
        public VeiculoException(String message) {
            super(message);
        }
    }
    public static class PneuException extends RuntimeException {
        public PneuException(String message) {
            super(message);
        }
    }
    public static class VeiculoPneuException extends RuntimeException {
        public VeiculoPneuException(String message) {
            super(message);
        }
    }

}
