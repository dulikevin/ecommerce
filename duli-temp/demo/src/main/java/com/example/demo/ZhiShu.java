package com.example.demo;

import java.util.Scanner;
class Main{

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        long ulDataInput = scanner.nextLong();
        System.out.println(getResult(ulDataInput));

        System.out.println(getResult(ulDataInput));
        System.out.println("hello,myboy");
        System.out.println(getResult(ulDataInput));
        System.out.println("hello,myboy");

    }

    public static String getResult(long ulDataInput){
        StringBuilder result = new StringBuilder("");
        for (long i=2;i<=ulDataInput;i++){
            if(ulDataInput % i ==0){
                result.append(i+" ");
                ulDataInput = ulDataInput/i;
                i=1;
            }
        }
        return result.toString();
    }
}
