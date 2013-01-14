package jp.michikusa.chitose.util;

public final class Objects{
	public static boolean isNotEqualsOrNotSame(Object before, Object after){
		// equalsが成り立たなければtrue
		if(before != null && after != null && !before.equals(after)){
			return true;
		}
		else if(before == null && after != null){
			return true;
		}
		else if(before != null && after == null){ //
			return true; //
		}
		
		return false;
	}
	
	private Objects(){}
}
