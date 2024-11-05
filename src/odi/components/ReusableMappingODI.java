package odi.components;

import infa2odi.commons.MigrationODI;
import oracle.odi.domain.mapping.ReusableMapping;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.project.OdiFolder;

public class ReusableMappingODI {

	//RegetOdiReusableMapping;
	public static ReusableMapping setOdiReusableMapping(OdiFolder sdkFolder, String reusableMappingName){
		ReusableMapping reusableMapping = MigrationODI.getOdiReusableMapping(sdkFolder,reusableMappingName);
		if(reusableMapping == null){
			try {
				reusableMapping = new ReusableMapping(reusableMappingName, sdkFolder);
				reusableMapping.setBusinessName(reusableMappingName);
				reusableMapping.setDescription("Import Infa2ODI");
			} catch (MappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			reusableMapping = MigrationODI.deleteOdiReusableMappingObjects(reusableMapping);
		}
			
	  return reusableMapping;	
	}
	
}

package odi.components;

import infa2odi.commons.MigrationODI;
import oracle.odi.domain.mapping.ReusableMapping;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.project.OdiFolder;

public class ReusableMappingODI {

	//RegetOdiReusableMapping;
	public static ReusableMapping setOdiReusableMapping(OdiFolder sdkFolder, String reusableMappingName){
		ReusableMapping reusableMapping = MigrationODI.getOdiReusableMapping(sdkFolder,reusableMappingName);
		if(reusableMapping == null){
			try {
				reusableMapping = new ReusableMapping(reusableMappingName, sdkFolder);
				reusableMapping.setBusinessName(reusableMappingName);
				reusableMapping.setDescription("Import Infa2ODI");
			} catch (MappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			reusableMapping = MigrationODI.deleteOdiReusableMappingObjects(reusableMapping);
		}
			
	  return reusableMapping;	
	}
	
}
