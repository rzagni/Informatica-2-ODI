package odi.components;

import infa2odi.commons.MigrationODI;
import odi.map.Project;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiProject;

public class ProjectFolderODI {

	public static OdiFolder setProjectFolder(OdiProject  ODI_Project,Project project){
		OdiFolder ODI_Folder    = MigrationODI.getOdiProjectFolder(ODI_Project,project);
		if (ODI_Folder == null){
			ODI_Folder = new OdiFolder (ODI_Project,project.getFolder()); 
		}
		return ODI_Folder;
	}
	
}
