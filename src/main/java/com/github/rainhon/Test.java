package com.github.rainhon;

public class Test {
    public static void main(String[] args){
        String s = "大家发牢骚<left-time>sdfdfsdf";
        System.out.println(s.replaceAll("<left-time>", "123"));
    }
}
