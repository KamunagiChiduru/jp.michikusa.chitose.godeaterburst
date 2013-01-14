package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringTest{
	@Test
	public void replace(){
		assertEquals("hoge", "AhogeA".replaceAll("A(hoge)A", "$1"));
	}
}
