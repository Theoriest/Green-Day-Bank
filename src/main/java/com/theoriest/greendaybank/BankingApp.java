package com.theoriest.greendaybank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract class User {
    protected String name;
    protected BigDecimal cash;
    protected BigDecimal savingsBalance;
    protected BigDecimal investmentBalance;
    protected Map<String, BigDecimal> funds;

    public User(String name) {
        this.name = name;
        this.cash = new BigDecimal("1000.00");
        this.savingsBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.investmentBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.funds = new HashMap<>();
        this.funds.put("LOW_RISK", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        this.funds.put("MEDIUM_RISK", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        this.funds.put("HIGH_RISK", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsBalance(BigDecimal savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public BigDecimal getInvestmentBalance() {
        return investmentBalance;
    }

    public void setInvestmentBalance(BigDecimal investmentBalance) {
        this.investmentBalance = investmentBalance;
    }

    public Map<String, BigDecimal> getFunds() {
        return funds;
    }
}

class Customer extends User {
    public Customer(String name) {
        super(name);
    }
}

public class BankingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, User> users = new HashMap<>();
        
        // Initialize the 4 required users
        users.put("Alice", new Customer("Alice"));
        users.put("Bob", new Customer("Bob"));
        users.put("Charlie", new Customer("Charlie"));
        users.put("Diana", new Customer("Diana"));

        boolean running = true;

        while (running) {
            // Login State
            // Ask for the user who wants to login
            System.out.println("Enter your name to log in: ");

            User currentUser = null;
            while (currentUser == null) {
                if (!scanner.hasNextLine()) {
                    running = false;
                    break;
                }
                String input = scanner.nextLine().trim();
                if (users.containsKey(input)) {
                    currentUser = users.get(input);
                    // Print welcome message for the current user.
                    System.out.println("Welcome " + input);
                } else {
                    // Handle invalid username gracefully
                    System.out.println("User not found. Please try again.");
                }
            }

            if (!running) {
                break;
            }

            // Session Active Loop
            boolean sessionActive = true;
            while (sessionActive) {
                printMenu();
                if (!scanner.hasNextLine()) {
                    sessionActive = false;
                    running = false;
                    break;
                }
                String choiceStr = scanner.nextLine().trim();
                int choice;
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                switch (choice) {
                    case 1:
                        // Show balance & apply interest/gains
                        applyInterestAndGains(currentUser);
                        showBalance(currentUser);
                        break;
                    case 2:
                        // Deposit money (Cash -> Savings)
                        depositMoney(currentUser, scanner);
                        break;
                    case 3:
                        // Withdraw money (Savings -> Cash)
                        System.out.println(withdrawMoney(currentUser, scanner));
                        System.out.println(); // Adds a blank line after the operation completes
                        break;
                    case 4:
                        // Send money to a person
                        System.out.println(sendMoney(currentUser, users, scanner));
                        break;
                    case 5:
                        // Invest in funds
                        investInFunds(currentUser, scanner);
                        break;
                    case 6:
                        // Transfer between accounts (Savings <-> Investment)
                        System.out.println(transferBetweenAccounts(currentUser, scanner));
                        break;
                    case 7:
                        // Withdraw all investments
                        System.out.println(withdrawAllInvestments(currentUser));
                        break;
                    case 8:
                        // Logout
                        sessionActive = false;
                        break;
                    case 9:
                        // Exit (Gracefully without System.exit)
                        sessionActive = false;
                        running = false;
                        System.out.println("\nThank you for using our Green Day Banking app. Bye!");
                        System.out.println();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("");
        System.out.println("\n --- Banking App Menu ---");
        System.out.println("1. Show balance");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Send money to a person");
        System.out.println("5. Invest in funds");
        System.out.println("6. Transfer between accounts");
        System.out.println("7. Withdraw all investments");
        System.out.println("8. Logout");
        System.out.println("9. Exit");
    }

    private static void applyInterestAndGains(User user) {
        // Savings 1% interest
        BigDecimal savingsInterest = user.getSavingsBalance().multiply(new BigDecimal("0.01"));
        user.setSavingsBalance(user.getSavingsBalance().add(savingsInterest).setScale(2, RoundingMode.HALF_UP));

        // Funds appreciation: LOW_RISK (2%), MEDIUM_RISK (5%), HIGH_RISK (10%)
        Map<String, BigDecimal> funds = user.getFunds();
        funds.put("LOW_RISK", funds.get("LOW_RISK").multiply(new BigDecimal("1.02")).setScale(2, RoundingMode.HALF_UP));
        funds.put("MEDIUM_RISK", funds.get("MEDIUM_RISK").multiply(new BigDecimal("1.05")).setScale(2, RoundingMode.HALF_UP));
        funds.put("HIGH_RISK", funds.get("HIGH_RISK").multiply(new BigDecimal("1.10")).setScale(2, RoundingMode.HALF_UP));
    }

    private static void showBalance(User user) {
        System.out.println("Cash: $" + user.getCash());
        System.out.println("Savings: $" + user.getSavingsBalance());
        System.out.println("Investment: $" + user.getInvestmentBalance());
        System.out.println("Low Risk Fund: $" + user.getFunds().get("LOW_RISK"));
        System.out.println("Medium Risk Fund: $" + user.getFunds().get("MEDIUM_RISK"));
        System.out.println("High Risk Fund: $" + user.getFunds().get("HIGH_RISK"));
    }

    private static void depositMoney(User user, Scanner scanner) {
        if (!scanner.hasNextLine()) return;
        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            if (amount.compareTo(BigDecimal.ZERO) > 0 && user.getCash().compareTo(amount) >= 0) {
                user.setCash(user.getCash().subtract(amount));
                user.setSavingsBalance(user.getSavingsBalance().add(amount));
            }
        } catch (NumberFormatException ignored) {}
    }

   private static String withdrawMoney(User user, Scanner scanner) {
    System.out.print("Enter the amount to withdraw from your savings account: ");

    if (!scanner.hasNextLine()) {
        return "\nAmount to withdraw can not be empty";
    }

    try {
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
        if (amount.compareTo(BigDecimal.ZERO) > 0 && user.getSavingsBalance().compareTo(amount) >= 0) {
            user.setSavingsBalance(user.getSavingsBalance().subtract(amount));
            user.setCash(user.getCash().add(amount));
            return "\nWithdrawal successful";
        } else {
            return "\nAmount to withdraw must be equal or less than balance";
        }
    } catch (NumberFormatException ignored) {
        return "\nInvalid input format";
    }
}

    private static String sendMoney(User user, Map<String, User> users, Scanner scanner) {
        System.out.println("\n The following are registered users :");

        //print out available users
        for(String registered : users.keySet()){
            System.out.print(" " + registered + "\t");
        }

        // ask for recipient and store the value
        System.out.print("\n Which registered user would you like to send the money to : ");

        // validate recipient
        if (!scanner.hasNextLine()) return " Recipient can not be blank";
        String recipientName = scanner.nextLine().trim();

        if (recipientName.isEmpty()) return " Recipient can not be blank";

        if (!users.containsKey(recipientName)) return " User not found. You can only send money to registered users";

        if (users.get(recipientName) == user)return " You can not send yourself money from your own account";

        // Ask for amount and store the value
        System.out.print("\n How much would you like to send to " + recipientName + ": ");
        if (!scanner.hasNextLine()) return " Amount can not be empty";
        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            if (amount.compareTo(BigDecimal.ZERO) > 0 && user.getSavingsBalance().compareTo(amount) >= 0) {
                user.setSavingsBalance(user.getSavingsBalance().subtract(amount));
                User recipient = users.get(recipientName);
                recipient.setSavingsBalance(recipient.getSavingsBalance().add(amount));
                return " " + amount + " sent to " + recipientName;
            }
            else{
                return " User can not send an amount greater than the balance in their savings account";
            }
        } catch (NumberFormatException ignored) {
            return " Amount can only be a number ";
        }
    }

    private static void investInFunds(User user, Scanner scanner) {
        if (!scanner.hasNextLine()) return;
        String fundType = scanner.nextLine().trim();
        if (!user.getFunds().containsKey(fundType)) return;

        if (!scanner.hasNextLine()) return;
        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            if (amount.compareTo(BigDecimal.ZERO) > 0 && user.getInvestmentBalance().compareTo(amount) >= 0) {
                user.setInvestmentBalance(user.getInvestmentBalance().subtract(amount));
                user.getFunds().put(fundType, user.getFunds().get(fundType).add(amount));
            }
        } catch (NumberFormatException ignored) {}
    }

    private static String transferBetweenAccounts(User user, Scanner scanner) {
        System.out.println("1. Transfer from savings to investment");
        System.out.println("2. Transfer from investment to savings");
        System.out.println("Enter your choice: ");

        if (!scanner.hasNextLine()) return "";
        String choice = scanner.nextLine().trim();

        System.out.print("Enter the amount to transfer: ");

        if (!scanner.hasNextLine()) return "";
        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

            if (choice.equals("1")) {
            if (amount.compareTo(BigDecimal.ZERO) > 0 && user.getSavingsBalance().compareTo(amount) >= 0) {
                user.setSavingsBalance(user.getSavingsBalance().subtract(amount));
                user.setInvestmentBalance(user.getInvestmentBalance().add(amount));
                return "\nYou have successfully transferred " + amount + " to investment account.";
            } else {
                return "\nInsufficient funds in savings account.";
            }
        } else if (choice.equals("2")) {
            if (amount.compareTo(BigDecimal.ZERO) > 0 && user.getInvestmentBalance().compareTo(amount) >= 0) {
                user.setInvestmentBalance(user.getInvestmentBalance().subtract(amount));
                user.setSavingsBalance(user.getSavingsBalance().add(amount));
                return "\nYou have successfully transferred " + amount + " to savings account.";
            } else {
                return "\nInsufficient funds in investment account. Check balance and try again. ";
            }
        } else {
            return "\nInvalid choice selection.";
        }
    } catch (NumberFormatException e) {
        return "\nInvalid input format.";
    }
    }

    private static String withdrawAllInvestments(User user) {
        BigDecimal totalFunds = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : user.getFunds().entrySet()) {
            totalFunds = totalFunds.add(entry.getValue());
            entry.setValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        }
        user.setInvestmentBalance(user.getInvestmentBalance().add(totalFunds));

        return "All investments totaling $" + totalFunds.setScale(2, RoundingMode.HALF_UP) + " were deposited in your investment account.";
    }
}