package odi.components;

import infa2odi.commons.MigrationODI;
import odi.map.Project;
import oracle.odi.domain.model.OdiModel;
import oracle.odi.domain.model.OdiModelFolder;
import oracle.odi.domain.topology.OdiLogicalSchema;

public class ModelFolderTargetODI {
	
	public static OdiModel setOdiModelTarget(OdiLogicalSchema odiLogicalSchema,OdiModelFolder odiModelFolder,Project project){
		OdiModel odiModel = null; 
		String modelname = project.getFolderModelCode() + "-TRG";
		String modelcode = project.getFolderModelCode() + "_TRG";

		odiModel = MigrationODI.getOdiModel(odiModelFolder, modelcode);
        if (odiModel == null){
        	odiModel = new OdiModel(odiLogicalSchema, modelname , modelcode);
        }
    	return odiModel;
	}

}
