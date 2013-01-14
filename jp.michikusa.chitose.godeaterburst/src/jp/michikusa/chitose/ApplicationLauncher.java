package jp.michikusa.chitose;

import jp.michikusa.chitose.util.R;
import jp.michikusa.chitose.util.ResourcePool;
import jp.michikusa.chitose.util.Views;
import jp.michikusa.chitose.view.item.ItemEditView;

public final class ApplicationLauncher{
	public static void main(String[] args){
		Views.shell().setText(String.format("%s - %s", R.Application.name, R.Application.version));
		Views.shell().setMinimumSize(R.Application.minimum_width, R.Application.minimum_height);
		Views.shell().setSize(Views.shell().getMinimumSize());
		
		// Views.openView(new ItemSearchView());
		Views.openView(new ItemEditView());
		Views.runner().run();
		
		ResourcePool.dispose();
	}
	
	private ApplicationLauncher(){}
}
