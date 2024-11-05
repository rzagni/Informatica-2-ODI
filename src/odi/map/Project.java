/**
 * Project - This class represents a project in ODI (Oracle Data Integrator) mapped from an Informatica PowerMart repository.
 * It defines project-specific attributes such as name, code, database type, and folder, and provides methods for generating
 * model names and codes for source and target models within the project.
 * 
 * <p>
 * Licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * You may use, modify, and share this code for non-commercial purposes, provided you give appropriate
 * credit, indicate if changes were made, and distribute any modified work under the same license.
 * </p>
 *
 * @author Renzo Zagni
 * @license Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License
 * @see <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons License</a>
 */

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
