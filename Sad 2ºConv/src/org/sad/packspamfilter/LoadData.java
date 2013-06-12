package org.sad.packspamfilter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.core.Instances;


public class LoadData {

	//Atributos
	private String path;
	
	//Constructora
	public LoadData(String pPath) {
		this.path = pPath;
	}
	//metodos
	public Instances cargarDatos() {
		// Abrimos el fichero
	    FileReader fi=null;
		try {
			fi= new FileReader(this.getPath()); //(args[0])
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos:"+this.getPath());
		}
		// Cargamos las instancias
		Instances data=null;
		try {
			data = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: "+this.getPath());
		}
		// Cerramos el fichero de datos
		try {
			fi.close();
		} catch (IOException e) {
			System.out.println("Se ha producido un error a la hora de cerrar el fichero");
		}
		
		// Mezclar las instancias, aplicar un filtro de Randomize.
		Random rdm = new Random(45);
		data.randomize(rdm);

		
		
		//Preguntar por ésta parte
		// 1.6. Specify which attribute will be used as the class: the last one, in this case 
		data.setClassIndex(data.numAttributes()-1);
		
		return data;
	}
	
	private String getPath() {
		return this.path;
	}
}


