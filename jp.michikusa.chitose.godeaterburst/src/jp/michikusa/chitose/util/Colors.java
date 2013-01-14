package jp.michikusa.chitose.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.graphics.Color;

import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class Colors{
	public static Color get(String key){
		checkArgument(key.equals(key.toLowerCase()) && key.startsWith(prefix));
		
		final String original_key= key;
		
		String color_key_regex= String.format(
				"^%s\\%s(%s|%s)$",
				prefix, separator, fg_suffix, bg_suffix
				);
		while( !key.matches(color_key_regex)){
			if(colors.containsKey(key)){ return colors.get(key); }
			
			key= up(key);
		}
		if(colors.containsKey(key)){ return colors.get(key); }
		
		throw new AssertionError(String.format("not found color key: '%s'", original_key));
	}
	
	private static String up(String color_key){
		ImmutableList<String> categories= ImmutableList.copyOf(color_key.split("\\" + separator));
		
		String parent_category= Joiner.on(separator)
				.join(categories.subList(0, categories.size() - 2));
		
		return parent_category + separator + categories.get(categories.size() - 1);
	}
	
	private static ImmutableMap<String, Color> loadFromProperties(File config_file){
		Properties properties= new Properties();
		
		try{
			properties.load(new FileInputStream(config_file));
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
		
		Map<String, Supplier<Color>> tmp= Maps.newLinkedHashMap();
		
		for(String key : properties.stringPropertyNames()){
			if(isColorKey(key)){
				String color_format= properties.getProperty(key);
				
				if(isColorKey(color_format)){
					tmp.put(toColorKey(key), new LazyColorSupplier(toColorKey(color_format), tmp));
				}
				else{
					Color color= ColorMaker.makeColor(Views.display(), color_format);
					
					tmp.put(toColorKey(key), new ColorHolder(color));
				}
			}
		}
		
		ImmutableMap.Builder<String, Color> builder= ImmutableMap.builder();
		
		for(String key : tmp.keySet()){
			Color color= tmp.get(key).get();
			
			builder.put(key, color);
			ResourcePool.regist(color);
		}
		
		return builder.build();
	}
	
	private static boolean isColorKey(String color_format){
		return toColorKey(color_format).startsWith("color.");
	}
	
	private static String toColorKey(String text){
		return text.trim().toLowerCase();
	}
	
	private static class ColorHolder implements Supplier<Color>{
		public ColorHolder(Color color){
			this.color= color;
		}
		
		@Override
		public Color get(){
			return this.color;
		}
		
		private final Color color;
	}
	
	private static class LazyColorSupplier implements Supplier<Color>{
		public LazyColorSupplier(String ref_key, Map<String, ? extends Supplier<Color>> m){
			this.ref_key= ref_key;
			this.m= m;
		}
		
		@Override
		public Color get(){
			checkState(this.waiting.compareAndSet(false, true), "循環参照が存在する。");
			
			return this.m.get(ref_key).get();
		}
		
		private final String ref_key;
		private final Map<String, ? extends Supplier<Color>> m;
		private AtomicBoolean waiting= new AtomicBoolean(false);
	}
	
	private Colors(){}
	
	private static final ImmutableMap<String, Color> colors= loadFromProperties(R.Application.config_file);
	
	private static final String separator= R.Application.Config.key_separator;
	private static final String prefix= R.Application.Config.Color.key_prefix;
	private static final String fg_suffix= R.Application.Config.Color.fg_key_suffix;
	private static final String bg_suffix= R.Application.Config.Color.bg_key_suffix;
}
