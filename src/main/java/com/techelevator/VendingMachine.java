package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {

	private Map<String,Slot> slotMap;
	private double totalBalance;
	private List<String> printList;
	private Map<String,Integer> salesMap;
	
	public VendingMachine() {
		slotMap = new HashMap<String,Slot>();
		totalBalance = 0;
		printList = new ArrayList<String>();
		this.salesMap = WriteReportFile.readSalesReportFile();
		this.stockMachine();
	}
	
	
	private void stockMachine() {
		// read from vendingmachine.csv and stock slotMap
		File inputFile = new File("vendingmachine.csv");
		try (FileReader fr = new FileReader(inputFile);
				BufferedReader br = new BufferedReader(fr);
				) {
			String line;
			while ((line = br.readLine())!=null) {
				String[] lineArray = line.split("\\|");
				printList.add(lineArray[0]);
				String slotNumber = lineArray[0];
				String foodItem = lineArray[1];
				double price = Double.parseDouble(lineArray[2]);
				slotMap.put(slotNumber, new Slot(slotNumber, foodItem, price));
			}
		}
		catch (Exception e) {
			System.out.println("FAILED!! " + e.getMessage());
		}
	}
	
	public String getItemName(String slotNum) {
		return slotMap.get(slotNum).getFoodItem();
	}
	
	public double getItemPrice(String slotNum) {
		return slotMap.get(slotNum).getPrice();
	}
	
	public boolean isItem(String slot) {
		return slotMap.containsKey(slot);
	}
	
	public void insertMoney(int amount) {
		totalBalance += amount;
	}
	
	/*
	 * Returns true if enough money given for item
	 * false otherwise
	 */
	public boolean haveEnoughMoney(String slot) {
		if (slotMap.get(slot).getPrice()<totalBalance) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Returns true if more than 0 of product in slot
	 * false otherwise
	 */
	public boolean isAvailable(String slot) {
		if (slotMap.get(slot).getCount()>0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Makes a purchase if valid slot option, enough money has been given, and there are more of the product available
	 * Writes purchase to TransactionLog.txt and updates the salesMap
	 */
	public boolean makePurchase(String slot) {
		if (isItem(slot) && haveEnoughMoney(slot) && isAvailable(slot)) {
			slotMap.get(slot).decrementCount();
			totalBalance -= slotMap.get(slot).getPrice();
			
			// write to TransactionLog.txt
			WriteReportFile.printTransactionLog(slotMap.get(slot).getSlotNumber(), slotMap.get(slot).getFoodItem(),slotMap.get(slot).getPrice());
			//update salesMap
			salesMap.put(slot, salesMap.get(slot)+1);
			
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Calculates change and only prints the number of quarters/nickels/dimes if more than 0 are returned
	 * writes updated sales report information to SalesReportTracking.csv
	 */
	public String endTransaction() {
		String change = "Change returned: $" + String.format("%.2f", totalBalance);
		int numberOfDimes = 0;
		int numberOfNickels = 0;
		int numberOfQuarters = 0;
		
		while (totalBalance/0.25>0) {
			totalBalance-=0.25;
			numberOfQuarters++;
		}
		if (numberOfQuarters>0) {
			change += "\nNumber of Quarters: " + numberOfQuarters;
		}
		
		while (totalBalance/0.10>0) {
			totalBalance-=0.10;
			numberOfDimes++;
		}
		if (numberOfDimes>0) {
			change += "\nNumber of Dimes: " + numberOfDimes;
		}
		
		while (totalBalance/0.5>0) {
			totalBalance-=0.5;
			numberOfNickels++;
		}
		if (numberOfNickels>0) {
			change += "\nNumber of Nickels: " + numberOfNickels;
		}
		
		WriteReportFile.writeSalesReportFile(salesMap);
		return change;
	}
	
	/*
	 * displays vending machine contents with slot number, food name, price, and quantity remaining
	 * if there are no more left, quantity reads "SOLD OUT"
	 */
	public String displayItems() {
		String returnString = "";
		for (String slot : printList) {
			String foodName = slotMap.get(slot).getFoodItem();
			double price = slotMap.get(slot).getPrice();
			String quantity;
			if (slotMap.get(slot).getCount()>0) {
				quantity = slotMap.get(slot).getCount() + " QTY";
			} else {
				quantity = "SOLD OUT";
			}
			returnString += String.format("%s | %-20s | $%-5.2f | %-10s%n", slot, foodName, price, quantity);
		}
		return returnString;
	}
	
	public double getTotalBalance() {
		return totalBalance;
	}
	
	public void getSalesReport() {
		WriteReportFile.printTimeStampedSalesReport(slotMap,salesMap);
	}
}
