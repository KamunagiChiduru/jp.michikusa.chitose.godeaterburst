package jp.michikusa.chitose.view.item;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jp.michikusa.chitose.entity.ItemInfo;
import jp.michikusa.chitose.util.H;
import jp.michikusa.chitose.util.H.AutoCloseableSession;
import jp.michikusa.chitose.view.SearchViewBase;
import jp.michikusa.chitose.view.widget.AppearPointPeriod;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import static jp.michikusa.chitose.util.Widgets.makeCombo;
import static jp.michikusa.chitose.util.Widgets.makeLabel;
import static jp.michikusa.chitose.util.Widgets.makeText;

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
		this.result_table.setContentProvider(new ArrayContentProvider());
		
		TableViewerColumn item_name= new TableViewerColumn(this.result_table, SWT.LEFT);
		
		item_name.getColumn().setText("アイテム名");
		item_name.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element){
				ItemInfo info= (ItemInfo)element;
				
				return info.getName();
			}
		});
		
		TableViewerColumn item_effect= new TableViewerColumn(this.result_table, SWT.LEFT);
		
		item_effect.getColumn().setText("効果・時間");
		item_effect.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element){
				ItemInfo info= (ItemInfo)element;
				
				return info.getDescription();
			}
		});
		
		TableViewerColumn limit_numbers= new TableViewerColumn(this.result_table, SWT.RIGHT);
		
		limit_numbers.getColumn().setText("最大携行可能数");
		limit_numbers.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element){
				ItemInfo info= (ItemInfo)element;
				
				return new DecimalFormat().format(info.getLimitNumbers());
			}
		});
		
		TableViewerColumn purchasing_price= new TableViewerColumn(this.result_table, SWT.RIGHT);
		
		purchasing_price.getColumn().setText("購入額");
		purchasing_price.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element){
				ItemInfo info= (ItemInfo)element;
				
				return new DecimalFormat().format(info.getPurchasingPrice());
			}
		});
		
		TableViewerColumn selling_price= new TableViewerColumn(this.result_table, SWT.RIGHT);
		
		selling_price.getColumn().setText("売却額");
		selling_price.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element){
				ItemInfo info= (ItemInfo)element;
				
				return new DecimalFormat().format(info.getSellingPrice());
			}
		});
		
		TableViewerColumn note= new TableViewerColumn(this.result_table, SWT.LEFT);
		
		note.getColumn().setText("主な入手法");
		note.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element){
				ItemInfo info= (ItemInfo)element;
				
				return info.getNote();
			}
		});
		
		for(TableColumn column : this.result_table.getTable().getColumns()){
			column.setMoveable(true);
			column.setResizable(true);
			column.pack();
		}
		
		return parent;
	}
	
	@Override
	protected void onSearchBehavior(){
		try(AutoCloseableSession session= H.instance().newAutoClosableSession()){
			@SuppressWarnings("unchecked")
			List<ItemInfo> result= new ArrayList<>(session.get()
					.createCriteria(ItemInfo.class)
					.list());
			
			this.result_table.setInput(result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private TableViewer result_table;
}
