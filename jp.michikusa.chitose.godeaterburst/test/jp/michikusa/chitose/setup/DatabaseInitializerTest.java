package jp.michikusa.chitose.setup;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DatabaseInitializerTest{
	@Test
	public void test() throws Exception{
		DatabaseInitializer proc= new DatabaseInitializer();
		
		assertTrue(proc.call());
	}
}
