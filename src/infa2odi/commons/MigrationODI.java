/**
 * MigrationODI - This utility class provides helper methods to support the migration of
 * Informatica mappings into Oracle Data Integrator (ODI) structures, including creating
 * and managing ODI models, projects, folders, mappings, and other components.
 * 
 * <p>
 * Licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * You may use, modify, and share this code for non-commercial purposes, provided you give appropriate
 * credit, indicate if changes were made, and distribute any modified work under the same license.
 * </p>
 * 
 * <p>
 * License: <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 *
 * @author Renzo Zagni
 * @license Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License
 * @see <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons License</a>
 */

package infa2odi.commons;

import infa.map.Source;
import infa.map.SourceField;
import infa.map.Target;
import infa.map.TargetField;

import java.util.Collection;
import java.util.List;

import odi.map.Project;
import oracle.odi.core.OdiInstance;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.mapping.IMapComponent;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.ReusableMapping;
import oracle.odi.domain.mapping.exception.MapComponentException;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.model.OdiColumn;
import oracle.odi.domain.model.OdiDataStore;
import oracle.odi.domain.model.OdiKey;
import oracle.odi.domain.model.OdiModel;
import oracle.odi.domain.model.OdiModelFolder;
import oracle.odi.domain.model.OdiSubModel;
import oracle.odi.domain.model.finder.IOdiModelFolderFinder;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiProject;
import oracle.odi.domain.project.OdiVariable;
import oracle.odi.domain.project.finder.IOdiProjectFinder;
import oracle.odi.domain.project.finder.IOdiVariableFinder;
import oracle.odi.domain.topology.OdiLogicalSchema;
import oracle.odi.domain.topology.finder.IOdiLogicalSchemaFinder;



public class MigrationODI {
	
	
	public static OdiLogicalSchema getOdiLogicalSchema(OdiInstance odiInstance,String logicalschemaname){
		return
		 ((IOdiLogicalSchemaFinder)odiInstance.getTransactionalEntityManager().getFinder(OdiLogicalSchema.class)).findByName(logicalschemaname);
		
	}

	public static OdiModel setOdiModelSource(OdiLogicalSchema odiLogicalSchema,OdiModelFolder odiModelFolder,Project project, Source source){
		OdiModel m = null; 
		String modelname = project.getFolderModelCode() + "-SRC-" + source.getDBDName();
		String modelcode = project.getFolderModelCode() + "_SRC_" + source.getDBDName();

		m = MigrationODI.getOdiModel(odiModelFolder, modelcode);
        
        if (m == null){
        	 m = new OdiModel(odiLogicalSchema, modelname , modelcode);
        }
		
    	return m;
	}

	public static OdiModel setOdiModelTarget(OdiLogicalSchema odiLogicalSchema,OdiModelFolder odiModelFolder,Project project){
		OdiModel m = null; 
		String modelname = project.getFolderModelCode() + "-TRG";
		String modelcode = project.getFolderModelCode() + "_TRG";

		m = MigrationODI.getOdiModel(odiModelFolder, modelcode);
        
        if (m == null){
        	 m = new OdiModel(odiLogicalSchema, modelname , modelcode);
        }
		
    	return m;
	}
	
	
	
	public static OdiModel setSourceModel(OdiLogicalSchema odiLogicalSchema,OdiModelFolder odiModelFolder,Project project){
		OdiModel m = null; 

		m = MigrationODI.getOdiModel(odiModelFolder, project.getSourceModelCode());
        
        if (m == null){
        	 m = new OdiModel(odiLogicalSchema, project.getSourceModelName() , project.getSourceModelCode());
        }
		
    	return m;
	}
	
	
	public static OdiModel setTargetModel(OdiLogicalSchema odiLogicalSchema,OdiModelFolder odiModelFolder,Project project){
		OdiModel m = null; 

		m = MigrationODI.getOdiModel(odiModelFolder, project.getTargetModelCode());
        
        if (m == null){
          	 m = new OdiModel(odiLogicalSchema, project.getTargetModelName() , project.getTargetModelCode());
        }
        
    	return m;
	}

	public static OdiProject getODIProject(OdiInstance odiInstance, Project project){
		Object[] ODI_Projects = null;
		OdiProject ODI_Project = null;
		
		ODI_Projects = (((IOdiProjectFinder) odiInstance.getTransactionalEntityManager().
				getFinder(OdiProject.class)).findAll().toArray());
		
		for (Object object : ODI_Projects) {
			ODI_Project = (OdiProject) object;
			
			if (ODI_Project.getCode().equals(project.getCode())){
				return ODI_Project;
			} 
		}	
		
	  return null;	
	}
	
	public static OdiProject setProject (OdiInstance odiInstance, Project project){
		OdiProject ODI_Project = getODIProject(odiInstance, project);
		
		if (ODI_Project == null){
        	ODI_Project = new OdiProject(project.getName(), project.getCode());
        }
		return ODI_Project;
	}
	
	public static OdiModelFolder getODIModelFolder(OdiInstance odiInstance,Project project){
		Object[] ODI_ModelFolders = null;
		OdiModelFolder ODI_ModelFolder = null;
		
        ODI_ModelFolders =  ((IOdiModelFolderFinder) odiInstance.getTransactionalEntityManager().
        		getFinder(OdiModelFolder.class)).findAll().toArray();
		
		for (Object object : ODI_ModelFolders) {
			ODI_ModelFolder = (OdiModelFolder) object;
			if (ODI_ModelFolder.getName().equals(project.getName())){
				return ODI_ModelFolder;
			} 
		}	
	    return null;	
	}
	
	
	public static OdiModelFolder setFolderModel (OdiInstance odiInstance, Project project){
		OdiModelFolder ODI_ModelFolder = getODIModelFolder(odiInstance,project);
		if (ODI_ModelFolder == null){
        	ODI_ModelFolder = new OdiModelFolder(project.getName());
        }
		return ODI_ModelFolder;
	}
	
	
	public static OdiFolder getOdiProjectFolder(OdiProject  ODI_Project,Project project){
		OdiFolder ODI_Folder = null;
		
		for (Object object : ODI_Project.getFolders()){
			ODI_Folder = (OdiFolder) object;
          	if (ODI_Folder.getName().equals(project.getFolder())){
          		return ODI_Folder;
          	}
          }
		return null;
	}
	
	public static OdiFolder setProjectFolder(OdiProject  ODI_Project,Project project){
		OdiFolder ODI_Folder    = getOdiProjectFolder(ODI_Project,project);
				
		if (ODI_Folder == null){
			ODI_Folder = new OdiFolder (ODI_Project,project.getFolder()); 
		}
		
		return ODI_Folder;
	}
	
	
	public static OdiModel getOdiModel(OdiModelFolder modelfolder, String modelname){
		OdiModel ODI_Model = null;
		String ModelCode = modelname.replace(" ", "_").toUpperCase();
		
		  for (Object object : modelfolder.getModels()){
          	ODI_Model = (OdiModel) object;
          	if (ODI_Model.getCode().equals(ModelCode)){
          		return ODI_Model;
          	}
          }
		return null;
	}
	

	public static OdiSubModel getOdiSubModel (OdiModel modelfolder , String submodelcode){
		OdiSubModel sbm = null;
		
        for (Object object : modelfolder.getSubModels()  ){
        	sbm = (OdiSubModel) object;
        	if (sbm.getCode().equals(submodelcode)){
          		return sbm;
          	}
        }
        
		return null;
	}
	
	public static OdiSubModel setOdiSubModel (OdiModel modelfolder ,String submodelname, String submodelcode){
		OdiSubModel sbm = getOdiSubModel(modelfolder,submodelcode);
		
		if(sbm == null){
			sbm = new OdiSubModel(modelfolder,
					submodelname, 
					submodelcode);
		}
		
		return sbm;
	}
	
	public static OdiSubModel getOdiSourceSubModel (OdiSubModel modelfolder , String submodelcode){
		OdiSubModel sbm = null;
		
        for (Object object : modelfolder.getSubModels()  ){
        	sbm = (OdiSubModel) object;
        	if (sbm.getCode().equals(submodelcode)){
          		return sbm;
          	}
        }
        
		return null;
	}
	
	public static OdiSubModel setOdiSourceSubModel (OdiSubModel modelfolder ,String submodelname, String submodelcode){
		OdiSubModel sbm = getOdiSourceSubModel(modelfolder,submodelcode);
		
		if(sbm == null){
			sbm = new OdiSubModel(modelfolder,
					submodelname, 
					submodelcode);
		}
		
		return sbm;
	}
	
	
	public static OdiDataStore getDatastore(Collection<OdiDataStore> ODI_DataStores, String datastorename){
		OdiDataStore ODI_DataStore = null;
		for (Object object : ODI_DataStores) {
	    	  ODI_DataStore = (OdiDataStore) object;
	    	 if (ODI_DataStore.getName().equals(datastorename)){
			     return ODI_DataStore;	 
	    	 }
      	 }
		return null;
	}
	
	public static OdiDataStore setOdiDataStoreSource(OdiSubModel submodel, Source source ){
		OdiDataStore srcDatastore = null;
		List<SourceField> SourceFiledList;
		
		srcDatastore = getDatastore(submodel.getDataStores(), source.getName());
		
		if ( srcDatastore == null  ){
        	srcDatastore = new OdiDataStore(submodel, source.getName() );
        	srcDatastore.setDefaultAlias(source.getName());
        	
            SourceFiledList = source.getSourceFieldList();
            
            for ( SourceField s : SourceFiledList ){
                  OdiColumn srcCol = new OdiColumn(srcDatastore, s.getName() );
                  srcCol.setDataTypeCode(s.getDataType() );
                  srcCol.setMandatory(s.getNullable());
                  srcCol.setLength(s.getLength());
                  srcCol.setScale(s.getScale());
                  
            }//loop to insert the columns
		}
		
		return srcDatastore;	
	}
	
	
	public static OdiDataStore setOdiDataStoreSource(OdiModel model, Source source ){
		OdiDataStore srcDatastore = null;
		List<SourceField> SourceFieldList;
		Boolean keyExist = false;
		OdiKey sdkPrimaryKey = null;
		OdiKey.KeyType keyType = null;
		
		srcDatastore = getDatastore(model.getDataStores(), source.getName());
		
		if ( srcDatastore == null  ){
        	srcDatastore = new OdiDataStore(model, source.getName() );
        	srcDatastore.setDefaultAlias(source.getName());
        	
        	SourceFieldList = source.getSourceFieldList();
            
            for ( SourceField s : SourceFieldList ){
                  OdiColumn srcCol = new OdiColumn(srcDatastore, s.getName() );
                  srcCol.setDataTypeCode(s.getDataType() );
                  srcCol.setMandatory(s.getNullable());
                  srcCol.setLength(s.getLength());
                  srcCol.setScale(s.getScale());
                  
                  if (s.getKeyType().equals("PRIMARY KEY")){
                	  if (keyExist == false){
                		  sdkPrimaryKey = new OdiKey( srcDatastore , "PK_" + srcDatastore.getName());
                          keyType = OdiKey.KeyType.valueOf("PRIMARY_KEY");
                          sdkPrimaryKey.setKeyType(keyType);
                          sdkPrimaryKey.addColumn(srcCol);
                          keyExist = true;
                	  }else{
                		  sdkPrimaryKey.addColumn(srcCol);
                	  }
                  }  	  
            }//loop to insert the columns
		}
		
		return srcDatastore;	
	}
	
	public static OdiDataStore setOdiDataStoreTarget(OdiModel model, Target target ){
		OdiDataStore trgDatastore = null;
		List<TargetField> TargetFieldList;
		Boolean keyExist = false;
		OdiKey sdkPrimaryKey = null;
		OdiKey.KeyType keyType = null;
		
		trgDatastore = getDatastore(model.getDataStores(), target.getName());
		
		if ( trgDatastore == null  ){
        	trgDatastore = new OdiDataStore(model, target.getName() );
        	trgDatastore.setDefaultAlias(target.getName());
        	
        	TargetFieldList = target.getTargetFieldList();
            
            for ( TargetField t : TargetFieldList ){
                  OdiColumn trgCol = new OdiColumn(trgDatastore, t.getName() );
                  trgCol.setDataTypeCode(t.getDataType() );
                  trgCol.setMandatory(t.getNullable());
                  trgCol.setLength(t.getPrecision());
                  trgCol.setScale(t.getScale());
                  
                  if (t.getKeyTipe().equals("PRIMARY KEY")){
                	  if (keyExist == false){
                		  sdkPrimaryKey = new OdiKey( trgDatastore , "PK_" + trgDatastore.getName());
                          keyType = OdiKey.KeyType.valueOf("PRIMARY_KEY");
                          sdkPrimaryKey.setKeyType(keyType);
                          sdkPrimaryKey.addColumn(trgCol);
                          keyExist = true;
                	  }else{
                		  sdkPrimaryKey.addColumn(trgCol);
                	  }
                  }
            }//loop to insert the columns
		}
		
		return trgDatastore;	
	}
	
	
	public static Mapping getOdiMapping(OdiFolder sdkFolder, String mappingname){
		Object[] ODI_Mappings = sdkFolder.getMappings().toArray();
		Mapping ODI_Mapping = null;
		
		  for (Object object : ODI_Mappings ){
			  ODI_Mapping = (Mapping) object;
          	if (ODI_Mapping.getName().equals(mappingname)  ){
          		return ODI_Mapping;
          	}
          }
		return null;
	}
	
	
	public static Mapping deleteOdiMappingObjects(Mapping mapping){
		Mapping ODI_Mapping = mapping;
   	 try {
			while (!ODI_Mapping.getAllComponents().isEmpty() ) {
	    		 IMapComponent comp =  ODI_Mapping.getAllComponents().get(0);
	    		 try {
	    			 ODI_Mapping.removeComponent(comp);
				} catch (AdapterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	 
	    		 
	    	 }
			ODI_Mapping.removeAllProperties();
			ODI_Mapping.resetUserFunctionMap();
			ODI_Mapping.clearPhysicalDesigns();
			ODI_Mapping.onSave();

		} catch (MapComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ODI_Mapping;
	}
	
	public static Mapping setOdiMapping(OdiFolder sdkFolder, String mappingname){
		Mapping ODI_Mapping = getOdiMapping(sdkFolder,mappingname);
		
		if (ODI_Mapping == null){
			try {
				ODI_Mapping = new Mapping(mappingname, sdkFolder);
				
			} catch (AdapterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			ODI_Mapping = deleteOdiMappingObjects(ODI_Mapping);
		}
		
		return ODI_Mapping;
	}

	//Find Project Variable
	public static  OdiVariable getOdiVariable(OdiInstance odiInstance,String ProjectVariable,String ProjectCode){
		OdiVariable odiVariable =((IOdiVariableFinder) odiInstance.getTransactionalEntityManager().getFinder(
                OdiVariable.class)).findByName(ProjectVariable,ProjectCode);

      return odiVariable;
	}
	
	public static  OdiVariable getOdiVariable(OdiProject odiProject, String variableName){
		for ( OdiVariable ov : odiProject.getVariables() ){
			if (ov.getName().equals(variableName)){
				return ov;
			}
		}
		return null;	
	}
	
	public static ReusableMapping getOdiReusableMapping(OdiFolder sdkFolder, String mappingname){
		Object[] ODI_ReusableMappings = sdkFolder.getReusableMappings().toArray();
		ReusableMapping reusableMapping = null;
		
		  for (Object object : ODI_ReusableMappings ){
			  reusableMapping = (ReusableMapping) object;
          	if (reusableMapping.getName().equals(mappingname)  ){
          		return reusableMapping;
          	}
          }
		return null;
	}
	
	public static ReusableMapping deleteOdiReusableMappingObjects(ReusableMapping reusableMapping){
		ReusableMapping ODI_reusableMapping = reusableMapping;
   	 try {
			while (!ODI_reusableMapping.getAllComponents().isEmpty() ) {
	    		 IMapComponent comp =  ODI_reusableMapping.getAllComponents().get(0);
	    		 try {
					ODI_reusableMapping.removeComponent(comp);
				} catch (AdapterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	 
	    		 
	    	 }
			ODI_reusableMapping.removeAllProperties();
			ODI_reusableMapping.resetUserFunctionMap();
			ODI_reusableMapping.onSave();

		} catch (MapComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ODI_reusableMapping;
	}
	
	
}//class MigrationO
