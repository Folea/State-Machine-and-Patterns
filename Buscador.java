//package ejercicio5;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ejercicio3.Ficheros;
/**
 * 
 * @author Cristian Folea
 * @author Jose Manuel Lorenzo Salmeron
 *
 */
/**
 * Esta clase  busca en los ficheros (.txt .htm .html) de un directorio las palabras clave 
 * los dominios , los emails.
 * Y los escribe en un fichero .txt. 
 * 
 */
public class Buscador {
	
	
	private static void palabrasClave(String texto, StringBuffer buffer) {
		// esta ER "acepta" las palabras clave
		////copiamos  el esquema donde aparecen las palabras claves
		// y le añadimos los posibles espacios
		//(.*) ponemos esta expresion entre parentesis para poder hacer group y cger las palabras
		Pattern patPalCla = Pattern
				.compile("<meta\\s+name\\s*=\\s*\\\"\\s*keywords\\s*\\\"\\s+"
						+ "content\\s*=\\s*\\\"\\s*(.*)\\s*\\\"\\s*/>");
		Matcher matPalCla = patPalCla.matcher(texto);
		if (matPalCla.find()) {// si hay palabras clave
			buffer.append("PALABRAS CLAVE: " + matPalCla.group(1)
					+ System.getProperty("line.separator"));
		} else
			buffer.append("no hay" + System.getProperty("line.separator"));

	}

	private static void dominios(String texto, StringBuffer buffer) {
		Pattern patDom = Pattern
				.compile("(?i)https?://((\\w[\\w-]*\\.)+[a-z]{2,4})(:\\d{1,4})?(/[\\w/#˜:.?+=& %@-]+)?");
		//hacemos el matcher con el string texto, que es el contenido del fichero
		Matcher matDom = patDom.matcher(texto);
		//buffer auxiliar para ayudarnos a imprimir
		StringBuffer auxBuffer = new StringBuffer();
		// creamos un conjunto par almacenar los dominios sin que se repitan
		HashSet<String> contenido = new HashSet<String>();

		while (matDom.find()) {// si hay alguna cadena que coincida con el patron
			if (!contenido.contains(matDom.group(1))) { //y si no esta en el conjunto
				//se lo añadimos al bufffer auxiliar y al conjunt
				auxBuffer.append("\t" + matDom.group(1)
						+ System.getProperty("line.separator"));

				contenido.add(matDom.group(1));
			}
		}
		//para imprimir congemoms la longitud del conjunto, asi sabemos el numero de dominios
		// y luego le añadimos al buffer principal el buffer auxiliar, para cumplir el
		//formato de salida
		buffer.append("\tDOMINIOS EN URLs --> total " + contenido.size()
				+ System.getProperty("line.separator"));
		buffer.append(auxBuffer);
		// Iterator it = contenido.entrySet().iterator();

	}
	
	// este metodo es analogo al anterior con la unica diferencia de
	//la expresion regular
	private static void Email(String texto, StringBuffer buffer) {
		Pattern patEmail = Pattern
				.compile("\\w[\\w. %+-]*@(\\w[\\w-]*\\.)+[a-zA-Z]{2,4}");
		Matcher matEmail = patEmail.matcher(texto);

		StringBuffer auxBuffer = new StringBuffer();
		HashSet<String> contenido = new HashSet<String>();

		while (matEmail.find()) {

			if (!contenido.contains(matEmail.group(0))) {
				auxBuffer.append("\t" + matEmail.group(0)
						+ System.getProperty("line.separator"));

				contenido.add(matEmail.group(0));
			}

		}
		buffer.append("\tDIRECCIONES DE CORREO:--> total " + contenido.size()
				+ System.getProperty("line.separator"));
		buffer.append(auxBuffer);

	}

	public static void main(String[] args) throws IOException {
		String nombre;
		System.out.println("Intruduzca un directorio donde buscar: ");
		Scanner scan = new Scanner(System.in);
		nombre = scan.nextLine();
		File directorio = new File(nombre);

		while (!directorio.isDirectory()) {//mientras el fichero no sea un directorio
			//seguimos pidiendo un directorio correcto
			System.out.println("No es un directorio.");
			System.out.println("Escribe el nombre del directorio a explorar:");
			nombre = scan.nextLine();
			directorio = new File(nombre);
		}
		StringBuffer buffer = new StringBuffer();
		//metemos en el buffer la ruta donde esta el directorio y un retorno de carro
		buffer.append(directorio.getAbsolutePath()
				+ System.getProperty("line.separator"));
		System.out.println(buffer);
		
		//esta ER "acepta" cualquier cadena que termina en txt html htm en mayuscula o minuscula
		Pattern patron = Pattern
				.compile("(.+)[(\\.txt|TXT)(\\.html|HTML)(\\.htm|HTM)]");

		//esta ER "acepta" cualquier cadena que empice por <title> y termine por </title>
		// hemos puesto parentesis a (.*) para poder hacer group y sacar el titulo
		Pattern titulo = Pattern.compile("<title>(.*)</title>");
		
		Matcher matitulo;

		File[] lista = directorio.listFiles();// lista de ficheros del directorio
		if (lista.length != 0) {// no esta vacio el directorio

			String contenidoFich;
			Matcher mat;
			for (File file : lista) {// recorremos todos los ficheros del directorio
				mat = patron.matcher(file.getName());//hacemos el matcher con el nombre del fichero
				if (mat.matches()) {// el fichero es htm html o txt
					//metemos en el buffer el nombre del fichero donde estamos buscando
					buffer.append("Buscando en : " + file.getName()
							+ System.getProperty("line.separator"));
					//ponemos en un string el contenido del fichero
					contenidoFich = Ficheros.leerDeFichero(file);
					//hacemos el matcher con el contenido del ficher
					matitulo = titulo.matcher(contenidoFich);
					
					if (matitulo.find()) {// si enencontramos allguna cadena que case con el patrin del titulo
						//lo metemos en el buffer
						buffer.append(matitulo.group(1)
								+ System.getProperty("line.separator"));

					}
					//System.out.println(contenidoFich);
					palabrasClave(contenidoFich, buffer);
					dominios(contenidoFich, buffer);
					Email(contenidoFich, buffer);
					//System.out.println(buffer);
					
					//escribimos en un fichero el buffer
					File fichero = new File("resultadoBusqueda.txt");
					Ficheros.escribirEnFichero(buffer.toString(), fichero);
				}
			}

		} else
			System.out.println("No hay ficheros");

	}

}
