package jp.michikusa.chitose.util;

public class UnsupportedColorFormatException extends RuntimeException{
	private static final long serialVersionUID= 4490366774756372004L;
	
	public UnsupportedColorFormatException(String unsupported_format){
		super(String.format("unsupported color format: '%s'", unsupported_format));
	}
}
