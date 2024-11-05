package Demo;


import java.util.Collection;
import java.util.List;

import oracle.odi.core.OdiInstance;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiPackage;
import oracle.odi.domain.project.OdiProject;
import oracle.odi.domain.project.OdiSequence;
import oracle.odi.domain.project.OdiUserFunction;
import oracle.odi.domain.project.OdiUserProcedure;
import oracle.odi.domain.project.finder.IOdiProjectFinder;
import oracle.odi.publicapi.samples.SimpleOdiInstanceHandle;
import oracle.odi.domain.adapter.project.IKnowledgeModule;
import oracle.odi.domain.adapter.project.IProcedureOption;
import oracle.odi.domain.mapping.IMapComponent;
import oracle.odi.domain.mapping.MapComponentType;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.ReusableMapping;
import oracle.odi.domain.mapping.exception.MapComponentException;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.mapping.physical.MapPhysicalDesign;
import oracle.odi.domain.mapping.physical.MapPhysicalNode;
import oracle.odi.domain.mapping.properties.Property;
import oracle.odi.domain.model.OdiDataStore;
import oracle.odi.domain.model.OdiModel;
import oracle.odi.domain.model.finder.IOdiModelFinder;
import oracle.odi.domain.project.OdiVariable;
import oracle.security.jps.az.internal.management.pd.PD;


public class MyFirstSDKCode {

	private static Object[] ODI_Projects;
	private static OdiProject ODI_Project;
	private static Collection<OdiFolder> ODI_Folders;
	private static OdiFolder ODI_Folder;
	private static Collection<OdiPackage> ODI_Packages;
	private static OdiPackage ODI_Package;
	private static List<Mapping> ODI_Mappings;
	private static Mapping ODI_Mapping;    
	private static Collection<OdiUserProcedure> ODI_Procedures;
	private static OdiUserProcedure ODI_Procedure;
	private static Collection<OdiVariable> ODI_Variables;
	private static OdiVariable ODI_Variable;
	private static Collection<OdiSequence> ODI_Sequences;
	private static OdiSequence ODI_Sequence;
	private static Collection<OdiUserFunction> ODI_Functions;
	private static OdiUserFunction ODI_Function;
	private static Object[] ODI_Models;
	private static OdiModel ODI_Model; 
	private static Collection<OdiDataStore> ODI_DataStores;
	private static OdiDataStore ODI_DataStore;
	private static Collection<ReusableMapping> ODI_ResusableMappings;
	private static ReusableMapping ODI_ResusableMapping;
	
    
	public static void main(String[] args) throws MappingException
    {
     //* code 

       
		@SuppressWarnings("deprecation")
		final SimpleOdiInstanceHandle odiInstanceHandle = SimpleOdiInstanceHandle
        .create(
        		"jdbc:oracle:thin:@localhost:1521:ORCL",  //JDBC driver URL
                "oracle.jdbc.OracleDriver",  //Driver
                "prod_odi_repo", //Your Master repository username should come here
                "oracle", //Your Master repository password should come here
                "WORKREP", //Work repository name should come here
                "SUPERVISOR",  //ODI login username
                "SUPERVISOR");   //ODI login password
		
		// Allocate an odisinstance of the name
		final OdiInstance odiInstance = odiInstanceHandle.getOdiInstance();
		
		try{
           
		   //Get lists of Projects	
			ODI_Projects = (((IOdiProjectFinder)odiInstance.getTransactionalEntityManager().
					getFinder(OdiProject.class)).findAll().toArray());
			
			System.out.println("Projects Details");
			
			for (Object object : ODI_Projects) {
				ODI_Project =(OdiProject) object;
			     
			     // Here i am casting the object as OdiProject
			     System.out.println("Project Name : "+ ODI_Project.getName());
			     			     
			     //Get lists of all Folders
			     ODI_Folders = ODI_Project.getFolders();
			     for (Object object1 : ODI_Folders) {
			    	 ODI_Folder = (OdiFolder) object1;
			    
			    	 // Here i am casting the object as OdiFolder
			    	 System.out.println("Folder Name : "+ ODI_Folder.getName());
			    	 
			    	 //Get lists of packages 
			    	 ODI_Packages = ODI_Folder.getPackages();
			    	 for (Object object2 : ODI_Packages) {
			    		 ODI_Package =  (OdiPackage) object2;
			    		 //Here i am casting the object as OdiFolder
				    	 System.out.println("Package Name : "+ ODI_Package.getName());
			      	 }
			    	 
			    	 
			    	 //Get list of Mapping 
			    	 ODI_Mappings =  ODI_Folder.getMappings();
			    	 for (Object object3 : ODI_Mappings) {
			    		 ODI_Mapping =  (Mapping) object3;
			    		 //Here i am casting the object as OdiFolder
				    	 System.out.println("Mapping Name : "+ ODI_Mapping.getName());
				    	 
				    	 try {
							List<IMapComponent> IMappcomp = ODI_Mapping.getAllComponents();
							
							 System.out.println("Mapping Name : "+ IMappcomp );

					    	 					    	 
							 //ODI_Mapping.getPhysicalDesigns().get(0).getPhysicalNodes().get(0).getp
							 for ( MapPhysicalDesign  x : ODI_Mapping.getPhysicalDesigns() ){
								 
								 
								 for (MapPhysicalNode  x1 : x.getPhysicalNodes()){
									 System.out.println("Physical  KM : "+ x1.getName() );
									 
									 if (x1.isXKMNode())
									 for ( IProcedureOption x2 : x1.getXKM().getProcedureOptions() ){
										 System.out.println("Physical  node  : "+ x2.getName() );
										 System.out.println("Physical  node  : "+ x2.getDescription() );
										 System.out.println("Physical  node  : "+ x2.getProcedureOptionType() );
										 System.out.println("Physical  node  : "+ x2.getDefaultOptionValue().toString() );
										 System.out.println("Physical  node  : "+ x2.getHelp());
									 }
									 
									 
									 
									 
								 }

								 } 
							 

							 
							 
						} catch (MapComponentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			      	 }
			    	 
			    	 
			    	 
			    				    	 
			    	 
			    	 //Get list of User Procedure
			    	 ODI_Procedures = ODI_Folder.getUserProcedures();
			    	 for (Object object4 : ODI_Procedures) {
			    		 ODI_Procedure =  (OdiUserProcedure) object4;
			    		 //Here i am casting the object as OdiFolder
				    	 System.out.println("Procedure Name : "+ ODI_Procedure.getName());
			      	 }
			    	 
			    	//Get list of Variables
			    	 ODI_Variables = ODI_Project.getVariables();
			    	 for (Object object5 : ODI_Variables) {
			    		 ODI_Variable =  (OdiVariable) object5;
			    		 //Here i am casting the object as OdiFolder
				    	 System.out.println("Variable Name : "+ ODI_Variable.getName());
			      	 }
			    	 
			    	//Get list of Sequence
			    	ODI_Sequences = ODI_Project.getSequences();
			    	for (Object object6 : ODI_Sequences) {
			    		ODI_Sequence =  (OdiSequence) object6;
			    		 //Here i am casting the object as OdiFolder
				    	 System.out.println("Sequence Name : "+ ODI_Sequence.getName());
			      	 }

			    	//Get list of User Function OdiUserFunction
			    	ODI_Functions = ODI_Project.getUserFunctions();
			    	for (Object object7 : ODI_Functions) {
			    		ODI_Function =  (OdiUserFunction) object7;
			    		 //Here i am casting the object as OdiFolder
				    	 System.out.println("Sequence Name : "+ ODI_Function.getName());
				    	 
			      	 }
			    	
			    	 //get list of Reusable Mapping
			    	 ODI_ResusableMappings =  ODI_Folder.getReusableMappings();
			    	 
			    	 for (Object object8 : ODI_ResusableMappings) {
			    		 ODI_ResusableMapping =  (ReusableMapping) object8;
			    		 //Here i am casting the object as OdiFolder
				    	 System.out.println("Reusable Mapping Name : "+ ODI_ResusableMapping.getName() );
				    	 
			      	 }
			    	 
			     } // Folders

			}//Projects
			
			
			
			 System.out.println("Close connection ODI");
		}
		finally{
		         odiInstanceHandle.release();
	    } // odiInstance
    	
    } // main
}
