package jp.michikusa.chitose.controller;

public interface Controller{
	interface SaveController{
		boolean onSavePre();
		
		boolean onSave();
		
		boolean onSavePost();
	}
	
	interface ReadController{
		boolean onReadPre();
		
		boolean onRead();
		
		boolean onReadPost();
	}
	
	interface SearchController{
		boolean onSearchPre();
		
		boolean onSearch();
		
		boolean onSearchPost();
	}
	
	SaveController saveController();
	
	ReadController readController();
	
	SearchController searchController();
}
