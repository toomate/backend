package com.toomate.backend.enums;

public enum StatusVencimento {
    VENCIDO {
        @Override
        public String getLabel(){
            return "Vencido";
        }
    },

    VENCE_LOGO {
        @Override
        public String getLabel(){
            return "Vence Logo";
        }
    },

    NO_PRAZO {
        @Override
        public String getLabel(){
            return "No Prazo";
        }
    };

    public abstract String getLabel();
}
