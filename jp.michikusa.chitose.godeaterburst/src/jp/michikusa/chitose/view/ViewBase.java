package jp.michikusa.chitose.view;

import jp.michikusa.chitose.util.Colors;
import jp.michikusa.chitose.util.Views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class ViewBase extends Composite{
	public ViewBase(){
		super(Views.topComposite(), SWT.NONE);
		
		this.setForeground(Colors.get("color.fg"));
		this.setBackground(Colors.get("color.bg"));
		
		Composite view= this.makeView(this);
		
		view.setForeground(Colors.get("color.fg"));
		view.setBackground(Colors.get("color.bg"));
	}
	
	protected abstract Composite makeView(Composite parent);
}
