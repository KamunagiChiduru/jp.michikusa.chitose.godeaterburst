package jp.michikusa.chitose.controller;

public class ItemEditController implements Controller{
	@Override
	public SaveController saveController(){
		return new SaveController();
	}
	
	@Override
	public ReadController readController(){
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SearchController searchController(){
		// TODO Auto-generated method stub
		return null;
	}
	
	private static class SaveController implements Controller.SaveController{
		@Override
		public boolean onSavePre(){
			System.out.println("on save pre");
			return true;
		}
		
		@Override
		public boolean onSave(){
			System.out.println("on save");
			return true;
		}
		
		@Override
		public boolean onSavePost(){
			System.out.println("on save post");
			return true;
		}
	}
}
