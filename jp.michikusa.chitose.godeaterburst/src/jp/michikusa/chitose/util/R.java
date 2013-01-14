package jp.michikusa.chitose.util;

import java.io.File;

public interface R{
	interface Application{
		String name= "god eater burst - database";
		String version= "v0.00";
		String author= "kamichidu";
		int minimum_width= 800;
		int minimum_height= 600;
		File config_file= new File("conf/config.properties");
		
		interface Config{
			String key_separator= ".";
			
			interface Color{
				String key_prefix= "color" + Config.key_separator;
				String fg_key_suffix= Config.key_separator + "fg";
				String bg_key_suffix= Config.key_separator + "bg";
			}
			
			interface Text{
				String key_prefix= "text" + Config.key_separator;
			}
		}
	}
	
	interface Jdbc{
		String driver_name= "org.sqlite.JDBC";
	}
	
	interface Hibernate{
		String configuration_filename= "hibernate.cfg.xml";
		File schema_export_file= new File("build/schema-export.sql");
	}
}
