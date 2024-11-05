/**
 * ProjectODI - This utility class manages the creation and configuration of an ODI (Oracle Data Integrator)
 * project. It checks if a project already exists within the ODI instance and creates a new project if necessary.
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
