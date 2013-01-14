package jp.michikusa.chitose.view.item;

import static jp.michikusa.chitose.util.Widgets.empty;
import static jp.michikusa.chitose.util.Widgets.makeLabel;
import static jp.michikusa.chitose.util.Widgets.makeText;
import jp.michikusa.chitose.controller.ItemEditController;
import jp.michikusa.chitose.view.EditViewBase;
import jp.michikusa.chitose.view.widget.JsCurrency;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ItemEditView extends EditViewBase{
	public ItemEditView(){
		super(new ItemEditController());
	}
	
	@Override
	protected Composite makeEditPart(Composite parent){
		parent.setLayout(GridLayoutFactory.swtDefaults().numColumns(4).create());
		
		makeLabel(parent, "アイテム名");
		Text item_name= makeText(parent, SWT.SINGLE | SWT.BORDER);
		item_name.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				.create());
		
		makeLabel(parent, "購入価格/売却価格");
		JsCurrency buy_amount= new JsCurrency(parent, SWT.BORDER);
		buy_amount.setPrecision(12);
		buy_amount.setScale(0);
		
		JsCurrency sell_amount= new JsCurrency(parent, SWT.BORDER);
		// sell_amount.setLayoutData(GridDataFactory.fillDefaults().hint(100,
		// SWT.DEFAULT).create());
		sell_amount.setData(null);
		
		empty(parent);
		
		return parent;
	}
}
