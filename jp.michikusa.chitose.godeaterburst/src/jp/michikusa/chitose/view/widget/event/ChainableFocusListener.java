package jp.michikusa.chitose.view.widget.event;

import org.eclipse.swt.events.FocusEvent;

public interface ChainableFocusListener{
	boolean focusGained(FocusEvent e);
	
	boolean focusLost(FocusEvent e);
}
