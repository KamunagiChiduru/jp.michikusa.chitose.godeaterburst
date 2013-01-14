package jp.michikusa.chitose.setup;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		
		this.selectAny();
		
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
		
		this.insertAny();
		
		return true;
	}
	
	private void selectAny(){
		Connection connection= null;
		try{
			connection= DriverManager.getConnection(this.database_url);
			Statement statement= connection.createStatement();
			
			statement.setQueryTimeout(30);
			
			try(ResultSet r= statement.executeQuery("select * from item_info;")){
				while(r.next()){
					System.out.format(
							"%s%s",
							r.getString("name"),
							r.getString("category")
							);
				}
			}
		}
		catch(Throwable e){
			e.printStackTrace();
		}
		finally{
			if(connection != null){
				try{
					connection.close();
				}
				catch(Throwable e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void insertAny(){
		Connection connection= null;
		try{
			connection= DriverManager.getConnection(this.database_url);
			
			connection.setAutoCommit(false);
			
			Statement statement= connection.createStatement();
			
			statement.setQueryTimeout(30);
			
			statement
					.executeUpdate("insert into item_info values (-1, '消費アイテム', '回復錠', 20, 10, 2, 'よろず屋, etc');");
			statement
					.executeUpdate("insert into item_info values (-2, '消費アイテム', '回復錠改', 10, 20, 5, 'よろず屋, etc');");
			
			connection.commit();
		}
		catch(Throwable e){
			try{
				if(connection != null){
					connection.rollback();
				}
			}
			catch(Throwable ex){
				ex.printStackTrace();
			}
			finally{
				e.printStackTrace();
			}
		}
		finally{
			try{
				if(connection != null){
					connection.close();
				}
			}
			catch(Throwable e){
				e.printStackTrace();
			}
		}
	}
	
	private final String jdbc_driver_classname;
	private final String database_url;
	
	public static void main(String[] args) throws Exception{
		new DatabaseInitializer().call();
	}
}
