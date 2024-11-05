/**
 * ODIConnectTo - This class represents a connector between source and target components in an ODI (Oracle Data Integrator)
 * mapping. It initializes with a source connector and allows setting a temporary connector, facilitating
 * connections by managing details such as source and target table/column names and dataset names.
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

import infa.map.Connector;
import infa.map.InfaTransformationType;


public class ODIConnectTo {
	private Connector connector    ;
	private Connector _tmpConnector;
	private String sourceTableName ;
	private String sourceColumnName;
	private String datasetName     ;
	private String targetTableName ;
	private String targetColumnName;
	
	public ODIConnectTo(Connector connector){
		this.setConnector(connector);
		this.setSourceTableName(connector.getFromInstance());
	}

	public void set_tmpConnector(Connector _tmpConnector) {
		this._tmpConnector = _tmpConnector;
		if(this._tmpConnector.getFromInstanceType().equals(InfaTransformationType.SOURCE_QUALIFIER)){
			setSourceColumnName(this._tmpConnector.getFromField());
			setDatasetName(this._tmpConnector.getFromInstance());
		}
		
		if(this._tmpConnector.getToInstanceType().equals(InfaTransformationType.TARGET_DEFINITION)){
			setTargetTableName(this._tmpConnector.getToInstance());
			setTargetColumnName(this._tmpConnector.getToField());
		}
	}

	public Connector get_tmpConnector() {
		return _tmpConnector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	public Connector getConnector() {
		return connector;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	public String getSourceTableName() {
		return sourceTableName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetColumnName(String targetColumnName) {
		this.targetColumnName = targetColumnName;
	}

	public String getTargetColumnName() {
		return targetColumnName;
	}

	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}

	public String getSourceColumnName() {
		return sourceColumnName;
	}
	
	
	
	
	
}
