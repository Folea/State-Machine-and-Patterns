//package ejercicio4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
 * La clase conversor coge los datos de un fichero con extension .csv y
 * siguiendo un patron va creando un fichero .html con los datos recogidos del
 * fichero .csv
 */
public class Conversor {

	public static void main(String[] args) throws IOException {
		Scanner lectorDeTeclado = new Scanner(System.in);
		System.out.println("Introduce el nombre del fichero: ");

		String nombreFichero = lectorDeTeclado.nextLine(); // Leemos del teclado
															// el nombre del
															// fichero
		File fichero = new File(nombreFichero); // Creamos un fichero con el
												// nombre que hemos leido por
												// teclado

		StringBuffer buffer = new StringBuffer("<html><body>"
				+ System.getProperty("line.separator") // Creamos una cadena
														// donde se va a
														// almacenar
				+ "<center><h1> ConversiÃ³n de <i>" + nombreFichero // el
																	// contenido
																	// del del
																	// archivo
																	// que se va
																	// a
																	// exportar
																	// como html
				+ "</i> a Html</h1></center>");

		Pattern expresion = Pattern // creamos la expresión regular que nos va a
									// separar los datos por grupos
				.compile(("(\\p{Lu}\\p{Ll}{2,}(\\s+\\p{Lu}\\.)?)") // primer nombre primera letra con mayuscula y dos o mas minusculas
						//segundo nombre puede estar o no y es una letar mayuscula seguida de un punto
						+ ("(.+)") // apellido coge todo lo que viene detras del nombre
						+ (";(\\w[\\w. %+-]*@(\\w[\\w-]*\\.)+[a-zA-Z]{2,4})?")// EMAIL
						+ (";(\\w?\\d+\\p{L})")// al principio una letra que puede estar o no y luego los digitos y por ultimo una letra
						+ (";(\\+?\\d+)?")// el + que puede estar o no y luego los digitos
						+ (";(\\p{L}+\\d{4}\\p{L}{0,3})")// una letra o mas 4 digitos y de 0 a 3 letras
						+ (";((\\d{2})/(\\d{2})/(\\d{4}))?")// 2 digitos para el dia otros 2 para el mes y 4 para el año
						+ (";(\\d{4})(\\d{4})(\\d{2})(\\d{10})"));// los 20 numeros de cuenta agrupados en id  de la entidad, oficina...
		BufferedReader lectorDeFichero = new BufferedReader(new FileReader(
				fichero)); // leemos los datos del fichero
		String lineaLeida; // Creamos una cadena de caracteres donde se va a
							// almacenar cada linea del fichero que va a ser
							// tratada
		while ((lineaLeida = lectorDeFichero.readLine()) != null) { // Si quedan
																	// datos en
																	// el
																	// fichero
																	// se guarda
																	// la
																	// siguiente
																	// linea en
																	// lineaLeida
			Matcher matcherLineaLeida = expresion.matcher(lineaLeida); // tratamos
																		// lo
																		// que
																		// se ha
																		// leido
																		// con
																		// la
																		// expresion
																		// regular
																		// para
																		// separar
																		// los
																		// datos
																		// por
																		// grupos
			if (matcherLineaLeida.find()) { // si se ha guardado algun dato
				buffer.append(System.getProperty("line.separator")
						+ " <!-- Comienzo de datos de un cliente -->"
						+ System.getProperty("line.separator")
						+ "<hr><h3><i>Nombre: </i>" // se guarda en el buffer el
													// codigo html mas
						+ matcherLineaLeida.group(1) // el group(1) que es el
														// nombre
						+ "&nbsp&nbsp&nbsp&nbsp <i>Apellidos: </i>"
						+ matcherLineaLeida.group(3) + "</h3>"); // y el
																	// group(3)
																	// que es el
																	// apellido.

				if (matcherLineaLeida.group(4) != null) // si hay datos en el
														// group(4)
					buffer.append("<b>Email: </b><a href=\"mailto:" // significa
																	// que tiene
																	// un email
							+ matcherLineaLeida.group(4) + "\">" // lo
																	// insertamos
																	// en el
																	// buffer
																	// junto al
																	// codigo
																	// html
							+ matcherLineaLeida.group(4) + ("</a><br>"));

				buffer.append("<b>DNI/NIF: </b>" + matcherLineaLeida.group(6) // insertamos
																				// el
																				// DNI
																				// que
																				// se
																				// encuentra
																				// en
																				// el
																				// group(6)
						+ "<br>");

				if (matcherLineaLeida.group(7) != null) // Si tiene un telefono
					buffer.append("<b>TelÃ©fono:</b>" // lo insertamos en el
														// buffer junto a su
														// codigo html
							+ matcherLineaLeida.group(7) + "<br>");

				buffer.append("<b>Matricula: </b>" // Insertamos la matricula
													// que se encuentra en el
													// group(8)
						+ matcherLineaLeida.group(8) + "<br>");

				if (matcherLineaLeida.group(9) != null) // Si tiene una fecha de
														// matriculación, se
														// ubica en el group(9),
														// y se guarda junto a
														// su
					buffer.append("<b>Fecha de matriculaciÃ³n: </b> Dia " // correspondiente
																			// codigo
																			// html.
																			// El
																			// group(9)
																			// guarda
																			// la
																			// fecha
																			// entera
							+ matcherLineaLeida.group(10)
							+ ", Mes" // en el group 10,11,12 van separado el
										// dia, mes año.
							+ matcherLineaLeida.group(11)
							+ ", AÃ±o"
							+ matcherLineaLeida.group(12) + "<br>");

				buffer.append(System.getProperty("line.separator")
						+ "<b>CCC: </b> Entidad " // Insertamos la cuenta que va
													// separada por:
						+ matcherLineaLeida.group(13)
						+ ", Oficina " // group(13) entidad
						+ matcherLineaLeida.group(14) // group(14) oficina
						+ ", Dï¿½gitos de control DC "
						+ matcherLineaLeida.group(15) // group(15) DC
						+ ", Numero de cuenta CUENTA "
						+ matcherLineaLeida.group(16)); // group(16) Cuenta
				buffer.append(System.getProperty("line.separator")
						+ "<!-- Fin de datos de un cliente -->"
						+ System.getProperty("line.separator"));

			}
		}
		lectorDeFichero.close(); // Se cierra el lector del fichero
		buffer.append("</body></html>");
		Pattern patNombreFich = Pattern.compile("(.*)(.csv)"); // se introduce
																// la expresión
																// regular del
																// nombre de
																// fichero para
																// que solo
																// pueda leer
																// ficheros .csv
		Matcher matNombreFich = patNombreFich.matcher(nombreFichero); // se
																		// aplica
																		// la
																		// expresión
																		// regular
																		// al
																		// fichero
		if (matNombreFich.find()) { // en el caso de que tenga un formato
									// correcto se añade al matNombreFichero
			String nuevoFichero = matNombreFich.group(1) + ".html"; // se crea
																	// una
																	// cadena
																	// que va a
																	// contener
																	// el nombre
																	// del
																	// fichero.csv
																	// pero con
																	// la
																	// extension
																	// .html
			File ficheroHtml = new File(nuevoFichero); // se crea un fichero con
														// el nombre
			Ficheros.escribirEnFichero(buffer.toString(), ficheroHtml); // Se
																		// copia
																		// el
																		// buffer
																		// en el
																		// contenido
																		// del
																		// fichero

		}
	}
}
