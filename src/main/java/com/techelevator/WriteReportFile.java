package com.techelevator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WriteReportFile {

	public static void printTimeStampedSalesReport(Map<String,Slot> slotMap, Map<String,Integer> salesMap) {
		File transactionLogFile = new File("Vendo-Matic-Sales-" + LocalDateTime.now() + ".txt");
		double totalSales = 0;
		try (FileWriter fw = new FileWriter(transactionLogFile, true);
				BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write("Sales Report");
			bw.newLine();
			bw.newLine();
			Set<String> keySet = salesMap.keySet();
			for (String key : keySet) {
				totalSales += salesMap.get(key)*slotMap.get(key).getPrice();
				
				String formatLine = String.format("%-20s %s", slotMap.get(key).getFoodItem(), salesMap.get(key));
				bw.write(formatLine);
				bw.newLine();
			}
			bw.newLine();
			bw.write("** TOTAL SALES ** $" + totalSales);
		}
		catch (Exception e) {
			System.out.println("FAILED!! " + e.getMessage());
		}
	}
	
	public static void printTransactionLog(String slot, String product, double price) {
		File transactionLogFile = new File("TransactionLog.txt");
		boolean fileExists = transactionLogFile.exists();
		try (FileWriter fw = new FileWriter(transactionLogFile, true);
			BufferedWriter bw = new BufferedWriter(fw)) {
			if (!fileExists) {
				bw.write(String.format("%-20s %-20s %-4s %-5s", "DateTime", "Product", "Slot", "Price"));
				bw.newLine();
			}
			
			
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String line = String.format("%-20s %-20s %-4s $%-5.2f", dateFormat.format(date),product,slot,price);
			bw.write(line);
			bw.newLine();
		}
		catch (Exception e) {
			System.out.println("FAILED!! " + e.getMessage());
		}
	}
	
	public static Map<String,Integer> readSalesReportFile() {
		File inputFile = new File("SalesReportTracking.csv");
		Map<String,Integer> salesMap = new HashMap<String,Integer>();
		try (FileReader fr = new FileReader(inputFile);
				BufferedReader br = new BufferedReader(fr);
				) {
			String line;
			while ((line = br.readLine())!=null) {
				String[] lineArray = line.split("\\|");
				salesMap.put(lineArray[0],Integer.parseInt(lineArray[1]));
			}
		}
		catch (Exception e) {
			System.out.println("FAILED!! " + e.getMessage());
		}
		return salesMap;
	}
	
	public static void writeSalesReportFile(Map<String,Integer> salesMap) {
		File transactionLogFile = new File("SalesReportTracking.csv");
		if (transactionLogFile.exists()) {
			transactionLogFile.delete();
		}
		try (FileWriter fw = new FileWriter(transactionLogFile, true);
			BufferedWriter bw = new BufferedWriter(fw)) {
			transactionLogFile.createNewFile();
			Set<String> keySet = salesMap.keySet();
			for (String key : keySet) {
				bw.write(key + "|" + salesMap.get(key));
				bw.newLine();
			}
		}
		catch (Exception e) {
			System.out.println("FAILED!! " + e.getMessage());
		}
	}
}
