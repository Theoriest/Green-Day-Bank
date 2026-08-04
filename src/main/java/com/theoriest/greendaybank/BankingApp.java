package com.theoriest.greendaybank;

public class BankingApp {

    // main class file
    public static void main(String[] args) {

    }
    // 1. Show balance method
    // 2. Deposit money method
    public interface Transactable{
        boolean deposit(double amount);
    }
    public static class CheckAccount implements Transactable{
        private double balance;

        @Override
        public boolean deposit(double amount) {
            if(amount < 0) return false;
            balance += amount;
            return true;
        }
    }


    // 3. Withdraw money method
    // 4. Send money to a person
    // 5. Invest in funds - Brian

    // 6. Transfer between accounts
    // 7. Withdraw all investments
    // 8. Logout - Brian
    // 9. Exit
}
