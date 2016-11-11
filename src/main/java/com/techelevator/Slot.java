package com.techelevator;

public class Slot {
	
	private String slotNumber;
	private String foodItem;
	private double price;
	private int count;
	
	public Slot(String slotNumber, String foodItem, double price)   {
		this.slotNumber = slotNumber;
		this.foodItem = foodItem;
		this.price = price;
		this.count = 5;
	}
	

	public String getSlotNumber() {
		return slotNumber;
	}

	public String getFoodItem() {
		return foodItem;
	}

	public double getPrice() {
		return price;
	}

	public int getCount() {
		return count;
	}

	public boolean decrementCount() {
		if (count >0) {
			count --;
			return true;
		} else 
			return false;
	}
	
}
