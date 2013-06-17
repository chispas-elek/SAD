package org.sad.packspamfilter;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class J48Tree {

	//atributos
	
	//constructora
	public J48Tree() {
		
	}
	
	//metodos
	public void estimarJ48(Instances pTrain, Instances pTest) {
		J48 estimador = new J48();
		
		try{
			double max=0;
			int posMax = 0;
			double measure=0;
			double pre=0;
			double rec=0;
			double ar=0;
			double acc=0;
			
			//Realizamos un barrido de parámetros y buscamos el mayor fmeasure
			Evaluation evaluator = new Evaluation(pTrain);
			for(int k=0;k<pTrain.numInstances();k++){
				//El factor de confianza para el podado
				estimador.setConfidenceFactor((float) 0.11);
				//El número de instancias por hoja
				estimador.setMinNumObj(6);
				evaluator.crossValidateModel(estimador, pTrain, 5, new Random(45));
				double fMeasure = evaluator.fMeasure(1);
				if(fMeasure > max) {
					max = fMeasure;
					posMax=k;
				}
				//Necesito saber que datos necesito imprimir por pantalla
				
				
				//Una vez hecho todo, entreno el estimador con los datos de train que tengo
				estimador.buildClassifier(pTrain);
				//Aleatorizamos los datos del test
				Random rnd = new Random();
				int num = rnd.nextInt(50)+1;
				pTest.randomize(new Random(num));
				
				//creamos los resultados
				evaluator.evaluateModel(estimador, pTest);
				double predictions[] = new double[pTest.numInstances()];
				
				for(int i = 0;i < pTest.numInstances();i++){
					predictions[i] = evaluator.evaluateModelOnceAndRecordPrediction((Classifier)estimador, pTest.instance(i));
				}
				
				//Escribir los resultados en un fichero.
				
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
