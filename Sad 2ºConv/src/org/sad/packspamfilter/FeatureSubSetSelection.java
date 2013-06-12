package org.sad.packspamfilter;

import weka.core.Instances;
import weka.filters.Filter;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;


public class FeatureSubSetSelection {
	
	//Atributos
	private Instances data;
	
	//Constructora
	public FeatureSubSetSelection(Instances pData)  {
		this.data = pData;
	}
	
	public Instances seleccioanrAtributos() {
		Instances dataInfoGain = null;
		
		
		/////////////////////////////////////////////////////////////		
		// 2. FEATURE SUBSET SELECTION
		//  HACER!!!! Empaquetar Bloque 2: como sub-clase		
		/*AttributeSelection filter= new AttributeSelection();
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search=new BestFirst();
		filter.setEvaluator(eval);
		filter.setSearch(search);
		filter.setInputFormat(data);
		// 2.1 Get new data set with the attribute sub-set
		Instances newData = Filter.useFilter(data, filter);*/
		
		InfoGainAttributeEval igae = new InfoGainAttributeEval();
		Ranker rank = new Ranker();
		rank.setThreshold(0.0005);
		
		
	}
}
