/**
 * VariableODI - This utility class manages the creation and configuration of an ODI (Oracle Data Integrator)
 * variable within a specified project. It checks if a variable with the specified name already exists and 
 * creates a new variable if necessary.
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
import oracle.odi.domain.project.OdiProject;
import oracle.odi.domain.project.OdiVariable;

public class VariableODI {
	//	private static OdiVariable      ODI_Varible;
	public static OdiVariable setVariable(OdiProject odiProject	, String variableName){
		OdiVariable odiVariable = null;
		odiVariable = MigrationODI.getOdiVariable(odiProject,variableName);
		if (odiVariable == null){
		  odiVariable = new OdiVariable(odiProject, variableName );
		  odiVariable.setDescription("Import Variable Infa2ODI");
		}
		return odiVariable;
	}

}
