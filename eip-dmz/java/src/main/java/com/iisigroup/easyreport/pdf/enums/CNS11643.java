package com.iisigroup.easyreport.pdf.enums;

public enum CNS11643 {
    
    SUNG("SUNG"), 
    KAI("KAI");

    private final String name;

    private CNS11643(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
