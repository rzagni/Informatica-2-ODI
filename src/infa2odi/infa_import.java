package infa2odi;

import infa.map.Connector;
import infa.map.InfMapping;
import infa.map.InfaTransformationType;
import infa.map.Instance;
import infa.map.PowerMart;
import infa.map.Source;
import infa.map.Target;
import infa.map.Transformation;
import infa.sql.parser.InfaSQLColumn;
import infa.sql.parser.InfaSQLTable;
import infa2odi.commons.MigrationODI;
import infa2odi.ui.infa2odi_ui;


import java.util.List;
import javax.swing.JOptionPane;
import odi.map.ODIComponentType;
import odi.map.ODIConnectTo;
import odi.map.Project;
import oracle.odi.core.OdiInstance;
import oracle.odi.core.persistence.transaction.ITransactionStatus;
import oracle.odi.core.persistence.transaction.support.TransactionCallbackWithoutResult;
import oracle.odi.core.persistence.transaction.support.TransactionTemplate;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.adapter.project.IMapComponent;
import oracle.odi.domain.adapter.relational.IDataStore;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.component.*;
import oracle.odi.domain.mapping.exception.MapComponentException;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.model.OdiDataStore;
import oracle.odi.domain.model.OdiModel;
import oracle.odi.domain.model.OdiModelFolder;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiProject;
import oracle.odi.domain.topology.OdiContext;
import oracle.odi.domain.topology.OdiLogicalSchema;
import oracle.odi.domain.topology.finder.IOdiContextFinder;
import oracle.odi.domain.topology.finder.IOdiLogicalSchemaFinder;
import oracle.odi.publicapi.samples.SimpleOdiInstanceHandle;



public class infa_import {
    
	Project odiP;    

	private static OdiContext 		sdkContext;
	private static OdiLogicalSchema srcLogicalSchema ;
	private static OdiLogicalSchema trgLogicalSchema;
	    
	    
	private static OdiProject 		ODI_Project				;
	private static OdiModelFolder 	ODI_ModelFolder			;
	private static OdiFolder 		ODI_Folder				; 
	private static OdiModel 		ODI_Model_Project_Source;
	private static OdiModel 		ODI_Model_Project_Target;
	private static Mapping 			ODI_Mapping				; 







	
	
	public static SimpleOdiInstanceHandle login(String login_user, String login_pwd, String master_user, String master_pwd){
		try{
			@SuppressWarnings("deprecation")
			final SimpleOdiInstanceHandle odiInstanceHandle = SimpleOdiInstanceHandle.create(
																        		"jdbc:oracle:thin:@localhost:1521:ORCL",  //JDBC driver URL
																                "oracle.jdbc.OracleDriver",               //Driver
																                master_user.trim(),                       //Your Master repository username should come hereODI_Model_Target
																                master_pwd,                               //Your Master repository password should come here
																                "WORKREP",                                //Work repository name should come here
																                login_user.trim(),                        //ODI login username
																                login_pwd								  //ODI login password
																                );                               		
			
			final OdiInstance odiInstance = odiInstanceHandle.getOdiInstance();
			
			Object[] logicalSchemas =  ((IOdiLogicalSchemaFinder)odiInstance.getTransactionalEntityManager().getFinder(OdiLogicalSchema.class)).findAll().toArray();
			
			for (Object schema : logicalSchemas) {
				OdiLogicalSchema odiSchema = (OdiLogicalSchema) schema;
				infa2odi_ui.cmbSourceLogicalSchema.add(odiSchema.getName());
				infa2odi_ui.cmbTargetLogicalSchema.add(odiSchema.getName());
				System.out.println(odiSchema.getName() );
			}
			return odiInstanceHandle;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Login unsuccessfull");
			return null;
		}
	}
	
	





	public static void parsingFiles(SimpleOdiInstanceHandle odiInstanceHandle,String src_schema, String trg_schema, String fileSelected)  {

  		final String source = src_schema;
  		final String target = trg_schema;
  		final OdiInstance odiInstance = odiInstanceHandle.getOdiInstance();
		
  		PowerMart infaPWM = new PowerMart(fileSelected);
	    
	    
	    if(infaPWM != null){
	    	//Connect to ODI
	    	System.out.println("Establish connection to ODI.........");
	    	
	    	final Project    odiP 		= new Project(infaPWM);
	    	final InfMapping mapping 	= infaPWM.get_mapping();
	    	
	    	final List<Source>         sourceList 			= infaPWM.getSourceList();
			final List<Target>         targetList 			= infaPWM.getTargetList();
			final InfMapping           infaMapping          = infaPWM.get_mapping();
			final List<Transformation> sourcequalifierList 	= infaMapping.getSourceQualifierList();
			
			

			
			//Create Structure on ODI Projects, Models, Topology
			try{
				
				TransactionTemplate tx = new TransactionTemplate(odiInstance.getTransactionManager());
	            tx.execute(new TransactionCallbackWithoutResult(){
						protected void doInTransactionWithoutResult(ITransactionStatus arg0) {
							
							sdkContext = ((IOdiContextFinder)odiInstance.getTransactionalEntityManager()
									.getFinder(OdiContext.class)).findDefaultContext();
							sdkContext.setDefaultContext(true);
							
						    		    	
							System.out.println("Repository Name         : " + odiP.getName() 		);
						    System.out.println("Repository Code         : " + odiP.getCode() 		);
						    System.out.println("Repository Databasetype : " + odiP.getDatabaseType());
						    System.out.println("Repository Folder       : " + odiP.getFolder() 		);
						    
							ODI_Project     = MigrationODI.setProject    (odiInstance, odiP);
							ODI_ModelFolder = MigrationODI.setFolderModel(odiInstance, odiP);
							
							odiInstance.getTransactionalEntityManager().persist(ODI_Project);
							odiInstance.getTransactionalEntityManager().persist(ODI_ModelFolder);
		    				odiInstance.getTransactionalEntityManager().persist(sdkContext);
		    				
		    				
		    				
		    				ODI_Folder =  MigrationODI.setProjectFolder(ODI_Project,odiP);
		    				odiInstance.getTransactionalEntityManager().persist(ODI_Folder);
		    				
		    				//need to create connection
		    				srcLogicalSchema = MigrationODI.getOdiLogicalSchema(odiInstance,source);
		    				trgLogicalSchema = MigrationODI.getOdiLogicalSchema(odiInstance,target);
		    				
		    				System.out.println("Model Structure Source" );
		    				
		    				for(Source source:sourceList) {
		    					
		    					/*Create Models Structure for Source*/
		    					ODI_Model_Project_Source = MigrationODI.setOdiModelSource(srcLogicalSchema,ODI_ModelFolder,odiP, source);
		    					ODI_Model_Project_Source.setReverseContext(sdkContext);
		    					ODI_ModelFolder.addModel(ODI_Model_Project_Source);
		    					odiInstance.getTransactionalEntityManager().persist(ODI_Model_Project_Source);
		    					System.out.println("Model Folder Source :" + ODI_Model_Project_Source.getName());
		    					
		    					// Create table definition
		    					OdiDataStore srcDatastore = MigrationODI.setOdiDataStoreSource(ODI_Model_Project_Source, source);
		    					odiInstance.getTransactionalEntityManager().persist(srcDatastore);
		    					System.out.println("DataStore Source :" + srcDatastore.getName());

		    				}
		    				
		                    
		                    System.out.println("Model Structure Target" );
		                    
		                    //Target
		                    /*Create Models Structure for Target*/	
		    				ODI_Model_Project_Target = MigrationODI.setOdiModelTarget(trgLogicalSchema,ODI_ModelFolder,odiP);
		    				ODI_Model_Project_Target.setReverseContext(sdkContext); 
		                    ODI_ModelFolder.addModel(ODI_Model_Project_Target);
		                    odiInstance.getTransactionalEntityManager().persist(ODI_Model_Project_Target);      
		                    System.out.println("Model Folder Target :" + ODI_Model_Project_Target.getName());
		                    
		                    //Create table definition
		                    //Implement a loop for each source in the mapping
		                    for(Target target:targetList){
		                    	OdiDataStore trgDatastore = MigrationODI.setOdiDataStoreTarget(ODI_Model_Project_Target, target);
		                        odiInstance.getTransactionalEntityManager().persist(trgDatastore);   
		                        System.out.println("DataStore Target :" + trgDatastore.getName());
		                    }
		                    	                
		                    
		                    //create mapping
		                    System.out.println("Mapping Structure"  );
		                    ODI_Mapping =  MigrationODI.setOdiMapping(ODI_Folder, mapping.getName() );
							odiInstance.getTransactionalEntityManager().persist(ODI_Mapping);
		                    System.out.println("Mapping : "  +  ODI_Mapping.getName() );
		                    
		                    //create variable
		                    
	
	
							 
	
							//add element on mapping
							try {
		
								System.out.println("- Target" );
								//add target table on mapping
								for(Target target:targetList){
									System.out.println( "Target name :" + target.getName());
									ODI_Model_Project_Target = MigrationODI.setOdiModelTarget(srcLogicalSchema, ODI_ModelFolder, odiP );
									IDataStore trgIDatastore = MigrationODI.getDatastore(ODI_Model_Project_Target.getDataStores(), target.getName());
								
									//adding target instances
									for(Instance targetInstance : target.getTargetInstanceAliases()){
										DatastoreComponent trgDatastoreComponent = new DatastoreComponent(ODI_Mapping, trgIDatastore);
										trgDatastoreComponent.setName(targetInstance.getName());
									}
	
								}
		
									
		 				    //Create dataset for each source qualifier
								System.out.println("- Source" );
								Dataset dataset1 = null;
								for(Transformation o :sourcequalifierList) {
									System.out.println("Source Qualifier : " + o.getName());
									dataset1 = new Dataset(ODI_Mapping, o.getName() );
									dataset1.setAlias(o.getName());
									
									for(InfaSQLTable sqlTable : o.getTableList() ){
										for(Source source:sourceList){												
											if(source.getName().equals(sqlTable.getTableName())){
												ODI_Model_Project_Source = MigrationODI.setOdiModelSource(srcLogicalSchema,ODI_ModelFolder,odiP, source);
												IDataStore srcIDatastore = MigrationODI.getDatastore(ODI_Model_Project_Source.getDataStores(), sqlTable.getTableName());
												dataset1.addSource(srcIDatastore, null);
												
												break;
											}
										}
									}	
									//add where clause
									
									if(!o.getWhereClause().isEmpty()){
										dataset1.addJoin(o.getWhereClause());
									}
									
								}
	
								
								final List<Connector> sourceDefinitionConnectorList = infaMapping.getConnectorListByFromInstanceType(InfaTransformationType.SOURCE_DEFINITION);
								final List<Connector> mappingConnectorList  = infaMapping.getConnectorListExceptByFromInstanceType(InfaTransformationType.SOURCE_DEFINITION);
								
								ODIConnectTo connectTo;
								for(Connector sdConnector : sourceDefinitionConnectorList){
									connectTo = new ODIConnectTo(sdConnector);
									for(int i = 0; i < mappingConnectorList.size();i++){
										if(connectTo.get_tmpConnector() == null){
											if(connectTo.getConnector().getToInstanceType() .equals(mappingConnectorList.get(i).getFromInstanceType()) && 
													connectTo.getConnector().getToInstance().equals(mappingConnectorList.get(i).getFromInstance    ()) && 
													connectTo.getConnector().getToField()   .equals(mappingConnectorList.get(i).getFromField       ())
											   ){
												connectTo.set_tmpConnector(mappingConnectorList.get(i));
												i=-1;
											}
										}else{
											if(connectTo.get_tmpConnector().getToInstanceType() .equals(mappingConnectorList.get(i).getFromInstanceType()) && 
													connectTo.get_tmpConnector().getToInstance().equals(mappingConnectorList.get(i).getFromInstance    ()) && 
													connectTo.get_tmpConnector().getToField()   .equals(mappingConnectorList.get(i).getFromField       ())
											   ){
												connectTo.set_tmpConnector(mappingConnectorList.get(i));
												if(connectTo.get_tmpConnector().getToInstanceType().equals(InfaTransformationType.TARGET_DEFINITION)){
													break;
												}
												i = -1;
											}
										}
									}
									
									for(IMapComponent elementDataSet :  ODI_Mapping.getAllComponentsOfType(ODIComponentType.DATASET)){
										boolean connectionCreated = false;
										Dataset dataset = (Dataset) elementDataSet;
										if(dataset.getName().equals(connectTo.getDatasetName())){
											for(IMapComponent elementDataStore :  ODI_Mapping.getAllComponentsOfType(ODIComponentType.DATASTORE)){
												DatastoreComponent dataStore = (DatastoreComponent) elementDataStore;
												if(dataStore.getName().equals(connectTo.getTargetTableName())){
													for(Transformation sqTransformation : sourcequalifierList){
														if(sqTransformation.getName().equals(connectTo.getDatasetName())){
															for(InfaSQLColumn sqColumn : sqTransformation.getColumnsListByTable(connectTo.getSourceTableName())){
																if(connectTo.getSourceColumnName().equals(sqColumn.getColumnAlias())){
																	connectTo.setSourceColumnName(sqColumn.getColumnName());
																	break;
																}
															}
															break;
														}
													}
													connectTo.getTargetTableName();
													dataset.connectTo(dataStore);
													dataStore.setAttributeExpressionText(connectTo.getTargetColumnName(), connectTo.getDatasetName() + "." + connectTo.getSourceTableName()+ "." + connectTo.getSourceColumnName());
													connectionCreated = true;
													break;
												}
											}
										}
										if(connectionCreated) break;
									}
									
									//break;
									
								}
								
								
								/*Dataset dataset11 = new Dataset(ODI_Mapping,"SQ_CBMS_PARTY_ASSIGNMENTS");
								//IDataStore srcIDatastore = MigrationODI.getDatastore(ODI_Model_Project_Source.getDataStores(), "HZ_PARTIES");
								dataset11.addSource(srcIDatastoress, null);
								DatastoreComponent trgDatastoreComponent = new DatastoreComponent(ODI_Mapping, trgIDatastore);
								dataset11.connectTo(trgDatastoreComponent);
								//add expression
								trgDatastoreComponent.setAttributeExpressionText("PARTY_ID", "SQ_CBMS_PARTY_ASSIGNMENTS.HZ_PARTIES.PARTY_ID" );
								*/
								
								/*for(IMapComponent l :  ODI_Mapping.getAllComponents()){
									//MapComponentType x = (MapComponentType)l;
									//System.out.println(x.getName());
									System.out.println(l.getName());
								}
								for(IMapComponent l :  ODI_Mapping.getAllComponentsOfType(DatastoreComponent.COMPONENT_TYPE_NAME)){
									System.out.println(l.getName());
								}*/
								
								//Save object from the mapping
								ODI_Mapping.onSave();
								ODI_Mapping.rebuildComponentMap();
														
								
								
							} catch (AdapterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}catch (MapComponentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
							
							//-------
							odiInstance.getTransactionalEntityManager().persist(ODI_Mapping);      
							
		                    System.out.println("Process successfully completed........");
							
							
						} //doInTransactionWithoutResult
		            }
	            );//tx.execute(new TransactionCallbackWithoutRes

			}finally{
	    	     odiInstanceHandle.release();
	  	    } // odiInstance

			System.out.println("Close connection with ODI.");
	    }
	    
	  }//parsingFiles
	  
}



	  