package org.sad.packspamfilter;

import java.util.Random;

import weka.core.Instances;

public class SeleccionDatos {
	
	//atributos
	private Instances test, train;
	private final double porcentaje = 0.66;
	
	//Constructora
	public SeleccionDatos() {
		this.test = null;
		this.train = null;
	}
	
	
	//metodos
	public void seleccionar(String pNombreFicheroTrain,String pNombreFicheroTest,Instances pData) {
		//Aleatorizamos los datos
		pData.randomize(new Random(45));
		
		//Generamos los ficheros, train y test
		int trainSize = (int) Math.round(pData.numInstances() * this.getPorcentaje());
		int testSize = pData.numInstances() - trainSize;
		this.setTrain(new Instances(pData, 0, trainSize));
		this.setTest(new Instances(pData, trainSize, testSize));
		
		//Guardamos los datos en un fichero
		//SaveData.guardarResultado(pNombreFicheroTrain, this.getTrain());
		//SaveData.guardarResultado(pNombreFicheroTest, this.getTest());
		SaveData.guardarResultadoConWeka(pNombreFicheroTrain, this.getTrain());
		SaveData.guardarResultadoConWeka(pNombreFicheroTest, this.getTest());
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
	
	public double getPorcentaje(){
		return this.porcentaje;
	}

	
	
}
