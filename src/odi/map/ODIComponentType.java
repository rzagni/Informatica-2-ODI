/**
 * ODIComponentType - This utility class defines constants representing various ODI (Oracle Data Integrator) 
 * component types. Each constant corresponds to a specific component type name used in ODI mappings, 
 * enabling easier reference and standardization within code.
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


package odi.map;

import oracle.odi.domain.mapping.component.*;

public class ODIComponentType {
	public static final String AGGREGATE 		= AggregateComponent.COMPONENT_TYPE_NAME      ;
	public static final String DATASET 			= Dataset.COMPONENT_TYPE_NAME                 ;
	public static final String DATASTORE 		= DatastoreComponent.COMPONENT_TYPE_NAME      ;
	public static final String DISTINCT 		= DistinctComponent.COMPONENT_TYPE_NAME       ;
	public static final String EXPRESSION 		= ExpressionComponent.COMPONENT_TYPE_NAME     ;
	public static final String FILE 			= FileComponent.COMPONENT_TYPE_NAME           ;
	public static final String FILTER 			= FilterComponent.COMPONENT_TYPE_NAME         ;
	public static final String INPUTSIGNATURE 	= InputSignature.COMPONENT_TYPE_NAME          ;
	public static final String JOIN 			= JoinComponent.COMPONENT_TYPE_NAME           ;
	public static final String LOOKUP 			= LookupComponent.COMPONENT_TYPE_NAME         ;
	public static final String OUTPUTSIGNATURE 	= OutputSignature.COMPONENT_TYPE_NAME         ;
	public static final String PIVOT 			= PivotComponent.COMPONENT_TYPE_NAME          ;
	public static final String REUSABLEMAPPING 	= ReusableMappingComponent.COMPONENT_TYPE_NAME;
	public static final String SET 				= SetComponent.COMPONENT_TYPE_NAME            ;
	public static final String SORTER 			= SorterComponent.COMPONENT_TYPE_NAME         ;
	public static final String SPLITTER 		= SplitterComponent.COMPONENT_TYPE_NAME       ;
	public static final String SUBQUERYFILTER 	= SubqueryFilterComponent.COMPONENT_TYPE_NAME ;
	public static final String TABLEFUNCTION 	= TableFunctionComponent.COMPONENT_TYPE_NAME  ;
	public static final String UNPIVOT 			= UnpivotComponent.COMPONENT_TYPE_NAME        ;
}
