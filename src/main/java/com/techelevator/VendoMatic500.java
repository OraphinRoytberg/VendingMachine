package com.techelevator;

import java.util.Scanner;

public class VendoMatic500 {
	
	public static void main(String [] args) {
		
		VendingMachine vendo = new VendingMachine();
		boolean isFinished = false;
		while (!isFinished) {
			System.out.println("*************************");
			System.out.println("\tMAIN MENU");
			System.out.println("*************************");
			System.out.println("(1) Display Vending Machine Items");
			System.out.println("(2) Purchase");
			System.out.print(">>> ");
			
			Scanner scan = new Scanner(System.in);
			String userInput = scan.nextLine();
			
			if (userInput.equals("0")) {
				vendo.getSalesReport();
				System.out.println("Sales Report Printed.");
				System.exit(1);
			} else if (userInput.equals("1")) {
				System.out.println();
				System.out.println(vendo.displayItems());
			} else if (userInput.equals("2")) {
				isFinished = true;	
			} else {
				System.out.println("Not A Valid Option! Please Try Again.");
			}
		}
		isFinished = false;
		while (!isFinished) {
			System.out.println();
			System.out.println("******************************");
			System.out.println("\tPURCHASE MENU");
			System.out.println("******************************");
			System.out.println("(1) Feed Money");
			System.out.println("(2) Select Product");
			System.out.println("(3) Finish Transaction");
			System.out.println("------------------------------");
			System.out.printf("Current Money Provided: $%.2f\n", vendo.getTotalBalance());
			System.out.println("------------------------------");
			System.out.print(">>> ");
			
			Scanner scan = new Scanner(System.in);
			String userInput = scan.nextLine();
			
			if (userInput.equals("1")) {
				System.out.println();
				System.out.println("How Many Dollars Do You Want To Insert?");
				System.out.print(">>> $");
				userInput = scan.nextLine();
				if (isDigit(userInput)) {
					vendo.insertMoney(Integer.parseInt(userInput));
				} else {
					System.out.println("Sorry, Only Whole Dollar Amounts Allowed.");
				}
			} else if (userInput.equals("2")) {
				System.out.println();
				System.out.println(vendo.displayItems());
				System.out.println("------------------------------");
				System.out.println("Please Select Slot Number");
				System.out.print(">>> ");
				userInput = scan.nextLine().toUpperCase();
				if (!vendo.isItem(userInput)) {
					System.out.println("Not A Valid Option! Please Try Again.");
				} else if (vendo.makePurchase(userInput)) {
					System.out.println("Enjoy your " + vendo.getItemName(userInput) + "!");
				} else if (!vendo.isAvailable(userInput)) {
					System.out.println("Sorry, We're All Out of " + vendo.getItemName(userInput) + "!");
					System.out.println("Please Select Another Item.");
				} else if (!vendo.haveEnoughMoney(userInput)) {
					System.out.println("Looks Like You're Short!");
					System.out.println("Please Add $" + String.format("%.2f", (vendo.getItemPrice(userInput)-vendo.getTotalBalance())) + " To Purchase " + vendo.getItemName(userInput) + ".");
				}
			} else if (userInput.equals("3")) {
				System.out.println(vendo.endTransaction());
				isFinished = true;
				System.out.println("Goodbye!");
			} else {
				System.out.println("Not A Valid Option! Please Try Again.");
			}
			
		}
	
	}
	
	private static boolean isDigit(String s) {
		for (int i=0; i<s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
