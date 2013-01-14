package jp.michikusa.chitose.view.widget;

import static com.google.common.base.Preconditions.checkArgument;
import static jp.michikusa.chitose.util.Widgets.makeText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import jp.michikusa.chitose.util.Colors;
import jp.michikusa.chitose.util.Objects;
import jp.michikusa.chitose.view.widget.event.ChainableFocusListener;
import jp.michikusa.chitose.view.widget.event.ChainableFocusListenerGroup;
import jp.michikusa.chitose.view.widget.event.JsInputChangeListener;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class JsCurrency extends Composite{
	public JsCurrency(Composite parent, int style){
		super(parent, SWT.NONE);
		
		this.setLayout(GridLayoutFactory.fillDefaults().create());
		
		this.w= makeText(this, style | SWT.SINGLE | SWT.RIGHT);
		this.w.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		this.w.addFocusListener(new ChainableFocusListenerGroup(
				new StopIfNoInputListener(),
				new NumberFormatValidateListener(),
				new PrecisionAndScaleValidateListener(),
				new TextFormatListener(),
				new ReflectDataListener()
				));
	}
	
	@Deprecated
	@Override
	public void setData(Object data){
		checkArgument(data instanceof BigDecimal);
		
		this.setData((BigDecimal)data);
	}
	
	public void setData(BigDecimal data){
		BigDecimal old_value= this.getData();
		
		this.w.setData(data);
		if(data != null){
			this.w.setText(new DecimalFormat().format(data));
		}
		else{
			this.w.setText("");
		}
		
		if(Objects.isNotEqualsOrNotSame(old_value, this.getData())){
			for(JsInputChangeListener<BigDecimal> listener : this.input_change_listeners){
				listener.inputChanged(old_value, this.getData());
			}
		}
	}
	
	@Override
	public BigDecimal getData(){
		return (BigDecimal)this.w.getData();
	}
	
	public void setPrecision(int precision){
		checkArgument(precision > 0);
		
		this.precision= precision;
	}
	
	public void setScale(int scale){
		checkArgument(scale >= 0);
		
		this.scale= scale;
	}
	
	public int getPrecision(){
		return this.precision;
	}
	
	public int getScale(){
		return this.scale;
	}
	
	public void addInputChangeListener(JsInputChangeListener<BigDecimal> listener){
		this.input_change_listeners.add(listener);
	}
	
	public void removeInputChangeListener(JsInputChangeListener<BigDecimal> listener){
		this.input_change_listeners.remove(listener);
	}
	
	@Override
	public void addFocusListener(FocusListener listener){
		this.w.addFocusListener(listener);
	}
	
	@Override
	public void removeFocusListener(FocusListener listener){
		this.w.removeFocusListener(listener);
	}
	
	@Override
	public void addHelpListener(HelpListener listener){
		this.w.addHelpListener(listener);
	}
	
	@Override
	public void removeHelpListener(HelpListener listener){
		this.w.removeHelpListener(listener);
	}
	
	@Override
	public void setForeground(Color color){
		this.w.setForeground(color);
	}
	
	@Override
	public void setBackground(Color color){
		this.w.setBackground(color);
	}
	
	@Override
	public Color getForeground(){
		return this.w.getForeground();
	}
	
	@Override
	public Color getBackground(){
		return this.w.getBackground();
	}
	
	private JsCurrency that(){
		return this;
	}
	
	private void success(){
		this.w.setForeground(Colors.get("color.text.fg"));
		this.w.setBackground(Colors.get("color.text.bg"));
		this.setToolTipText(null);
	}
	
	private void error(String msg){
		this.w.setForeground(Colors.get("color.error.fg"));
		this.w.setBackground(Colors.get("color.error.bg"));
		this.setToolTipText(msg);
	}
	
	private class StopIfNoInputListener implements ChainableFocusListener{
		@Override
		public boolean focusGained(FocusEvent e){
			that().success();
			return this.hasText(e);
		}
		
		@Override
		public boolean focusLost(FocusEvent e){
			that().success();
			return this.hasText(e);
		}
		
		private boolean hasText(FocusEvent e){
			Text w= (Text)e.getSource();
			
			return !Strings.isNullOrEmpty(w.getText());
		}
	}
	
	private class NumberFormatValidateListener implements ChainableFocusListener{
		@Override
		public boolean focusGained(FocusEvent e){
			return true;
		}
		
		@Override
		public boolean focusLost(FocusEvent e){
			Text w= (Text)e.getSource();
			
			try{
				new BigDecimal(w.getText());
				
				return true;
			}
			catch(NumberFormatException ex){
				that().error(ex.getMessage());
				
				return false;
			}
		}
	}
	
	private class PrecisionAndScaleValidateListener implements ChainableFocusListener{
		@Override
		public boolean focusGained(FocusEvent e){
			return true;
		}
		
		@Override
		public boolean focusLost(FocusEvent e){
			Text w= (Text)e.getSource();
			
			if(Strings.isNullOrEmpty(w.getText())){ return true; }
			
			BigDecimal n= new BigDecimal(w.getText()
					.trim()
					.replaceAll(",", "")
					);
			
			if( !(n.precision() <= that().precision && n.scale() <= that().scale)){
				that().error(String.format(
						"precision %d <= $d, scale %d <= %d.",
						n.precision(),
						that().precision,
						n.scale(),
						that().scale
						));
				return false;
			}
			
			return true;
		}
	}
	
	private static class TextFormatListener implements ChainableFocusListener{
		@Override
		public boolean focusGained(FocusEvent e){
			Text w= (Text)e.getSource();
			
			w.setText(w.getText().replaceAll(",", ""));
			
			return true;
		}
		
		@Override
		public boolean focusLost(FocusEvent e){
			Text w= (Text)e.getSource();
			
			String text= w.getText().trim().replaceAll(",", "");
			
			DecimalFormat fmt= new DecimalFormat();
			w.setText(fmt.format(new BigDecimal(text)));
			
			return true;
		}
	}
	
	private static class ReflectDataListener implements ChainableFocusListener{
		@Override
		public boolean focusGained(FocusEvent e){
			return true;
		}
		
		@Override
		public boolean focusLost(FocusEvent e){
			Text w= (Text)e.getSource();
			
			if( !Strings.isNullOrEmpty(w.getText())){
				w.getParent().setData(new BigDecimal(w.getText().trim().replaceAll(",", "")));
			}
			
			return true;
		}
	}
	
	private final Text w;
	private final List<JsInputChangeListener<BigDecimal>> input_change_listeners=
			Lists.newLinkedList();
	
	private int precision= 12;
	private int scale= 0;
}
