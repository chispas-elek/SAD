package org.sad.packspamfilter;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import weka.core.Instances;

public class SaveData {

	//En un principio la idea es que los datos se guarden en el mismo path del eclipse, luego se pensará opciones diferentes
	
	//Constructora
	public SaveData() {
		
	}
	
	//Metodos
	public void guardarResultado(String pNombreFichero, Instances pData) {
		File fichero = new File(pNombreFichero);
		if(!fichero.exists()) {
			//No existe duplicado
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(pNombreFichero));
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("El fichero "+pNombreFichero+" existe en el directorio de datos, por favor eliminalo");
		}
	}
}
