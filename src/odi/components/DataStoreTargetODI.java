package odi.components;

import infa.map.Target;
import infa.map.TargetField;
import infa2odi.commons.MigrationODI;

import java.util.List;

import oracle.odi.domain.model.OdiColumn;
import oracle.odi.domain.model.OdiDataStore;
import oracle.odi.domain.model.OdiKey;
import oracle.odi.domain.model.OdiModel;

public class DataStoreTargetODI {
	public static OdiDataStore setOdiDataStoreTarget(OdiModel model, Target target ){
		OdiDataStore trgDatastore = null;
		List<TargetField> TargetFieldList;
		Boolean keyExist = false;
		OdiKey sdkPrimaryKey = null;
		OdiKey.KeyType keyType = null;
		
		trgDatastore = MigrationODI.getDatastore(model.getDataStores(), target.getName());
		
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
	
}
