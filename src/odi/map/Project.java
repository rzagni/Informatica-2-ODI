package odi.map;

import infa.map.PowerMart;

public class Project {
	/*
	 * Defining Global ODI Attributes names for Project node
	 */
	
	private String Name;
	private String Code;
	private String DatabaseType ;
	private String Folder;
	private String SourceModel;
	private String TargetModel;
	
		
	
	public Project(PowerMart infaPWM ){
		Name = infaPWM.get_repository().getName();
		Code = Name.replace(" ", "_").toUpperCase();
		DatabaseType = infaPWM.get_repository().getDatabaseType() ;
		Folder = infaPWM.get_repository().get_folder().getName();
		SourceModel = "SOURCE";
		TargetModel = "TARGET";
	}
	
	
	public String getName() {
		return Name;
	}
	
	public String getCode() {
		return Code;
	}
	
	public String getDatabaseType() {
		return DatabaseType;
	}
	
	public String getFolder() {
		return Folder;
	}
	
	public String getSourceModelName(){
		return SourceModel;
	}
	
	public String getFolderModelCode(){
		return Folder.trim().replace(" ", "_").toUpperCase();
	}
	
	
	public String getSourceModelCode(){
		return Folder.trim().replace(" ", "_").toUpperCase()+"_"+SourceModel;
	}
	
	public String getTargetModelName(){
		return TargetModel;
	}
	
	public String getTargetModelCode(){
		return Folder.trim().replace(" ", "_").toUpperCase() +"_"+ TargetModel;
	}
	
	public String getSubMolderSource(String submodel){
		return getFolder().trim().replace(" ", "_").toUpperCase()+ "_" + getSourceModelName() +"_"+ submodel.trim().replace(" ", "_").toUpperCase();
        	
	}
	
	public String getSubMolderTarget(String submodel){
		return getFolder().trim().replace(" ", "_").toUpperCase()+ "_" + getTargetModelName() +"_"+ submodel.trim().replace(" ", "_").toUpperCase();
        	
	}
	
}
