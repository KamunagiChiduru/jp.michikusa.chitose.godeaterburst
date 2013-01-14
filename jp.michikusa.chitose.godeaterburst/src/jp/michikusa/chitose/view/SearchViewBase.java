package jp.michikusa.chitose.view;

import static jp.michikusa.chitose.util.Widgets.makeButton;
import static jp.michikusa.chitose.util.Widgets.makeComposite;
import jp.michikusa.chitose.util.Widgets;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class SearchViewBase extends ViewBase{
	@Override
	protected final Composite makeView(Composite parent){
		parent.setLayout(GridLayoutFactory
				.swtDefaults()
				.numColumns(2)
				.create()
				);
		
		// ボタン類
		Composite button_part= makeComposite(parent);
		
		button_part.setLayout(GridLayoutFactory
				.fillDefaults()
				.create()
				);
		
		Button search_button= makeButton(button_part, SWT.PUSH);
		
		search_button.setText("検索");
		search_button.setLayoutData(GridDataFactory
				.swtDefaults()
				.create()
				);
		search_button.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				onSearchBehavior();
			}
		});
		
		Widgets.empty(parent);
		
		// 検索条件部
		ScrolledComposite condition_parent= new ScrolledComposite(
				parent,
				SWT.H_SCROLL | SWT.V_SCROLL
				);
		condition_parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		condition_parent.setExpandVertical(true);
		condition_parent.setExpandHorizontal(true);
		
		Composite condition_part= this.makeConditionPart(makeComposite(condition_parent));
		
		condition_parent.setContent(condition_part);
		Point condition_part_size= condition_part.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		condition_part.setSize(condition_part_size);
		condition_parent.setMinSize(condition_part_size);
		
		// 検索結果部
		ScrolledComposite result_parent= new ScrolledComposite(
				parent,
				SWT.H_SCROLL | SWT.V_SCROLL
				);
		result_parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		result_parent.setExpandVertical(true);
		result_parent.setExpandHorizontal(true);
		
		Composite result_part= this.makeResultPart(makeComposite(result_parent));
		
		result_parent.setContent(result_part);
		Point result_part_size= result_part.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		result_part.setSize(result_part_size);
		result_parent.setMinSize(result_part_size);
		
		return parent;
	}
	
	protected abstract Composite makeConditionPart(Composite parent);
	
	protected abstract Composite makeResultPart(Composite parent);
	
	protected abstract void onSearchBehavior();
}
