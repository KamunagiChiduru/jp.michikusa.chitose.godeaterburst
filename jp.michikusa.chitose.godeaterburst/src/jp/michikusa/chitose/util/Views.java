package jp.michikusa.chitose.util;

import jp.michikusa.chitose.view.ViewBase;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public final class Views{
	public static Display display(){
		return display;
	}
	
	public static Shell shell(){
		return shell;
	}
	
	public static Composite topComposite(){
		return shell();
	}
	
	public static Runnable runner(){
		return new Runnable(){
			@Override
			public void run(){
				shell().open();
				while( !shell().isDisposed()){
					if( !display().readAndDispatch()){
						display().sleep();
					}
				}
				display.dispose();
			}
		};
	}
	
	public static void openView(ViewBase opened){
		synchronized(view_stack){
			view_stack.topControl= opened;
			opened.getParent().layout();
		}
	}
	
	private Views(){}
	
	private static final Display display;
	private static final Shell shell;
	private static final StackLayout view_stack;
	
	static{
		display= new Display();
		shell= new Shell(display);
		
		view_stack= new StackLayout();
		shell.setLayout(view_stack);
	}
}
