package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

public class VendingMachineTest {

	@Test
	public void test_constructor() {
		VendingMachine vm = new VendingMachine();
		assertEquals(0, vm.getTotalBalance(), 0);
		vm.displayItems();
	}
	
	@Test
	public void add_money_check_balance() {
		VendingMachine vm = new VendingMachine();
		vm.insertMoney(5);
		assertEquals(5, vm.getTotalBalance(), 0);
		vm.insertMoney(2);
		assertEquals(7, vm.getTotalBalance(), 0);
	}
	
	@Test
	public void add_money_buy_product() {
		VendingMachine vm = new VendingMachine();
		vm.insertMoney(5);
		assertEquals(5d, vm.getTotalBalance(), 0);
		assertTrue(vm.makePurchase("C3"));
		assertEquals(3.50,vm.getTotalBalance(), 0);
	}
	
	@Test
	public void buy_five_attempt_to_buy_a_sixth() {
		VendingMachine vm = new VendingMachine();
		vm.insertMoney(8);
		assertTrue(vm.makePurchase("C3"));
		assertTrue(vm.makePurchase("C3"));
		assertTrue(vm.makePurchase("C3"));
		assertTrue(vm.makePurchase("C3"));
		assertTrue(vm.makePurchase("C3"));
		assertFalse(vm.makePurchase("C3"));
		assertFalse(vm.haveEnoughMoney("C3"));
		assertFalse(vm.isAvailable("C3"));
	}
	
	@Test
	public void buy_item_endTransaction() {
		VendingMachine vm = new VendingMachine();
		vm.insertMoney(8);
		assertTrue(vm.makePurchase("C3"));
		System.out.println(vm.endTransaction());
	}

}
