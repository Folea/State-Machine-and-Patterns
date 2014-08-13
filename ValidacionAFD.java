//package ejercicio1;

import java.io.IOException;
import java.util.Scanner;
/**
 * 
 * @author Cristian Folea
 * @author Jose Manuel Lorenzo Salmeron
 *
 */
public class ValidacionAFD {

	public static boolean esValida(Afd automata, String cadena) {
		int posicion = 0;
		automata.reset();
		// se lee hasta el final de la cadena de entrada
		while (posicion < cadena.length()) {
			// se actualiza el simbolo actual.
			automata.setSimboloActual(cadena.charAt(posicion));

			if (automata.transicion(automata.getEstadoActual(),
					automata.getSimboloActual()) == null)
				return false;// no existe transicion

			else
				// se sigue recorriendo la cadena
				automata.setEstadoActual(automata.transicion(
						automata.getEstadoActual(), automata.getSimboloActual()));

			posicion++;

		}
		// se comprueba si el estado donde nos hemos quedado es final.
		// en caso que lo sea se acepta la cadena
		if (automata.esFinal(automata.getEstadoActual()))
			return true;
		return false;
	}

	public static void main(String[] args) throws IOException {
		Afd afd1 = new Afd("afd1.jff");
		Afd afdcomp = new Afd("afd1-comp.jff");

		System.out
				.println("introduzca una cadena o varias separadas por comas");

		Scanner scan = new Scanner(System.in);
		String listaDeJugadas = scan.nextLine();
		String[] jugadas = listaDeJugadas.split(",");

		// automata 1.
		System.out.println("AUTOMATA: afd1.jff");
		for (int i = 0; i < jugadas.length; i++) {

			if (esValida(afd1, jugadas[i]))
				System.out.println("La cadena " + jugadas[i] + " es valida.");
			else {
				System.out.println("La cadena " + jugadas[i] + " no es valida.");
				System.out.println("Ultimo estado :" + afd1.getEstadoActual()
						+ " \nUltimo simbolo: " + afd1.getSimboloActual());

			}

		}

		System.out.println("AUTOMATA: afd1-comp.jff");
		for (int i = 0; i < jugadas.length; i++) {

			if (esValida(afdcomp, jugadas[i]))
				System.out.println("La cadena " + jugadas[i] + " es valida.");
			else {
				System.out.println("La cadena " + jugadas[i] + " no es valida.");
				System.out.println("Ultimo estado :" + afd1.getEstadoActual()
						+ " \nUltimo simbolo: " + afd1.getSimboloActual());

			}
		}
	}
}