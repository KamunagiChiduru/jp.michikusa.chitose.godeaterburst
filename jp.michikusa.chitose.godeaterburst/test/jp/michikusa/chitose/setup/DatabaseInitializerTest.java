package jp.michikusa.chitose.setup;

import jp.michikusa.chitose.entity.ItemCategory;
import jp.michikusa.chitose.util.H;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;

public class DatabaseInitializerTest{
	@BeforeClass
	public static void initDb() throws Exception{
		DatabaseInitializer proc= new DatabaseInitializer();
		
		assertTrue(proc.call());
	}
	
	@Test
	public void insertAndSelect(){
		Session s= H.instance().newSession();
		
		try{
			Transaction tx= null;
			try{
				tx= s.beginTransaction();
				
				ItemCategory category= new ItemCategory();
				
				category.setName("消費アイテム");
				
				s.saveOrUpdate(category);
				
				tx.commit();
			}
			catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				throw new AssertionError(e);
			}			
			ItemCategory result= (ItemCategory)s
					.createCriteria(ItemCategory.class)					.add(Restrictions.eq(ItemCategory.Column.name, "消費アイテム"))
					.uniqueResult();
			
			assertNotNull(result);
			assertEquals("消費アイテム", result.getName());			assertTrue(result.getItemInfos() != null);			assertTrue(result.getItemInfos().isEmpty());		}
		finally{
			s.close();
		}
	}
}
