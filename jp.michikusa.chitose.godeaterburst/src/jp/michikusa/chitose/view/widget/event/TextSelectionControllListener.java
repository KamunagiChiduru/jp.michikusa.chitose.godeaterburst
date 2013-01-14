package jp.michikusa.chitose.view.widget.event;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Text;

public class TextSelectionControllListener implements FocusListener{
	@Override
	public void focusGained(FocusEvent e){
		Text w= (Text)e.getSource();
		
		w.selectAll();
	}
	
	@Override
	public void focusLost(FocusEvent e){
		Text w= (Text)e.getSource();
		
		w.clearSelection();
	}
}
