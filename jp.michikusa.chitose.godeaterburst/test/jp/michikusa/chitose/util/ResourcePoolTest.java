package jp.michikusa.chitose.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import jp.michikusa.chitose.util.ResourcePool.Holder;

import org.junit.Test;

import com.google.common.collect.Sets;

public class ResourcePoolTest{
	
	@Test
	public void test(){
		Holder<String> hoge= new Holder<String>("hoge");
		Holder<String> piyo= new Holder<String>("piyo");
		Holder<String> hoge_= new Holder<String>("hoge");
		Holder<String> piyo2= new Holder<String>(new String("piyo"));
		
		assertEquals(hoge, hoge);
		assertEquals(hoge, hoge_);
		assertNotSame(hoge, piyo);
		assertEquals(hoge.hashCode(), hoge_.hashCode());
		
		assertEquals(piyo.hashCode(), piyo2.hashCode());
		assertNotSame(piyo, piyo2);
		
		Set<Holder<String>> s= Sets.newHashSet();
		
		s.add(hoge);
		s.add(hoge_);
		s.add(piyo);
		s.add(piyo2);
		
		assertEquals(3, s.size());
		assertTrue(s.contains(hoge));
		assertTrue(s.contains(hoge_));
		assertTrue(s.contains(piyo));
		assertTrue(s.contains(piyo2));
	}
	
}
