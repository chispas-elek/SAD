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
				System.out.println("Iteracion : " + k + " de " + pTrain.numInstances());
				//El factor de confianza para el podado
				estimador.setConfidenceFactor((float) 0.11);
				//El número de instancias por hoja
				estimador.setMinNumObj(6);
				evaluator.crossValidateModel(estimador, pTrain, 5, new Random(45));
				double fMeasure = evaluator.fMeasure(1);
				double precision=evaluator.precision(1); 
				double recall=evaluator.recall(1);
				double area=evaluator.areaUnderROC(1);
				double accuracy=evaluator.pctCorrect();
				if(fMeasure > max) {
					max = fMeasure;
					posMax=k;
					measure=fMeasure;
					pre=precision;
					rec=recall;
					ar=area;
					acc=accuracy;
				}
			}	
			//Necesito saber que datos necesito imprimir por pantalla
			
			System.out.println("Posicion del maximo" + posMax);
			System.out.println("Valor de F-measure maximo:" + measure);
			System.out.println("Valor de precision maximo:" + pre);
			System.out.println("Valor de recall maximo:" +rec);
			System.out.println("Valor de area maximo:" +ar);
			System.out.println("valor del accuracy maximo"+acc);
				
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
				
			//Resultados
				
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
				
			//Escribir los resultados en un fichero.
			SaveData.escribirResultadosEvaluador("ResultadoJ48Tree.txt", pTest, predictions);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
