package com.treasure_hunter_java.dictionary;

public class Password {

    private final int length;

    private int countUsed = 1;

    private final String password;

    public String getPassword(){
        return password;
    }

    public int getCountUsed(){
        return countUsed;
    }

    public void increaseCountUsed(){
        this.countUsed += 1;
    }

    public int getLength() {
        return length;
    }

    Password(String password) {
        this.password = password;
        this.length = password.length();
    }

}
