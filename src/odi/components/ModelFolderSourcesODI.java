/**
 * ModelFolderSourcesODI - This utility class manages the creation and configuration of an ODI (Oracle Data Integrator)
 * source model within a specified folder. It maps information from an Informatica source object and a project 
 * to an ODI model, setting the model name and code based on the source and project details.
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
