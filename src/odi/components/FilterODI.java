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
