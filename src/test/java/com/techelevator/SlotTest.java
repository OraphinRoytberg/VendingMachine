package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

public class SlotTest {

	@Test
	public void test_contructor() {
		Slot potato = new Slot("A1", "potato" , 150);
		assertEquals("A1", potato.getSlotNumber());
		assertEquals("potato", potato.getFoodItem());
		assertEquals(150, potato.getPrice(), 0);
		assertEquals(5, potato.getCount());			
	}
	
	@Test
	public void test_decrementCount() {
		Slot potato = new Slot("A1", "potato" , 150);
		assertTrue(potato.decrementCount());
		assertTrue(potato.decrementCount());
		assertTrue(potato.decrementCount());
		assertTrue(potato.decrementCount());
		assertTrue(potato.decrementCount());
		assertFalse(potato.decrementCount());
		
	}

}
