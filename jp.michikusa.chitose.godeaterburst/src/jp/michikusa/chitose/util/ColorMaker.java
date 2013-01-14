package jp.michikusa.chitose.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;

public final class ColorMaker{
	public static Color makeColor(Device device, String color_format){
		Impl maker= Impl.of(color_format);
		
		return maker.make(device, color_format);
	}
	
	private ColorMaker(){}
	
	private static enum Impl{
		MAKE_FROM_RGB_FORMAT("^rgb\\([^.]{1,3},[^.]{1,3},[^.]{1,3}\\)$"){
			@Override
			public Color make(Device device, String text){
				String[] rgb_text= text
						.toLowerCase()
						.trim()
						.replaceAll("^rgb\\(", "")
						.replaceAll("\\)$", "")
						.split(",", 3)
				;
				
				RGB rgb= new RGB(
						Integer.valueOf(rgb_text[0]),
						Integer.valueOf(rgb_text[1]),
						Integer.valueOf(rgb_text[2])
						);
				
				return new Color(device, rgb);
			}
		},
		MAKE_FROM_HSB_FORMAT("^hsb\\([.0-9],[.0-9],[.0-9]\\)$"){
			@Override
			public Color make(Device device, String text){
				String[] hsb_text= text
						.toLowerCase()
						.trim()
						.replaceAll("^hsb\\(", "")
						.replaceAll("\\)", "")
						.split(",", 3)
				;
				
				RGB rgb= new RGB(
						Float.valueOf(hsb_text[0]),
						Float.valueOf(hsb_text[1]),
						Float.valueOf(hsb_text[2])
						);
				
				return new Color(device, rgb);
			}
		},
		MAKE_FROM_HEX_FORMAT("^#[0-9a-f]{6}$"){
			@Override
			public Color make(Device device, String text){
				String hex_codes= text
						.toLowerCase()
						.trim()
						.replaceAll("#", "")
				;
				
				String r= hex_codes.substring(0, 1);
				String g= hex_codes.substring(2, 3);
				String b= hex_codes.substring(4, 5);
				
				final int radix= 16;
				RGB rgb= new RGB(
						Integer.valueOf(r, radix),
						Integer.valueOf(g, radix),
						Integer.valueOf(b, radix)
						);
				
				return new Color(device, rgb);
			}
		},
		;
		
		public final boolean isSupported(String color_format){
			return color_format.matches(this.support_format);
		}
		
		public abstract Color make(Device device, String text);
		
		public static Impl of(String color_format){
			String format= color_format.trim().toLowerCase();
			
			for(Impl maker : values()){
				if(maker.isSupported(format)){ return maker; }
			}
			throw new UnsupportedColorFormatException(color_format);
		}
		
		private final String support_format;
		
		private Impl(String support_format){
			this.support_format= support_format;
		}
	}
}
