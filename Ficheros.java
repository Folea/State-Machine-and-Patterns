
import java.io.*;
import java.util.Scanner; 

/**
 * Clase con una colecci�n de m�todos est�ticos que pueden usarse en otra clase para leer un fichero 
 * l�nea a l�nea y  volcar el contenido en un String; tambi�n  para la operaci�n contraria. 
 * Se incluyen ejemplos de uso de m�todos de la clase File, como:
 * 		 getAbsolutePath(), getName(), getParent(), isDirectory() y listFiles()
 * Se ilustra tambi�n el uso de los m�todos de lectura y escritura en fichero de esta clase y 
 * captura  de IOexception.
 *
 */
 
public class Ficheros {
			
	/**
	* M�todo que lee  un File de texto l�nea a l�nea y devuelve un String con todo el contenido.
	* @param fichero El fichero  que se quiere leer para recuperar el texto.
	* @return contenido El texto del fichero guardado en String.
	* @throws IOException 
	*/	
	public static String leerDeFichero(File fichero) throws IOException  {
		BufferedReader lector;
		lector = new BufferedReader(new FileReader(fichero));
			
		StringBuffer texto=new StringBuffer(""); 
			//para guardar el texto del fichero a medida que se lee; m�s eficiente que concatenar Strings.
		String linea; //para a�adir cada l�nea de fichero  al  buffer texto
			
			while ((linea=lector.readLine())!= null){ //bucle de volcado del fichero en StringBuffer
				texto.append(linea);//a�ado la l�nea le�da
				texto.append(System.getProperty("line.separator")); 
					 //a�adimos  secuencia de newline adecuada seg�n el sistema operativo, para que sea portable.		
			}	
			lector.close();	//cierro el lector creado.
			
			return (texto.toString()); //convierte el StringBuffer a String y lo devuelve.
	}
						
	/**
	 * M�todo que guarda  un  String en un File. 
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
					
			//PROBAMOS M�TODOS DE LA CLASE FILE  	
			System.out.println("La ruta completa del fichero/directorio  es: "+fichero.getAbsolutePath());
				      	//imprime el path completo
			System.out.println("El nombre del fichero/directorio es: "+fichero.getName());
			  			//imprime s�lo el nombre de fichero o  directorio, aunque se haya  introducido ruta completa    
			
			System.out.println("El padre del fichero/directorio es: "+fichero.getParent());

		
			if (fichero.isDirectory()) {
				//El objeto File se corresponde con un directorio
				System.out.println("Es un directorio");
				
				
				File[] listadoFile=fichero.listFiles();
			    //con el m�todo listFiles se obtienen objetos Files correspondientes a los archivos
				//de un directorio, uno en cada posici�n del array listadoFile
				if (listadoFile.length>0){
					System.out.print("Ruta completa de archivo:");	
					System.out.println(listadoFile[0].getAbsolutePath()); 
							//imprime ruta completa de primer archivo de array listado
				}
			}   	
			
			else{
				//Aqu� se sabe que se la ha le�do el nombre de un fichero 
				//PROBAMOS M�TODOS DE LECTURA Y ESCRITURA CON CAPTURA DE EXCEPCI�N DE E/S
			
				try {
					String contenido = Ficheros.leerDeFichero(fichero);  //leemos contenido de fichero
				
					//Si no ha habido excepcion al leer se guarda el contenido le�do en otro fichero de salida 
					System.out.println("Nombre del fichero para guardar> ");
					nombreFichero=consola.nextLine(); 				
					File outFile=new File(nombreFichero);
					
					Ficheros.escribirEnFichero(contenido,outFile);
					System.out.println("+++Fichero de salida generado: "+outFile.getName()+
							 " en directorio: "+outFile.getParent());
							//imprimo nombre de fichero generado y aparte el directorio donde se encuentra
							//no se imprime el path completo				
				} catch (IOException e) {			
					System.out.println("***Error: no se puede encontrar el fichero."); //captura e imprime excepci�n
					} 	
				
				System.out.println("FIN DE PRUEBAS METODOS FILE");
					// Aunque se haya lanzado una excepci�n, el programa
					// sigue ejecut�ndose e imprime esta l�nea
							
			}//fin-else 
			
	} 

} 
