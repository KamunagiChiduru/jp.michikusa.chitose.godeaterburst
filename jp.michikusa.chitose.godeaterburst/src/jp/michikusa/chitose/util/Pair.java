package jp.michikusa.chitose.util;

import static com.google.common.base.Preconditions.checkNotNull;

public class Pair<E1, E2>{
	public Pair(E1 first, E2 second){
		this.first= checkNotNull(first);
		this.second= checkNotNull(second);
	}
	
	public E1 first(){
		return this.first;
	}
	
	public E2 second(){
		return this.second;
	}
	
	@Override
	public int hashCode(){
		final int prime= 31;
		int result= 1;
		result= prime * result + ((first == null) ? 0 : first.hashCode());
		result= prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Pair<?, ?> other= (Pair<?, ?>)obj;
		if(first == null){
			if(other.first != null) return false;
		}
		else if( !first.equals(other.first)) return false;
		if(second == null){
			if(other.second != null) return false;
		}
		else if( !second.equals(other.second)) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "Pair [first=" + first + ", second=" + second + "]";
	}
	
	private final E1 first;
	private final E2 second;
}
