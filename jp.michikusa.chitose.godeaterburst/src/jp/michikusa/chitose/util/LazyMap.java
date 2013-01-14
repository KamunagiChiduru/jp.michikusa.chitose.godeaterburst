package jp.michikusa.chitose.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

public class LazyMap<K, V>{
	public LazyMap(Map<K, V> m){
		this.m= checkNotNull(m);
	}
	
	public void ref(K key, K ref_key){
		this.refs.put(key, ref_key);
	}
	
	public ImmutableList<K> resolveOrder(){
		final ImmutableMap<K, K> refs= ImmutableMap.copyOf(this.refs);
		
		// 独立なキーを探す
		// 独立:= valueが他のkeyを指さない
		ImmutableCollection<K> individuals= ImmutableSet.copyOf(
				Iterables.filter(this.refs.values(), new Predicate<K>(){
					@Override
					public boolean apply(K elm){
						return !refs.containsKey(elm);
					}
				}));
		
		// 依存関係のあるキーを探す
		ImmutableCollection<K> depends= ImmutableSet.copyOf(
				Iterables.filter(this.refs.values(), new Predicate<K>(){
					@Override
					public boolean apply(K elm){
						return refs.containsKey(elm);
					}
				}));
		
		return ImmutableList.of();
	}
	
	public V resolve(K key){
		if( !this.refs.containsKey(key)){ return null; }
		
		K ref_key= this.refs.get(key);
		
		if( !this.m.containsKey(ref_key)){ return null; }
		
		this.refs.remove(key);
		
		return this.m.get(ref_key);
	}
	
	public boolean isEmpty(){
		return this.refs.isEmpty();
	}
	
	public int size(){
		return this.refs.size();
	}
	
	public Set<K> keySet(){
		return this.refs.keySet();
	}
	
	public K get(K key){
		return this.refs.get(key);
	}
	
	private final Map<K, V> m;
	private final Map<K, K> refs= Maps.newHashMap();
}
