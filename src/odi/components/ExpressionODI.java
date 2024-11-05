/**
 * ExpressionODI - This utility class is responsible for creating and configuring an ODI (Oracle Data Integrator)
 * expression component within a mapping. It maps transformation fields from an Informatica expression transformation
 * to an ODI expression component, including setting field names, expressions, and precision values.
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

import infa.map.TransformField;
import infa.map.Transformation;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.component.ExpressionComponent;
import oracle.odi.domain.mapping.exception.MappingException;

public class ExpressionODI {

	@SuppressWarnings("finally")
	public static ExpressionComponent setODIExpression(Mapping odiMapping, Transformation expressionInfa){
		ExpressionComponent expression = null;
		try {
			expression = new ExpressionComponent(odiMapping, expressionInfa.getName());
			System.out.println("Expression Name : " + expressionInfa.getName());
			for(TransformField field :expressionInfa.getTransformFieldList()){
				expression.addExpression(field.getName(), field.getExpression(), null , field.getPrecision() ,null);
			}
			
			// attributes should be added here.
			
			return expression;	
			
		} catch (AdapterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return null;
		}
		
	}
	
}
