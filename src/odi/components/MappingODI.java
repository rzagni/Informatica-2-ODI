/**
 * MappingODI - This utility class is responsible for creating and configuring an ODI (Oracle Data Integrator)
 * mapping component within a specified folder. It maps information from an Informatica mapping object to an ODI
 * mapping, setting properties such as description and business name. If the mapping already exists, it resets its
 * components before reconfiguring.
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
