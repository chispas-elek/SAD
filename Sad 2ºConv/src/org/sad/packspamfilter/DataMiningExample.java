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

import weka.core.Instances;


public class DataMiningExample {
	
    public static void main(String[] args) throws Exception {
		
    	/////////////////////////////////////////
    	//Abrimos el fichero y cargamos los datos
        LoadData datos = new LoadData(args[0]);
        Instances data = datos.cargarDatos();
     
        /////////////////////////////////////////
        //Bag of Words
        //En éste apartado vamos a crear la matriz 
        //dispersa para indicar la presencia de las palabras.
        
        BagOfWords bow = new BagOfWords(data);
        
        //Por el momento será con el output en false
       
        Instances dataBOW = bow.matrizDispersionOWCFalse();
        Instances dataTFIDF = bow.matrizDispersionTFIDF();
        
        //////////////////////////////////////////
        //FeatureSubSetSelection
        
        SeleccionarAtributos fss = new SeleccionarAtributos();
        
        //Ahora vamos a elegir los datos más relevantes mediante dos tipos de algoritmos. 
        //Uno de ganancia de datos y otra basado en técnicas TF-IDF
        
        //Algoritmo basado en ganancia de datos
        
		Instances dataInfoGain = fss.seleccionarAtributos("smsInstancesBOW_FSS_InfoGain.arff",dataBOW);
		

		
		//////////////////////////////////////////////////////////////////////////////////////////////
		//Partición del train y test.
		
		//Partición train y test para InfoGain
		
		SeleccionDatos selecDatos = new SeleccionDatos();
		selecDatos.seleccionar("sms_InfoGain_train.arff","sms_InfoGain_test.arff",dataInfoGain);
		Instances train = selecDatos.getTrain();
		Instances test = selecDatos.getTest();
		
		//Partición train y test para TF-IDF
		
		SeleccionDatos selecDatosTFIDF = new SeleccionDatos();
		selecDatosTFIDF.seleccionar("sms_TFIDF_train.arff","sms_TFIDF_test.arff",dataTFIDF);
		Instances trainTFIDF = selecDatosTFIDF.getTrain();
		Instances testTFIDF = selecDatosTFIDF.getTest();
		
		/////////////////////////////////////////
		// 3. CLASSIFY: 
		
		//Estimador usándo NaiveBayes
		Naive naiveIG = new Naive();
		naiveIG.estimarNaive("InfoGain_test_BaselinePredictions.txt",train, test);
		naiveIG.estimarNaive("TFIDF_test_BaselinePredictions.txt",trainTFIDF, testTFIDF);
		//Estimador usándo árbol de decisión J48
		J48Tree jTree = new J48Tree();
		jTree.estimarJ48("InfoGain_test_AlgorithmPredictions.txt",train, test);
		jTree.estimarJ48("TFIDF_test_AlgorithmPredictions.txt",trainTFIDF, testTFIDF);
    }
}
