/**
 * ProjectFolderODI - This utility class manages the creation and configuration of an ODI (Oracle Data Integrator)
 * project folder for a specified project. It checks if a folder already exists within the project and creates 
 * a new one if necessary.
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
