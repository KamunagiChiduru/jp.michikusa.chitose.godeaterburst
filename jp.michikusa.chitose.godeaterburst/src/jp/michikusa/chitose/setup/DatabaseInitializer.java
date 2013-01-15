package jp.michikusa.chitose.setup;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.Callable;

import jp.michikusa.chitose.util.R;

import org.apache.commons.io.FileUtils;
import org.hibernate.cfg.Configuration;

public class DatabaseInitializer implements Callable<Boolean>{
	public DatabaseInitializer(){
		Properties props= new Configuration()
				.configure(R.Hibernate.configuration_filename)
				.getProperties();
		
		this.jdbc_driver_classname= props.getProperty("connection.driver_class");
		this.database_url= props.getProperty("connection.url");
	}
	
	@Override
	public Boolean call() throws Exception{
		Class.forName(this.jdbc_driver_classname);
		
		Connection connection= null;
		try{
			connection= DriverManager.getConnection(this.database_url);
			
			connection.setAutoCommit(false);
			
			Statement statement= connection.createStatement();
			
			statement.setQueryTimeout(30);
			
			String read= FileUtils.readFileToString(
					R.Hibernate.schema_export_file,
					Charset.forName("UTF-8")
					);
			String[] ddls= read.split(";");
			for(String ddl : ddls){
				ddl= ddl.trim();
				if( !ddl.isEmpty()){
					System.out.format("> '%s'%n", ddl);
					statement.executeUpdate(ddl);
				}
			}
			
			connection.commit();
		}
		catch(Throwable e){
			if(connection != null){
				connection.rollback();
			}
			e.printStackTrace();
			return false;
		}
		finally{
			if(connection != null){
				connection.close();
			}
		}
		
		return true;
	}
	
	private final String jdbc_driver_classname;
	private final String database_url;
	
	public static void main(String[] args) throws Exception{
		new DatabaseInitializer().call();
	}
}
