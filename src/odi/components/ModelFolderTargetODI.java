/**
 * ModelFolderTargetODI - This utility class manages the creation and configuration of an ODI (Oracle Data Integrator)
 * target model within a specified folder. It maps target information from an ODI project to an ODI model, setting
 * the model name and code based on project details for target mappings.
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
