package infa2odi;


import infa.map.InfMapping;
import infa.map.Instance;
import infa.map.PowerMart;
import infa.map.Source;
import infa.map.Target;
import infa.map.Transformation;
import infa.sql.parser.InfaSQLTable;
import infa2odi.commons.MigrationODI;
import infa2odi.ui.infa2odi_ui_Objects;
import java.util.List;
import javax.swing.JOptionPane;


import odi.components.DataStoreSourceODI;
import odi.components.DataStoreTargetODI;
import odi.components.ExpressionODI;
import odi.components.FilterODI;
import odi.components.MappingODI;
import odi.components.ModelFolderSourcesODI;
import odi.components.ModelFolderTargetODI;
import odi.components.ModelODI;
import odi.components.ProjectFolderODI;
import odi.components.ProjectODI;
import odi.components.ReusableMappingODI;
import odi.components.VariableODI;
import odi.map.Project;
import oracle.odi.core.OdiInstance;
import oracle.odi.core.persistence.transaction.ITransactionStatus;
import oracle.odi.core.persistence.transaction.support.TransactionCallbackWithoutResult;
import oracle.odi.core.persistence.transaction.support.TransactionTemplate;
import oracle.odi.domain.IOdiEntity;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.adapter.relational.IDataStore;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.ReusableMapping;
import oracle.odi.domain.model.OdiDataStore;
import oracle.odi.domain.model.OdiModel;
import oracle.odi.domain.model.OdiModelFolder;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiProject;
import oracle.odi.domain.project.OdiVariable;
import oracle.odi.domain.topology.OdiContext;
import oracle.odi.domain.topology.OdiLogicalSchema;
import oracle.odi.domain.topology.finder.IOdiContextFinder;
import oracle.odi.domain.topology.finder.IOdiLogicalSchemaFinder;
import oracle.odi.publicapi.samples.SimpleOdiInstanceHandle;
import oracle.odi.domain.mapping.component.Dataset;
import oracle.odi.domain.mapping.component.DatastoreComponent;
import oracle.odi.domain.mapping.component.ExpressionComponent;
import oracle.odi.domain.mapping.component.FilterComponent;
import oracle.odi.domain.mapping.exception.MapComponentException;
import oracle.odi.domain.mapping.exception.MappingException;

public class infa_import_Objects {
    
	Project odiP;    

	private static OdiContext 		sdkContext;
	private static OdiLogicalSchema srcLogicalSchema ;
	private static OdiLogicalSchema trgLogicalSchema;
	    
	    
	private static OdiProject    	   ODI_Project				;
	private static OdiModelFolder 	   ODI_ModelFolder			;
	private static OdiFolder 		   ODI_Folder				; 
	private static OdiModel 		   ODI_Model_Project_Source ;
	private static OdiModel 		   ODI_Model_Project_Target ;
	private static Mapping 		       ODI_Mapping				;
	private static OdiVariable         ODI_Varible              ;
	private static ReusableMapping     ODI_Reusable_Mapping     ;
	private static ExpressionComponent ODI_ExpressionComponent  ;
	private static FilterComponent     ODI_FilterComponent      ;
	


	
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
				infa2odi_ui_Objects.cmbSourceLogicalSchema.add(odiSchema.getName());
				infa2odi_ui_Objects.cmbTargetLogicalSchema.add(odiSchema.getName());
				//System.out.println(odiSchema.getName() );
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
  		final InfMapping mapping 	= infaPWM.get_mapping();
	    
	    if(infaPWM != null){
	    	//Connect to ODI
	    	System.out.println("Establish connection to ODI.........");
	    	
	    	final Project    odiP 		= new Project(infaPWM);
	    	
	    	
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
							odiInstance.getTransactionalEntityManager().persist(sdkContext);
							
							  	
							System.out.println("Repository Name         : " + odiP.getName() 		);
						    System.out.println("Repository Code         : " + odiP.getCode() 		);
						    System.out.println("Repository Databasetype : " + odiP.getDatabaseType());
						    System.out.println("Repository Folder       : " + odiP.getFolder() 		);
						    
						    //Get Connection definition
		    				srcLogicalSchema = MigrationODI.getOdiLogicalSchema(odiInstance,source);
		    				trgLogicalSchema = MigrationODI.getOdiLogicalSchema(odiInstance,target);
		    				
						    
						    //Create Project
						    ODI_Project     = ProjectODI.setProject(odiInstance, odiP);
						    odiInstance.getTransactionalEntityManager().persist(ODI_Project);
							ODI_ModelFolder = ModelODI.setFolderModel(odiInstance, odiP);
							odiInstance.getTransactionalEntityManager().persist(ODI_ModelFolder);
							System.out.println("Create Project : " + ODI_Project.getName() );
							
                            //Create Project Folder
		    				ODI_Folder =  ProjectFolderODI.setProjectFolder(ODI_Project,odiP);
		    				odiInstance.getTransactionalEntityManager().persist(ODI_Folder);
		    				System.out.println("Create Project : " + ODI_Folder.getName() );
		    				
		    				
		    				
		    				System.out.println("Model Structure Source" );
		    				
		    				OdiDataStore odiDataStore = null;
		    				
		    				for(Source source:sourceList) {
		    					//Create Model folder Source 
		    					ODI_Model_Project_Source = ModelFolderSourcesODI.setOdiModelSource(srcLogicalSchema,ODI_ModelFolder,odiP, source);
			    				ODI_Model_Project_Source.setReverseContext(sdkContext);
			    				ODI_ModelFolder.addModel(ODI_Model_Project_Source);
		    					odiInstance.getTransactionalEntityManager().persist(ODI_Model_Project_Source);	
		    					
		    					//Create Table definition
		    					odiDataStore = DataStoreSourceODI.setOdiDataStoreSource(ODI_Model_Project_Source, source);
		    					odiInstance.getTransactionalEntityManager().persist(odiDataStore);
		    					
		    					System.out.println("DataStore Source : " + ODI_Model_Project_Source.getName() +" - " + odiDataStore.getName() );
		    				}
		                    
		                    System.out.println("Model Structure Target" );
		                    
		                    /*Create Models Structure for Target*/	
		                    ODI_Model_Project_Target = ModelFolderTargetODI.setOdiModelTarget(trgLogicalSchema,ODI_ModelFolder,odiP);
		                    ODI_Model_Project_Target.setReverseContext(sdkContext); 
		                    ODI_ModelFolder.addModel(ODI_Model_Project_Target);
		                    odiInstance.getTransactionalEntityManager().persist(ODI_Model_Project_Target);
		                    
		                    
		                    //Create Table definition
		                    for(Target target:targetList){
		                    	odiDataStore = DataStoreTargetODI.setOdiDataStoreTarget(ODI_Model_Project_Target, target);
		                        odiInstance.getTransactionalEntityManager().persist(odiDataStore);         
		                        System.out.println("DataStore Target : " + ODI_Model_Project_Target.getName() +" - " + odiDataStore.getName() );   
		                    }
		                    	         
		                    //Create Mapping
		                    System.out.println("Create Mapping : "  + mapping.getName());
		                    
		                    ODI_Mapping =  MappingODI.setOdiMapping(ODI_Folder, mapping );
							odiInstance.getTransactionalEntityManager().persist(ODI_Mapping);
	
							//Create Variable
							ODI_Varible = VariableODI.setVariable(ODI_Project, "My Varible2");
							odiInstance.getTransactionalEntityManager().persist(ODI_Varible);
							
							//Create Reusable Mapping (Mapplet)
							ODI_Reusable_Mapping = ReusableMappingODI.setOdiReusableMapping(ODI_Folder, "MyReusableMapping"); 
							odiInstance.getTransactionalEntityManager().persist(ODI_Reusable_Mapping);	
                         /* ---   Inside of the mapping  --- */  	
						  //Create object inside of Mapping
						  try{
		
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
							  
								//Create Expression
								for(Transformation transformation : infaMapping.getTransformationList()){
									if(transformation.getType().equals("Expression")){
	                                	ODI_ExpressionComponent = ExpressionODI.setODIExpression(ODI_Mapping, transformation);
	                                }
								}
	
	                            //Create Filter
	                            for(Transformation transformation : infaMapping.getTransformationList()){
	                                if(transformation.getType().equals("Filter")){
	                                	ODI_FilterComponent = FilterODI.setODIFilter(ODI_Mapping,transformation);
	                                }
	                            }

								
	                          //Save object from the mapping
								ODI_Mapping.onSave();
								ODI_Mapping.rebuildComponentMap();
								System.out.println("Saving Mapping Objects...");
								
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
							/* ---   Inside of the mapping  --- */
		                    System.out.println("Process successfully completed........");
						} //doInTransactionWithoutResultl

						
		            }
	            );//tx.execute(new TransactionCallbackWithoutRes

			}finally{
	    	     odiInstanceHandle.release();
	  	    } // odiInstance

			System.out.println("Close connection with ODI.");
	    }
	    
	  }//parsingFiles








	  
}



	  