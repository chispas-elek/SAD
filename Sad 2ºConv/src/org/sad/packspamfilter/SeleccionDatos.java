package org.sad.packspamfilter;

import weka.core.Instances;

public class SeleccionDatos {
	
	private Instances test, train;
	
	public SeleccionDatos() {
		
	}
	
	public void seleccionar(Instances pData) {
		int trainSize = (int) Math.round(pData.numInstances() * 0.66);
		int testSize = pData.numInstances() - trainSize;
		train = new Instances(pData, 0, trainSize);
		test = new Instances(pData, trainSize, testSize);
		
		// HACER!!!! Salvar las instancias del test en un fichero
	}
	
	public Instances train() {
		return this.train;
	}
	
	public Instances test() {
		return this.test;
	}

}
