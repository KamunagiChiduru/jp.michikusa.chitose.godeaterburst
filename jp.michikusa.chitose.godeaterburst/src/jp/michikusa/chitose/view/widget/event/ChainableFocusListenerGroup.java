package jp.michikusa.chitose.view.widget.event;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

import com.google.common.collect.Lists;

public class ChainableFocusListenerGroup implements FocusListener{
	public ChainableFocusListenerGroup(){}
	
	public ChainableFocusListenerGroup(ChainableFocusListener... listeners){
		Collections.addAll(this.listeners, listeners);
	}
	
	public ChainableFocusListenerGroup(List<? extends ChainableFocusListener> listeners){
		this.listeners.addAll(listeners);
	}
	
	public ChainableFocusListenerGroup add(ChainableFocusListener listener){
		this.listeners.add(checkNotNull(listener));
		
		return this;
	}
	
	public ChainableFocusListenerGroup remove(ChainableFocusListener listener){
		this.listeners.remove(listener);
		
		return this;
	}
	
	public ChainableFocusListenerGroup clear(){
		this.listeners.clear();
		
		return this;
	}
	
	public int size(){
		return this.listeners.size();
	}
	
	@Override
	public void focusGained(FocusEvent e){
		for(ChainableFocusListener listener : this.listeners){
			if( !listener.focusGained(e)){ return; }
		}
	}
	
	@Override
	public void focusLost(FocusEvent e){
		for(ChainableFocusListener listener : this.listeners){
			if( !listener.focusLost(e)){ return; }
		}
	}
	
	private final List<ChainableFocusListener> listeners= Lists.newLinkedList();
}
