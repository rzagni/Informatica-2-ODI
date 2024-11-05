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
