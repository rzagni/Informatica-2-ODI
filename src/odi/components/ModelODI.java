/**
 * ModelODI - This utility class handles the creation and configuration of an ODI (Oracle Data Integrator)
 * model folder for a specified project. It checks if a model folder already exists for the project, 
 * creating a new one if necessary.
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
