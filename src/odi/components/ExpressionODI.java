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