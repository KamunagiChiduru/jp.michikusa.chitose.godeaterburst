package jp.michikusa.chitose.util;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class H implements Serializable{
	public static H instance(){
		return instance;
	}
	
	public Session newSession(){
		return this.session_factory.openSession();
	}
	
	/**
	 * create {@link AutoCloseableSession} initialized with {@link #newSession()}.
	 * this method used instead of {@code new AutoCloseableSession(H.instance().newSession());}
	 * 
	 * @return new AutoCloseableSession instance
	 */
	public AutoCloseableSession newAutoClosableSession(){
		return new AutoCloseableSession(this.newSession());
	}
	
	public static class AutoCloseableSession implements AutoCloseable{
		public AutoCloseableSession(Session s){
			this.s= s;
		}
		
		public Session get(){
			return this.s;
		}
		
		@Override
		public void close() throws Exception{
			if(this.s.isOpen()){
				this.s.close();
			}
		}
		
		private final Session s;
	}
	
	private H(){
		this.session_factory= new Configuration()
				.configure(R.Hibernate.configuration_filename)
				.buildSessionFactory();
		
		ResourcePool.regist(this.session_factory);
	}
	
	private static final H instance= new H();
	
	private final SessionFactory session_factory;
}
