package org.sad.packspamfilter;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Naive {
	
	public Naive() {
		
	}
	public void Naive(Instances pTrain, Instances pTest) {
			NaiveBayes estimador = new NaiveBayes();
			try {
				double max=0;
				int posMax = 0;
				double measure=0;
				double pre=0;
				double rec=0;
				double ar=0;
				double acc=0;
				
				//Seleccionamos el último atributo que corresponde al clasify
				//pTrain.setClassIndex(pTrain.numAttributes());
				
				Evaluation evaluator = new Evaluation(pTrain);
				for(int k=0;k<pTrain.numInstances();k++){
					System.out.println("Iteracion : " + k + " de " + pTrain.numInstances());
					evaluator.crossValidateModel(estimador, pTrain, 5, new Random(45));
					double fMeasure=evaluator.fMeasure(1); 
					double precision=evaluator.precision(1); 
					double recall=evaluator.recall(1);
					double area=evaluator.areaUnderROC(1);
					double accuracy=evaluator.pctCorrect();
	                       
	                if (fMeasure>max) {
						max=fMeasure;
						posMax=k;
						measure=fMeasure;
						pre=precision;
						rec=recall;
						ar=area;
						acc=accuracy;
	                }
				} 
				System.out.println("Posicion del maximo" + posMax);
				System.out.println("Valor de F-measure maximo:" + measure);
				System.out.println("Valor de precision maximo:" + pre);
				System.out.println("Valor de recall maximo:" +rec);
				System.out.println("Valor de area maximo:" +ar);
				System.out.println("valor del accuracy maximo"+acc);
				
				
				//Una vez imprimido el fmeasure máximo vamos a entrenar el modelo y guardar los resultados
				
				try{
					estimador.buildClassifier(pTrain);
					//Aleatorizamos el fichero de test
					Random rnd = new Random();
					int num = rnd.nextInt(50)+1;
					pTest.randomize(new Random(num));
					
					evaluator.evaluateModel(estimador, pTest);
					
					double predictions[] = new double[pTest.numInstances()];
					for (int i = 0; i < pTest.numInstances(); i++) {
						predictions[i] = evaluator.evaluateModelOnceAndRecordPrediction((Classifier)estimador, pTest.instance(i)); //Cast Clasifier a estimador
					}
					
					//Resultado
					
					acc=evaluator.pctCorrect();
					double inc=evaluator.pctIncorrect();
					double kappa=evaluator.kappa();
					double mae=evaluator.meanAbsoluteError();    
					double rmse=evaluator.rootMeanSquaredError();
					double rae=evaluator.relativeAbsoluteError();
					double rrse=evaluator.rootRelativeSquaredError();
		
					System.out.println("Correctly Classified Instances  " + acc);
					System.out.println("Incorrectly Classified Instances  " + inc);
					System.out.println("Kappa statistic  " + kappa);
					System.out.println("Mean absolute error  " + mae);
					System.out.println("Root mean squared error  " + rmse);
					System.out.println("Relative absolute error  " + rae);
					System.out.println("Root relative squared error  " + rrse);
					
					
					
					//Escribiremos en un fichero el resultado final de la matriz de predictions.
				}catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
}	
