package odi.components;

import infa.map.Source;
import infa.map.SourceField;
import infa2odi.commons.MigrationODI;

import java.util.List;

import oracle.odi.domain.model.OdiColumn;
import oracle.odi.domain.model.OdiDataStore;
import oracle.odi.domain.model.OdiKey;
import oracle.odi.domain.model.OdiModel;

public class DataStoreSourceODI {

	public static OdiDataStore setOdiDataStoreSource(OdiModel model, Source source ){
		OdiDataStore srcDatastore = null;
		List<SourceField> SourceFieldList;
		Boolean keyExist = false;
		OdiKey sdkPrimaryKey = null;
		OdiKey.KeyType keyType = null;
		
		srcDatastore = MigrationODI.getDatastore(model.getDataStores(), source.getName());
		
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
	
}
