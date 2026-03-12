package com.FinancialTransactionProcessor.prartice;

public class ReverseString {
    public static void main(String[] args) {
        String str = "Capgemini";
        String reversed = new StringBuilder(str).reverse().toString();
        System.out.println(reversed);
    }
}
