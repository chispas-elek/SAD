package org.sad.packspamfilter;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import weka.core.Instances;

public class SaveData {

	//En un principio la idea es que los datos se guarden en el mismo path del eclipse, luego se pensará opciones diferentes
	
	//Constructora
	public SaveData() {
		
	}
	
	//Metodos
	public static void guardarResultado(String pNombreFichero, Instances pData) {
		File fichero = new File(pNombreFichero);
		if(!fichero.exists()) {
			//No existe duplicado
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(pNombreFichero));
				//Escribimos el relation
				bw.write("@relation "+pData.relationName());
				bw.newLine();
				//Aquí imprimimos cada atributo
				for(int k=0;k<pData.numAttributes();k++) {
					bw.newLine();
					bw.write(""+pData.attribute(k));
				}
				
				//Aquí escribiremos las instancias de la clase
				bw.newLine();
				bw.newLine();
				bw.write("@data");
				for(int i=0;i<pData.numInstances();i++) {
					bw.newLine();
					bw.write(""+pData.instance(i));
				}
				bw.close();
				System.out.println("El fichero "+pNombreFichero+" ha sido creado satisfactoriamente");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("El fichero "+pNombreFichero+" existe en el directorio de datos, por favor eliminalo");
		}
	}
	
	//Con el siguiente método se intenta escribir los resultados obtenidos
	
	public static void escribirResultadosEvaluador(String pNombreFichero,Instances pTest,double[] pPrediction){
		File fichero = new File(pNombreFichero);
		if(!fichero.exists()) {
			//No existen duplicados
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(pNombreFichero));
				for(int i=0;i<pPrediction.length;i++){
					// Escribir la clase real que aparece en el conjunto de test y la clase estimada
					double prediction = pPrediction[i];
					bw.write(" REAL CLASS: " + pTest.classAttribute().value((int) pTest.instance(i).classValue()));
					bw.write("Â  SYSTEM PREDICTED CLASS: " + pTest.classAttribute().value((int) prediction));
					bw.newLine();
				}
				bw.close();
				System.out.println("Los resultados han sido guardados con el nombre de "+pNombreFichero);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("El fichero de Resultados ya existe. Por favor elimina primero dicho fichero");
		}
	}
}
