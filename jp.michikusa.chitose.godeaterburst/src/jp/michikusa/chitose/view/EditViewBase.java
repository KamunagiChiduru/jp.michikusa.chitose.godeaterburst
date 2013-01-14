package jp.michikusa.chitose.view;

import static jp.michikusa.chitose.util.Widgets.makeButton;
import static jp.michikusa.chitose.util.Widgets.makeComposite;
import jp.michikusa.chitose.controller.Controller;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class EditViewBase extends ViewBase{
	public EditViewBase(Controller controller){
		this.controller= controller;
	}
	
	@Override
	protected final Composite makeView(Composite parent){
		parent.setLayout(GridLayoutFactory.fillDefaults().create());
		
		Composite button_part= this.makeButtonPart(makeComposite(parent));
		
		ScrolledComposite base= new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		base.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		base.setExpandVertical(true);
		base.setExpandHorizontal(true);
		
		Composite edit_part= this.makeEditPart(makeComposite(base));
		base.setContent(edit_part);
		Point point= edit_part.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		edit_part.setSize(point);
		base.setMinSize(point);
		
		return base;
	}
	
	protected Composite makeButtonPart(Composite parent){
		parent.setLayout(GridLayoutFactory.swtDefaults().numColumns(3).create());
		
		Button read= makeButton(parent, SWT.PUSH);
		read.setText("読込");
		read.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				Controller.ReadController c= that().controller.readController();
				
				if(c == null){ throw new UnsupportedOperationException(); }
				
				if(c.onReadPre()){
					if(c.onRead()){
						if(c.onReadPost()){
							
						}
					}
				}
			}
		});
		
		Button save= makeButton(parent, SWT.PUSH);
		save.setText("保存");
		save.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				Controller.SaveController c= that().controller.saveController();
				
				if(c == null){ throw new UnsupportedOperationException(); }
				
				if(c.onSavePre()){
					if(c.onSave()){
						if(c.onSavePost()){
							
						}
					}
				}
			}
		});
		
		return parent;
	}
	
	protected abstract Composite makeEditPart(Composite parent);
	
	private EditViewBase that(){
		return this;
	}
	
	private final Controller controller;
}
