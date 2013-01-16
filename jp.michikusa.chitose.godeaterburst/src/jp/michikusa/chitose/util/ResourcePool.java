package jp.michikusa.chitose.util;

import java.util.Set;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Widget;
import org.hibernate.SessionFactory;

import com.google.common.collect.Sets;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ResourcePool{
	public static synchronized void regist(Widget w){
		widget_pool.add(new Holder<>(w));
	}
	
	public static void regist(Color c){
		color_pool.add(new Holder<>(c));
	}
	
	public static void regist(SessionFactory session_factory){
		session_factory_pool.add(new Holder<>(session_factory));
	}
	
	public static synchronized void dispose(){
		for(Holder<SessionFactory> session_factory : session_factory_pool){
			if( !session_factory.get().isClosed()){
				session_factory.get().close();
			}
		}
		session_factory_pool.clear();
		
		for(Holder<Widget> widget : widget_pool){
			if( !widget.get().isDisposed()){
				widget.get().dispose();
			}
		}
		widget_pool.clear();
		
		for(Holder<Color> color : color_pool){
			if( !color.get().isDisposed()){
				color.get().dispose();
			}
		}
		color_pool.clear();
	}
	
	static class Holder<T>{
		public Holder(T ref){
			this.ref= checkNotNull(ref);
		}
		
		public T get(){
			return this.ref;
		}
		
		@Override
		public int hashCode(){
			return this.ref.hashCode();
		}
		
		@Override
		public boolean equals(Object obj){
			if(obj == null){ return false; }
			if(obj.getClass() == this.getClass()){
				Holder<?> rhs= (Holder<?>)obj;
				
				return rhs.ref == this.ref;
			}
			return false;
		}
		
		@Override
		public String toString(){
			return this.ref.toString();
		}
		
		private final T ref;
	}
	
	private ResourcePool(){}
	
	private static final Set<Holder<SessionFactory>> session_factory_pool= Sets.newHashSet();
	private static final Set<Holder<Widget>> widget_pool= Sets.newHashSet();
	private static final Set<Holder<Color>> color_pool= Sets.newHashSet();
}
