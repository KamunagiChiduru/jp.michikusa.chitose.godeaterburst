package jp.michikusa.chitose.view;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.widgets.Composite;

public class GuideView extends ViewBase{
	@Override
	protected Composite makeView(Composite parent){
		parent.setLayout(GridLayoutFactory.fillDefaults().create());
		
		return parent;
	}
	
}
