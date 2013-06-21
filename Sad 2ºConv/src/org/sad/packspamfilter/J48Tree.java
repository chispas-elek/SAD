package org.sad.packspamfilter;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class J48Tree {

	//atributos
	private final int vueltas = 200;
	//constructora
	public J48Tree() {
		
	}
	
	//metodos
	public void estimarJ48(String pNombreFichero,Instances pTrain, Instances pTest) {
		J48 estimador = new J48();
		
		try{
			double max=0;
			int posMax = 0;
			double measure=0;
			double pre=0;
			double rec=0;
			double acc=0;
			double inc=0;
			int minnumobjopt=0;
			boolean uopt=false;
			//Realizamos un barrido de parámetros y buscamos el mayor fmeasure
			Evaluation evaluator = new Evaluation(pTrain);
			//Barremospara umpruned en true o false
			for(int k=0;k<2;k++){
				if(estimador.getUnpruned()) {
					estimador.setUnpruned(false);
				}else {
					estimador.setUnpruned(true);
				}
				System.out.println("El valor del Umpruned es "+estimador.getUnpruned());
				//Cambiamos el MinNumObj tanto como creamos que debemos configurar en el parámetro vueltas
				for(int b=0;b<vueltas;b++) {
					estimador.setMinNumObj(b);
					evaluator.crossValidateModel(estimador, pTrain, 5, new Random(45));
					double fMeasure = evaluator.fMeasure(1);
					double precision=evaluator.precision(1); 
					double recall=evaluator.recall(1);
					double accuracy=evaluator.pctCorrect();
					int minNumObj = estimador.getMinNumObj();
					boolean umpruned = estimador.getUnpruned();
					System.out.println("Valor del fMeasure "+fMeasure);
					if(fMeasure > max) {
						uopt = umpruned; 
						minnumobjopt= minNumObj;
						max = fMeasure;
						posMax=b;
						measure=fMeasure;
						pre=precision;
						rec=recall;
						acc=accuracy;
					}
				}
			}	
			//Imprimimos los datos referentes a la combinación que mejor fMeasure me da
			
			System.out.println("Posicion del maximo" + posMax);
			System.out.println("Valor de F-measure maximo:" + measure);
			System.out.println("Valor de precision maximo:" + pre);
			System.out.println("Valor de recall maximo:" +rec);
			System.out.println("valor del accuracy maximo"+acc);
			System.out.println("Valor del umpruned óptimo "+uopt);
			System.out.println("Valor del minNumObject óptimo "+minnumobjopt);
				
			//Una vez hecho todo, entreno el estimador con los datos de train que tengo
			//setear parametros óptimos
			J48 estimadorOptimo = new J48();
			estimadorOptimo.setUnpruned(uopt);
			estimadorOptimo.setMinNumObj(minnumobjopt);
			//Entrenamos el modelo con los atributos óptimos
			estimadorOptimo.buildClassifier(pTrain);
				
			//creamos los resultados
			evaluator.evaluateModel(estimadorOptimo, pTest);
			double predictions[] = new double[pTest.numInstances()];
				
			for(int i = 0;i < pTest.numInstances();i++){
				predictions[i] = evaluator.evaluateModelOnceAndRecordPrediction((Classifier)estimadorOptimo, pTest.instance(i));
			}
				
			//Resultados
			measure = evaluator.fMeasure(1);
			pre=evaluator.precision(1); 
			rec=evaluator.recall(1);
			acc=evaluator.pctCorrect();
			inc=evaluator.pctIncorrect();
			
			//Imprimimos por pantalla los resultados que nos ha dado el evaluador después de entrenar el modelo
			System.out.println("Valor de F-measure maximo:" + measure);
			System.out.println("Valor de precision maximo:" + pre);
			System.out.println("Valor de recall maximo:" +rec);
			System.out.println("valor del accuracy maximo"+acc);
			System.out.println("Correctly Classified Instances  " + acc);
			System.out.println("Incorrectly Classified Instances  " + inc);

				
			//Escribir los resultados en un fichero.
			SaveData.escribirResultadosEvaluador(pNombreFichero, pTest, predictions);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
