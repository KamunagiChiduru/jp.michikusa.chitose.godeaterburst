package jp.michikusa.chitose.view.widget;

import static jp.michikusa.chitose.util.Widgets.makeCombo;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class AppearPointPeriod extends JsPeriod<Combo>{
	public AppearPointPeriod(Composite parent){
		super(parent);
	}
	
	@Override
	protected Combo makeBoundaryWidget(Composite parent){
		Combo result= makeCombo(parent, SWT.NONE);
		
		result.setLayoutData(GridDataFactory
				.fillDefaults()
				.hint(100, SWT.DEFAULT)
				.create()
				);
		
		result.setItems(new String[]{
				"",
				"最初",
				"難易度1",
				"難易度2",
				"難易度3",
				"難易度4",
				"難易度5",
				"難易度6",
				"難易度7",
				"難易度8",
				"難易度9",
				"難易度10",
				"Ch",
		});
		
		return result;
	}
}
