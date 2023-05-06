package com.treasure_hunter_java.dictionary;

public class Password {

    private final int length;

    private int countUsed = 1;

    private final String password;

    public String getPassword(){
        return password;
    }

    public void increaseCountUsed(){
        this.countUsed += 1;
    }

    public int getLength() {
        return length;
    }

    public int getCountUsed() {return countUsed;}

    Password(String password) {
        this.password = password;
        this.length = password.length();
    }

}
