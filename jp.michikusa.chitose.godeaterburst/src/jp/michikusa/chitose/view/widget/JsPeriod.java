package jp.michikusa.chitose.view.widget;

import jp.michikusa.chitose.util.Colors;
import jp.michikusa.chitose.util.Widgets;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class JsPeriod<W extends Control> extends Composite{
	public JsPeriod(Composite parent){
		super(parent, SWT.NONE);
		
		this.setLayout(GridLayoutFactory.fillDefaults().numColumns(3).create());
		this.setForeground(Colors.get("color.fg"));
		this.setBackground(Colors.get("color.bg"));
		
		this.from= this.makeBoundaryWidget(this);
		Widgets.makeLabel(this, "〜");
		this.to= this.makeBoundaryWidget(this);
	}
	
	public W getFrom(){
		return this.from;
	}
	
	public W getTo(){
		return this.to;
	}
	
	protected abstract W makeBoundaryWidget(Composite parent);
	
	private final W from;
	private final W to;
}
