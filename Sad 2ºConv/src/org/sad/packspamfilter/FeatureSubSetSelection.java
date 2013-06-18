package org.sad.packspamfilter;

import weka.core.Instances;
import weka.filters.Filter;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.filters.supervised.attribute.AttributeSelection;


public class FeatureSubSetSelection {
	
	
	//Constructora
	public FeatureSubSetSelection()  {
		
	}
	
	//Metodos
	public Instances seleccionarAtributos(String pNombreFichero,Instances pData) {
		Instances dataInfoGain = null;
		pData.setClassIndex(0);
		AttributeSelection filter= new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		search.setThreshold(0.0005);
		search.setNumToSelect(-1);
		filter.setEvaluator(eval);
		filter.setSearch(search);
		try{
			filter.setInputFormat(pData);
			dataInfoGain = new Instances(Filter.useFilter(pData, filter));
		}catch (Exception e) {
			e.printStackTrace();
		}
		//Guardo los ficheros resultantes
		SaveData.guardarResultado(pNombreFichero, dataInfoGain);
		return dataInfoGain;
	}
}
