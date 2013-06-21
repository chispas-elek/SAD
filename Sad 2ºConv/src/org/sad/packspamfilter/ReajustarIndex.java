package org.sad.packspamfilter;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instances;

public class ReajustarIndex {
	
	//atributos
	private Instances data;
	
	//constructora
	public ReajustarIndex(Instances pData) {
		this.data = pData;
	}

	//metodos
	
	private Instances getData() {
		return this.data;
	}
	
	
	//La idea de ésta clase es hacer que clasify ocupe la posición 0 para poder poner el class index en ese valor
	public Instances redIndexClasify() {
		Instances newData = this.getData();
		Attribute index = newData.attribute("clasify");
		newData.swap(0,index.index());
		newData.setClassIndex(0);
		return newData;
	}
}
