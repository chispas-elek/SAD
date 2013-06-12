package org.sad.packspamfilter;

import weka.core.Instances;
import weka.filters.Filter;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.filters.supervised.attribute.AttributeSelection;


public class FeatureSubSetSelection {
	
	//Atributos
	private Instances data;
	
	//Constructora
	public FeatureSubSetSelection(Instances pData)  {
		this.data = pData;
	}
	
	//Metodos
	public Instances seleccionarAtributos() {
		Instances dataInfoGain = null;
		
		AttributeSelection filter= new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		search.setThreshold(0.0005);
		filter.setEvaluator(eval);
		filter.setSearch(search);
		try{
			filter.setInputFormat(this.data);
			dataInfoGain = Filter.useFilter(data, filter);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataInfoGain;
	}
}
