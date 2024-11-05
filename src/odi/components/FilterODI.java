/**
 * FilterODI - This utility class manages the creation and configuration of an ODI (Oracle Data Integrator)
 * filter component within a mapping. It maps filter conditions from an Informatica transformation to an
 * ODI filter component, setting up the filter condition, description, and attributes as defined in the source.
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

import infa.map.TableAttribute;
import infa.map.Transformation;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.component.FilterComponent;
import oracle.odi.domain.mapping.exception.MappingException;

public class FilterODI {

	
	@SuppressWarnings("finally")
	public static FilterComponent setODIFilter(Mapping odiMapping, Transformation filterInfa){
		FilterComponent filter = null;
		try {
			filter = new FilterComponent(odiMapping, filterInfa.getName());
			System.out.println("Filter Name : " + filterInfa.getName());
			
			for(TableAttribute condition :filterInfa.getTableAttributeList()){
				if(condition.getName().equals("Filter Condition")){
					String conditionString = condition.getValue();
					conditionString = conditionString.replace("IIF(", "");
					conditionString = conditionString.substring(0, conditionString.indexOf(","));
					filter.setFilterCondition(conditionString);
					filter.setDescription(condition.getValue());
				}
			}
			// add attributes here
			
			return filter;
		} catch (AdapterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return null;
		}
		
	}
	
}
