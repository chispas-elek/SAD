package org.sad.packspamfilter;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Naive {
	
	NaiveBayes estimador = new NaiveBayes();

	public Naive(Instances pData) {
		try {
			estimador.buildClassifier(pData);
		}
		catch(Exception e) {
			System.out.println("No se ha podido crear el estimador");
		}
	}
	
	public NaiveBayes getEstimador() {
		return this.estimador;
	}
}
