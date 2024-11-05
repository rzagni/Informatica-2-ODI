package odi.components;

import odi.map.Project;
import infa2odi.commons.MigrationODI;
import oracle.odi.core.OdiInstance;
import oracle.odi.domain.project.OdiProject;

public class ProjectODI {
	
	public static OdiProject setProject(OdiInstance odiInstance, Project project) {
		// TODO Auto-generated constructor stub
		OdiProject ODI_Project = MigrationODI.getODIProject(odiInstance, project);
		
		if (ODI_Project == null){
        	ODI_Project = new OdiProject(project.getName(), project.getCode());
        }
		return ODI_Project;
	}	
	
	

	
}
