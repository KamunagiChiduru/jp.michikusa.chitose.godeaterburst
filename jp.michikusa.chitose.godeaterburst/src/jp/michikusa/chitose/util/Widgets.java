package jp.michikusa.chitose.util;

import jp.michikusa.chitose.view.widget.event.TextSelectionControllListener;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Strings;

public final class Widgets{
	public static Label makeLabel(Composite parent, String text){
		Label result= new Label(parent, SWT.NONE);
		
		result.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		result.setText(text);
		result.setForeground(Colors.get("color.label.fg"));
		result.setBackground(Colors.get("color.label.bg"));
		
		return result;
	}
	
	public static Button makeButton(Composite parent, int style){
		Button result= new Button(parent, style);
		
		result.setForeground(Colors.get("color.button.fg"));
		result.setBackground(Colors.get("color.button.bg"));
		
		return result;
	}
	
	public static Text makeText(Composite parent, int style){
		Text result= new Text(parent, style);
		
		result.setForeground(Colors.get("color.text.fg"));
		result.setBackground(Colors.get("color.text.bg"));
		
		result.addFocusListener(new TextSelectionControllListener());
		
		return result;
	}
	
	public static Combo makeCombo(Composite parent, int style){
		Combo result= new Combo(parent, style | SWT.READ_ONLY);
		
		result.setForeground(Colors.get("color.combo.fg"));
		result.setBackground(Colors.get("color.combo.bg"));
		
		return result;
	}
	
	public static Composite makeComposite(Composite parent){
		Composite result= new Composite(parent, SWT.NONE);
		
		result.setForeground(Colors.get("color.fg"));
		result.setBackground(Colors.get("color.bg"));
		
		return result;
	}
	
	public static void empty(Composite parent){
		empty(parent, 1);
	}
	
	public static void empty(Composite parent, int span){
		Label l= new Label(parent, SWT.NONE);
		
		l.setLayoutData(GridDataFactory
				.swtDefaults()
				.span(span, SWT.DEFAULT)
				.create()
				);
	}
	
	public static Point computeSize(Text widget, int text_length){
		String backup= widget.getText();
		String example= Strings.repeat("x", text_length);
		
		widget.setText(example);
		Point result= widget.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		widget.setText(backup);
		
		return result;
	}
	
	private Widgets(){}
}
