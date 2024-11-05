package odi.components;

import infa.map.InfMapping;
import infa2odi.commons.MigrationODI;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.project.OdiFolder;

public class MappingODI {

	public static Mapping setOdiMapping(OdiFolder sdkFolder, InfMapping infaMapping){
		Mapping mapping = MigrationODI.getOdiMapping(sdkFolder,infaMapping.getName());
		
		if (mapping == null){
			try {
				mapping = new Mapping(infaMapping.getName(), sdkFolder);
				mapping.setDescription(infaMapping.getDescription());
				mapping.setBusinessName(infaMapping.getName());
				
			} catch (AdapterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			mapping = MigrationODI.deleteOdiMappingObjects(mapping);
		}
		
		return mapping;
	}
}
