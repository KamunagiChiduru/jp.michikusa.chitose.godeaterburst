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
	
	public void dispose(){
		this.session_factory.close();
	}
	
	private H(){
		this.session_factory= new Configuration()
				.configure(R.Hibernate.configuration_filename)
				.buildSessionFactory();
	}
	
	private static final H instance= new H();
	
	private final SessionFactory session_factory;
}
