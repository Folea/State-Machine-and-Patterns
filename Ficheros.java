
import java.io.*;
import java.util.Scanner; 

/**
 * Clase con una colección de métodos estáticos que pueden usarse en otra clase para leer un fichero 
 * línea a línea y  volcar el contenido en un String; también  para la operación contraria. 
 * Se incluyen ejemplos de uso de métodos de la clase File, como:
 * 		 getAbsolutePath(), getName(), getParent(), isDirectory() y listFiles()
 * Se ilustra también el uso de los métodos de lectura y escritura en fichero de esta clase y 
 * captura  de IOexception.
 *
 */
 
public class Ficheros {
			
	/**
	* Método que lee  un File de texto línea a línea y devuelve un String con todo el contenido.
	* @param fichero El fichero  que se quiere leer para recuperar el texto.
	* @return contenido El texto del fichero guardado en String.
	* @throws IOException 
	*/	
	public static String leerDeFichero(File fichero) throws IOException  {
		BufferedReader lector;
		lector = new BufferedReader(new FileReader(fichero));
			
		StringBuffer texto=new StringBuffer(""); 
			//para guardar el texto del fichero a medida que se lee; más eficiente que concatenar Strings.
		String linea; //para añadir cada línea de fichero  al  buffer texto
			
			while ((linea=lector.readLine())!= null){ //bucle de volcado del fichero en StringBuffer
				texto.append(linea);//añado la línea leída
				texto.append(System.getProperty("line.separator")); 
					 //añadimos  secuencia de newline adecuada según el sistema operativo, para que sea portable.		
			}	
			lector.close();	//cierro el lector creado.
			
			return (texto.toString()); //convierte el StringBuffer a String y lo devuelve.
	}
						
	/**
	 * Método que guarda  un  String en un File. 
	 * @param texto El texto para guardar en fichero.
	 * @param fichero El File  donde se quiere guardar.
	 * @throws FileNotFoundException 
	 */
	public static void  escribirEnFichero(String texto,File fichero) throws FileNotFoundException {
		PrintWriter escritor = new PrintWriter(fichero);			
		escritor.print(texto); //  escribe el  texto en fichero
		escritor.close(); //cierro el  escritor creado.			
	}
	
	
	   
	///////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	public static void main(String[] args)  {
		Scanner consola = new Scanner(System.in); //para leer por consola
		System.out.println("Nombre del fichero o directorio> ");
		String nombreFichero=consola.nextLine(); //lee nombre de fichero o directorio  desde consola
		
		File fichero=new File(nombreFichero);  
			//crea un File de entrada a partir del nombre, que puede ser fichero o directorio
					
			//PROBAMOS MÉTODOS DE LA CLASE FILE  	
			System.out.println("La ruta completa del fichero/directorio  es: "+fichero.getAbsolutePath());
				      	//imprime el path completo
			System.out.println("El nombre del fichero/directorio es: "+fichero.getName());
			  			//imprime sólo el nombre de fichero o  directorio, aunque se haya  introducido ruta completa    
			
			System.out.println("El padre del fichero/directorio es: "+fichero.getParent());

		
			if (fichero.isDirectory()) {
				//El objeto File se corresponde con un directorio
				System.out.println("Es un directorio");
				
				
				File[] listadoFile=fichero.listFiles();
			    //con el método listFiles se obtienen objetos Files correspondientes a los archivos
				//de un directorio, uno en cada posición del array listadoFile
				if (listadoFile.length>0){
					System.out.print("Ruta completa de archivo:");	
					System.out.println(listadoFile[0].getAbsolutePath()); 
							//imprime ruta completa de primer archivo de array listado
				}
			}   	
			
			else{
				//Aquí se sabe que se la ha leído el nombre de un fichero 
				//PROBAMOS MÉTODOS DE LECTURA Y ESCRITURA CON CAPTURA DE EXCEPCIÓN DE E/S
			
				try {
					String contenido = Ficheros.leerDeFichero(fichero);  //leemos contenido de fichero
				
					//Si no ha habido excepcion al leer se guarda el contenido leído en otro fichero de salida 
					System.out.println("Nombre del fichero para guardar> ");
					nombreFichero=consola.nextLine(); 				
					File outFile=new File(nombreFichero);
					
					Ficheros.escribirEnFichero(contenido,outFile);
					System.out.println("+++Fichero de salida generado: "+outFile.getName()+
							 " en directorio: "+outFile.getParent());
							//imprimo nombre de fichero generado y aparte el directorio donde se encuentra
							//no se imprime el path completo				
				} catch (IOException e) {			
					System.out.println("***Error: no se puede encontrar el fichero."); //captura e imprime excepción
					} 	
				
				System.out.println("FIN DE PRUEBAS METODOS FILE");
					// Aunque se haya lanzado una excepción, el programa
					// sigue ejecutándose e imprime esta línea
							
			}//fin-else 
			
	} 

} 
