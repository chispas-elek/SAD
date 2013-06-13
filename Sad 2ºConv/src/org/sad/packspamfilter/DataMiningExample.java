/*
 GOAL: Load data from .arff files, preprocess the data, train a model and assess it either using 10-fold cross-validation 
 
 Compile:
 javac DataMiningExample.java

 Run Interpret:
 java DataMiningExample
 
 HACER!!!
	- ¡Este programa no es modular!
	- 

 */
package org.sad.packspamfilter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;


public class DataMiningExample {
	
    public static void main(String[] args) throws Exception {
		/////////////////////////////////////////////////////////////
		// 1. LOAD DATA FILE
		//  HACER!!!! Bloque 1: como sub-clase
        // 1.1. Get the path of the .arff (instances) from the command line
        /*
		 if( args.length < 1 ){
			System.out.println("OBJETIVO: Seleccionar atributos (AttributeSelection<-CfsSubsetEval, search<-BestFirst) y Evaluar clasificador NaiveBayes con 10-fold cross-validation.");
			System.out.println("ARGUMENTOS:");
			System.out.println("\t1. Path del fichero de entrada: datos en formato .arff");
			return; 
		}
		 */		
    	
    	/////////////////////////////////////////////////////////////////////////
    	//Abrimos el fichero y cargamos los datos
        LoadData datos = new LoadData(args[0]);
        Instances data = datos.cargarDatos();
     
        ////////////////////////////////////////////////////////////////////////
        //Bag of Words
        //En éste apartado vamos a crear la matriz dispersa para indicar la presencia de las palabras.
        
        BagOfWords bow = new BagOfWords(data);
        //Por el momento será con el output en false
        Instances dataBOW = bow.matrizDispersionOWCFalse();
        Instances dataBOWTF = bow.matrizDispersionTFIDF();
        ////////////////////////////////////////////////////////////////////////
        //FeatureSubSetSelection
        //Ahora vamos a elegir los datos más relevantes mediante dos tipos de algoritmos. Uno de ganancia de datos y otra basado en técnicas TF-IDF
        
        //Algoritmo basado en ganancia de datos
        
        FeatureSubSetSelection fss = new FeatureSubSetSelection();
		Instances dataInfoGain = fss.seleccionarAtributos(dataBOW);
		
		//Algoritmo basado en TF-IDF
		
		Instances dataTFIDF = fss.seleccionarAtributos(dataBOWTF);

		
		
		
		/////////////////////////////////////////////////////////////
		// 3. CLASSIFY: 
		// 3.0 Train the classifier (estimador) by means of:	the Naive Bayes algorithm (in this case)
		NaiveBayes estimador= new NaiveBayes();//Naive Bayes
		// Instead, train the classifier (estimador) by means of:		the IB1 algorithm (in this case) 
		//IB1 estimador= new IB1();//k-Nearest Neighbour (with k=1)
		try {
			estimador.buildClassifier(dataInfoGain);
		}
		catch(Exception e) {
			System.out.println("No se ha podido crear el estimador");
		}

		// 3.1 Assess the performance of the classifier by means of 10-fold cross-validation 
		//  HACER!!!! Empaquetar Bloque 3.1: como sub-clase						
		Evaluation evaluator = new Evaluation(dataInfoGain);
		evaluator.crossValidateModel(estimador, dataInfoGain, 10, new Random(3)); // Random(1): the seed=1 means "no shuffle" :-!
		double acc=evaluator.pctCorrect();
		double inc=evaluator.pctIncorrect();
		double kappa=evaluator.kappa();
		double mae=evaluator.meanAbsoluteError();    
		double rmse=evaluator.rootMeanSquaredError();
		double rae=evaluator.relativeAbsoluteError();
		double rrse=evaluator.rootRelativeSquaredError();
		double confMatrix[][]= evaluator.confusionMatrix();
		
		System.out.println("Correctly Classified Instances  " + acc);
		System.out.println("Incorrectly Classified Instances  " + inc);
		System.out.println("Kappa statistic  " + kappa);
		System.out.println("Mean absolute error  " + mae);
		System.out.println("Root mean squared error  " + rmse);
		System.out.println("Relative absolute error  " + rae);
		System.out.println("Root relative squared error  " + rrse);	
		
		/*
		 // 3.2 Alternatively, assess the classifier leaving the 30% of the data randomly selected out to test the model 
		// 3.2.a Get the test set by randomly selecting the the 30% of the instances
		int trainSize = (int) Math.round(newData.numInstances() * 0.7);
		int testSize = newData.numInstances() - trainSize;
		// HACER!!!! Salvar las instancias del test en un fichero
		Instances train = new Instances(newData, 0, trainSize);
		Instances test = new Instances(newData, trainSize, testSize);
		
		// 3.2.b Train the classifier with the 70\% of the data by means of the Naive Bayes algorithm
		estimador.buildClassifier(train);
		
		// 3.2.c Let the model predict the class for each instance in the test set
		evaluator.evaluateModel(estimador, test);
		double predictions[] = new double[test.numInstances()];
		for (int i = 0; i < test.numInstances(); i++) {
			predictions[i] = evaluator.evaluateModelOnceAndRecordPrediction((Classifier)estimador, test.instance(i));
		}
		// HACER!!!! Guardar en un fichero de salida la clase estimada por el modelo para cada instancia del test y así después podremos comparar la clase real y la estimada
		
		// 3.2.d Assess the performance on the test
		//  HACER!!!! Idéntico idéntico idéntico al 3.1: por eso es necesario que sea modular, no vamos a copiar aquí el código de nuevo!
		*/
		
    }
}
