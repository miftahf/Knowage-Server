package it.eng.spagobi.commons.utilities;

/* SpagoBI, the Open Source Business Intelligence suite

 * Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0, without the "Incompatible With Secondary Licenses" notice.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import it.eng.spagobi.utilities.engines.rest.AbstractRestClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.json.JSONArray;

/**
 * 
 * @author Gavardi Giulio(giulio.gavardi@eng.it)
 */

public class DataSetPersister extends AbstractRestClient{

	private String serviceUrl = "/restful-services/1.0/datasets/list/persist";
	
	public DataSetPersister(){
		
	}
	
	static protected Logger logger = Logger.getLogger(DataSetPersister.class);

	public void cacheDataSets(List<String> datasetLabels, String userId) throws Exception {

		logger.debug("IN");

		Map<String, Object> parameters = new java.util.HashMap<String, Object> ();
		
		JSONArray datasetLabelsArray = new JSONArray();
		
		for(int i=0; i<datasetLabels.size(); i++){
			datasetLabelsArray.put(datasetLabels.get(i));
		}
		
		
		parameters.put("labels", datasetLabelsArray);
		parameters.put("user_id", userId);

		logger.debug("Call persist service in post");
		executeService(parameters, serviceUrl);
		
		logger.debug("OUT");
	}

}
