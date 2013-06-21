package org.sad.packspamfilter;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instances;

public class Naive {
	
	public Naive() {
		
	}
	public void estimarNaive(String pNombreFichero,Instances pTrain, Instances pTest) {
			NaiveBayes estimador = new NaiveBayes();
			try {
				Attribute atrib = pTrain.attribute("clasify");
				pTrain.setClassIndex(atrib.index());
				
				//Estimamos el mejor resultado
				Evaluation evaluator = new Evaluation(pTrain);
				evaluator.crossValidateModel(estimador, pTrain, 5, new Random(45));
				System.out.println("Valor de F-measure maximo:" + evaluator.fMeasure(1));
				System.out.println("Valor de precision maximo:" + evaluator.precision(1));
				System.out.println("Valor de recall maximo:" +evaluator.recall(1));
				System.out.println("valor del accuracy maximo"+evaluator.pctCorrect());
				
				
				//Una vez imprimido el fmeasure máximo vamos a entrenar el modelo y guardar los resultados
				
				estimador.buildClassifier(pTrain);
				//Evaluamos el modelo
				evaluator.evaluateModel(estimador, pTest);
					
				double predictions[] = new double[pTest.numInstances()];
				for (int i = 0; i < pTest.numInstances(); i++) {
					predictions[i] = evaluator.evaluateModelOnceAndRecordPrediction((Classifier)estimador, pTest.instance(i)); //Cast Clasifier a estimador
				}
					
				//Resultado
		
				System.out.println("Valor de F-measure maximo:" + evaluator.fMeasure(1));
				System.out.println("Valor de precision maximo:" + evaluator.precision(1));
				System.out.println("Valor de recall maximo:" +evaluator.recall(1));
				System.out.println("valor del accuracy maximo"+evaluator.pctCorrect());
				System.out.println("Incorrectly Classified Instances  " +evaluator.pctIncorrect());

					
					
					
				//Escribiremos en un fichero el resultado final de la matriz de predictions.
				SaveData.escribirResultadosEvaluador(pNombreFichero, pTest, predictions);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
}	
