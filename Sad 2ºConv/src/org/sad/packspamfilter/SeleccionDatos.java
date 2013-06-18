package org.sad.packspamfilter;

import weka.core.Instances;

public class SeleccionDatos {
	
	//atributos
	private Instances test, train;
	
	//Constructora
	public SeleccionDatos() {
		this.test = null;
		this.train = null;
	}
	
	
	//metodos
	public void seleccionar(String pNombreFicheroTest,String pNombreFicheroTrain,Instances pData) {
		int trainSize = (int) Math.round(pData.numInstances() * 0.66);
		int testSize = pData.numInstances() - trainSize;
		this.setTrain(new Instances(pData, 0, trainSize));
		this.setTest(new Instances(pData, trainSize, testSize));
		
		//Guardamos los datos en un fichero
		SaveData.guardarResultado(pNombreFicheroTrain, this.getTrain());
		SaveData.guardarResultado(pNombreFicheroTest, this.getTest());
	}
	
	private void setTrain(Instances pDatos) {
		this.train = pDatos;
	}
	
	private void setTest(Instances pDatos) {
		this.test = pDatos;
	}
	
	public Instances getTrain() {
		return this.train;
	}
	
	public Instances getTest() {
		return this.test;
	}

	
	
}
