package jp.michikusa.chitose.view.item;

import static jp.michikusa.chitose.util.Widgets.makeCombo;
import static jp.michikusa.chitose.util.Widgets.makeLabel;
import static jp.michikusa.chitose.util.Widgets.makeText;

import java.util.ArrayList;
import java.util.List;

import jp.michikusa.chitose.entity.ItemInfo;
import jp.michikusa.chitose.util.H;
import jp.michikusa.chitose.view.SearchViewBase;
import jp.michikusa.chitose.view.widget.AppearPointPeriod;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.hibernate.Session;

public class ItemSearchView extends SearchViewBase{
	@Override
	protected Composite makeConditionPart(Composite parent){
		parent.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).create());
		
		makeLabel(parent, "分類");
		Combo category= makeCombo(parent, SWT.NONE);
		
		makeLabel(parent, "名称");
		Text name= makeText(parent, SWT.SINGLE | SWT.BORDER);
		name.setLayoutData(GridDataFactory.fillDefaults().create());
		
		makeLabel(parent, "出現時期");
		AppearPointPeriod appear_point= new AppearPointPeriod(parent);
		
		return parent;
	}
	
	@Override
	protected Composite makeResultPart(Composite parent){
		parent.setLayout(GridLayoutFactory.fillDefaults().create());
		parent.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		
		this.result_table= new TableViewer(parent, SWT.BORDER);
		
		this.result_table.getTable().setLayoutData(
				GridDataFactory.fillDefaults().grab(true, true).create());
		this.result_table.getTable().setHeaderVisible(true);
		this.result_table.getTable().setLinesVisible(true);
		
		String[] labels= {
				"アイテム名",
				"効果・時間",
				"最大傾向可能数",
				"購入額",
				"売却額",
				"主な入手法",
		};
		for(String label : labels){
			TableColumn column= new TableColumn(this.result_table.getTable(), SWT.NONE);
			
			column.setText(label);
			column.setMoveable(true);
			column.setResizable(true);
		}
		this.result_table.setLabelProvider(new ITableLabelProvider(){
			@Override
			public void removeListener(ILabelProviderListener listener){}
			
			@Override
			public boolean isLabelProperty(Object element, String property){
				return false;
			}
			
			@Override
			public void dispose(){}
			
			@Override
			public void addListener(ILabelProviderListener listener){}
			
			@Override
			public String getColumnText(Object element, int columnIndex){
				return ((Object[])element)[columnIndex].toString();
			}
			
			@Override
			public Image getColumnImage(Object element, int columnIndex){
				return null;
			}
		});
		this.result_table.setContentProvider(new IStructuredContentProvider(){
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput){}
			
			@Override
			public void dispose(){}
			
			@Override
			public Object[] getElements(Object inputElement){
				@SuppressWarnings("unchecked")
				List<ItemInfo> elms= (List<ItemInfo>)inputElement;
				List<Object> result= new ArrayList<>(elms.size());
				
				for(ItemInfo elm : elms){
					result.add(new Object[]{
							elm.getName(),
							"hoge",
							elm.getNitems(),
							elm.getBuyAmount(),
							elm.getSellAmount(),
							elm.getNote(),
					});
				}
				
				return result.toArray();
			}
		});
		
		for(TableColumn column : this.result_table.getTable().getColumns()){
			column.pack();
		}
		
		return parent;
	}
	
	@Override
	protected void onSearchBehavior(){
		Session session= null;
		
		try{
			session= H.instance().newSession();
			
			@SuppressWarnings("unchecked")
			List<ItemInfo> result= new ArrayList<>(session
					.createCriteria(ItemInfo.class)
					.list());
			
			this.result_table.setInput(result);
		}
		finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	private TableViewer result_table;
}
