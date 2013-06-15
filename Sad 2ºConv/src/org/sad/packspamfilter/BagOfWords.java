package org.sad.packspamfilter;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class BagOfWords {

	//Atributos
	private Instances data;
	private StringToWordVector stw;
	
	//Constructora
	public BagOfWords(Instances pData) {
		this.data = pData;
		this.stw = new StringToWordVector();
	}
	
	//Métodos
	public StringToWordVector getStw(){
		return this.stw;
	}
	
	public Instances getData() {
		return this.data;
	}
	
	public Instances matrizDispersionOWCFalse() {
		Instances dataBOW = null;
		this.getStw().setIDFTransform(false);
		this.getStw().setTFTransform(false);
		this.getStw().setAttributeIndices("last");
		this.getStw().setLowerCaseTokens(true);
		this.getStw().setOutputWordCounts(false);
		this.getStw().setWordsToKeep(2000);
		
		
		try{
			this.getStw().setInputFormat(this.getData());
			dataBOW = Filter.useFilter(this.getData(), this.getStw());
		}catch (Exception e) {
			System.out.println("Ha ocurrido un error durante el filtrado, es posible que el fichero de datos no sea correcto o que los parámetros del filtro no sean correctos");
		}
		SaveData.guardarResultado("testBOW.arff", dataBOW);
		return dataBOW;
	}
	
	public Instances matrizDispersionOWCTrue() {
		Instances dataBOW = null;
		this.getStw().setIDFTransform(false);
		this.getStw().setTFTransform(false);
		this.getStw().setAttributeIndices("last");
		this.getStw().setLowerCaseTokens(true);
		this.getStw().setOutputWordCounts(true);
		this.getStw().setWordsToKeep(2000);
		
		
		try{
			this.getStw().setInputFormat(this.getData());
			dataBOW = Filter.useFilter(this.getData(), this.getStw());
		}catch (Exception e) {
			System.out.println("Ha ocurrido un error durante el filtrado, es posible que el fichero de datos no sea correcto o que los parámetros del filtro no sean correctos");
		}
		return dataBOW;
		
		//Falta la parte de escritura del fichero
	}
	
	
	//Faltaría hacer el método para TF-IDF
	public Instances matrizDispersionTFIDF() {
		Instances dataBOWTF = null;
		this.getStw().setIDFTransform(true);
		this.getStw().setTFTransform(true);
		this.getStw().setAttributeIndices("last");
		this.getStw().setLowerCaseTokens(true);
		this.getStw().setOutputWordCounts(true);
		this.getStw().setWordsToKeep(2000);
		
		try{
			this.getStw().setInputFormat(data);
			dataBOWTF = Filter.useFilter(data, this.getStw());
		}catch (Exception e) {
			System.out.println("Ha ocurrido un error durante el filtrado, es posible que el fichero de datos no sea correcto o que los parámetros del filtro no sean correctos");
		}
		SaveData.guardarResultado("testBOWTFIDF.arff", dataBOWTF);
		return dataBOWTF;
		//Falta la parte de escritura del fichero
	}
}
