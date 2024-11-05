package odi.components;

import infa.map.Source;
import infa2odi.commons.MigrationODI;
import odi.map.Project;
import oracle.odi.domain.model.OdiModel;
import oracle.odi.domain.model.OdiModelFolder;
import oracle.odi.domain.topology.OdiLogicalSchema;

public class ModelFolderSourcesODI {
	public static OdiModel setOdiModelSource(OdiLogicalSchema odiLogicalSchema,OdiModelFolder odiModelFolder,Project project, Source source ){
		OdiModel odiModel = null; 
		String modelName  = null;
		String modelCode  = null;

    	modelName = project.getFolderModelCode() + "-SRC-" + source.getDBDName();
		modelCode = project.getFolderModelCode() + "_SRC_" + source.getDBDName();
		odiModel = MigrationODI.getOdiModel(odiModelFolder, modelCode);
	    if (odiModel == null){
	      	odiModel = new OdiModel(odiLogicalSchema, modelName , modelCode);
	    }
    	return odiModel;
	}	
	
}
