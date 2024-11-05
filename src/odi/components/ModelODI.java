package odi.components;

import infa2odi.commons.MigrationODI;
import oracle.odi.core.OdiInstance;
import oracle.odi.domain.model.OdiModelFolder;
import odi.map.Project;

public class ModelODI {

	public static  OdiModelFolder setFolderModel(OdiInstance odiInstance, Project project) {
		// TODO Auto-generated constructor stub
		OdiModelFolder ODI_ModelFolder = MigrationODI.getODIModelFolder(odiInstance,project);
		if (ODI_ModelFolder == null){
        	ODI_ModelFolder = new OdiModelFolder(project.getName());
        }
		return ODI_ModelFolder;
	}

}
