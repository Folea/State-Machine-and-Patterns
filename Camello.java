//package ejercicio2;

import java.io.IOException;
import java.util.Scanner;

import ejercicio1.Afd;

/**
 * 
 * @author Cristian Folea
 * @author Jose Manuel Lorenzo Salmeron
 *
 */

/**
 * Clase que simula el juego de los camellos.
 */
public class Camello {
	// -------------------------------UN
	// JUGADOR---------------------------------------
	/**
	 * El metodo UnJugador representa el metodo de jugada de un jugador.
	 */
	public static void UnJugador(String[] jugada, Afd camellouno)
			throws IOException {
		System.out.println("--- Modo 1 Jugador ---");
		for (String jugada1 : jugada) { // Recorremos todas las jugadas cogiendo
										// una por una para tratarla
			int noavanza = 0; // variable representa que la bola ha caido por no
								// avanza
			int avanza = 0; // variable representa que la bola ha caido por
							// avanza
			camellouno.reset(); // reseteamos el automata
			boolean trampa = false; // variable que representa que el jugador ha
									// hecho trampa
			System.out.print("\nJugada " + jugada1 + ": ");

			for (int i = 0; i < jugada1.length(); i++) { // cogemos de la
															// jugada, la
															// introducción de
															// cada bola
				camellouno.setSimboloActual(jugada1.charAt(i)); // le asignamos
																// al simbolo
																// actual del
																// automata el
																// simbolo que
																// cogemos de la
																// jugada

				if ((camellouno.transicion(camellouno.getEstadoActual(), // si
																			// la
																			// transición
																			// con
																			// el
																			// simbolo
																			// que
																			// se
																			// le
																			// ha
																			// pasado
																			// es
																			// posible
						camellouno.getSimboloActual())) != null && !trampa) { // y
																				// el
																				// jugador
																				// no
																				// ha
																				// hecho
																				// trampa
					camellouno.setEstadoActual(camellouno.transicion( // le
																		// asignamos
																		// el
																		// estado
																		// actual,
																		// el
																		// nuevo
																		// estado
							camellouno.getEstadoActual(), // que se obtiene
															// despues de
															// realizar la
															// transición
							camellouno.getSimboloActual()));
					if (camellouno.esFinal(camellouno.getEstadoActual())) { // si
																			// el
																			// nuevo
																			// estado
																			// es
																			// final
						avanza++;
						System.out.print(" " + jugada1.charAt(i) + "-A");

					} else {
						noavanza++;
						System.out.print(" " + jugada1.charAt(i) + "-N");
					}
				} else {
					if (!trampa) { // si el jugado hace trampa
						trampa = true;
						System.out.print(" " + camellouno.getSimboloActual()
								+ "*T* --> PIERDE");
					}
				}
			}
			if (avanza > noavanza)
				System.out.print(" --> GANA: " + avanza * 2 + "euros");
			else if (!trampa)
				System.out.print(" ---> PIERDE");
		}
	}

	// ------------------------------------ MULTIJUGADOR
	// ---------------------------
	/**
	 * El metodo MultiJugador representa el modo de juego de dos jugadores
	 * 
	 * @param jugadas
	 *            las jugadas que realizan los jugadores
	 * @param automata1
	 *            automata del primer jugador
	 * @param automata2
	 *            automata del segundo jugador
	 */
	public static void MultiJugador(String[] jugadas, Afd automata1,
			Afd automata2) {
		System.out.print("\n--- Modo 2 jugadores --");
		for (String jugada1 : jugadas) { // cogemos las jugadas una por una
			int avanza1 = 0; // la bola del primer jugador cae por avanza
			int noavanza1 = 0; // la bola del segundo jugador cae por no avanza
			boolean trampa1 = false; // el primer jugado hace trampa
			int avanza2 = 0; // la bola del segundo jugador cae por avanza
			int noavanza2 = 0; // la bola del segundo jugador cae por no avanza
			boolean trampa2 = false; // el segundo jugador hace trampa
			automata1.reset(); // reseteamos el automata 1
			automata2.reset(); // reseteamos el automata 2
			String tr1 = "";
			String tr2 = "";
			for (int i = 0; i < jugada1.length(); i += 2) { // cogemos el
															// agujero por el
															// que introduce la
															// bola el jugador 1
				automata1.setSimboloActual(jugada1.charAt(i)); // le asignamos
																// el al simbolo
																// actual el
																// agujero por
																// donde se va a
																// introducir la
																// bola
				if ((automata1.transicion(automata1.getEstadoActual(), // si la
																		// transición
																		// es
																		// posible
																		// y no
																		// ha
																		// cometido
																		// trapa
						automata1.getSimboloActual())) != null && !trampa1) {
					automata1.setEstadoActual(automata1.transicion(
							// se realiza la transición y se le asigna el nuevo
							// estado al estado actual
							automata1.getEstadoActual(),
							automata1.getSimboloActual()));
					if (automata1.esFinal(automata1.getEstadoActual())) // si el
																		// estado
																		// es
																		// final
						avanza1++;
					else
						// si el estado no es final
						noavanza1++;
				} else {
					if (!trampa1) { // si hace trampa
						trampa1 = true;
						tr1 = tr1 + automata1.getSimboloActual(); // guardamos
																	// la trampa
					}
				}
				// ///////////JUGADOR 2
				if ((i + 1) < jugada1.length()) { // el agujero por el que
													// introduce la bola el
													// segundo jugador
					automata2.setSimboloActual(jugada1.charAt(i + 1)); // le
																		// asignamos
																		// el
																		// simbolo
																		// actual
					if ((automata2.transicion(automata2.getEstadoActual(), // si
																			// la
																			// transición
																			// es
																			// posible
																			// y
																			// el
																			// segundo
																			// jugador
																			// no
																			// ha
																			// hecho
																			// trampa
							automata2.getSimboloActual()) != null && !trampa2)) {
						automata2.setEstadoActual(automata2.transicion(
								// se le asigna el nuevo estado al automata
								automata2.getEstadoActual(),
								automata2.getSimboloActual()));
						if (automata2.esFinal(automata2.getEstadoActual())) // si
																			// es
																			// final
							avanza2++;

						else
							// si no es final
							noavanza2++;
					} else {
						if (!trampa2) {
							trampa2 = true;
							tr2 = tr2 + automata2.getSimboloActual();
						}
					}
				}
			}
			System.out.print("\nJugada " + jugada1 + ": ");
			if (trampa1)
				System.out.print(tr1 + "*T*");
			if (trampa2)
				System.out.print(tr2 + "*T*");
			System.out.print(" J1(");
			for (int i = 0; i < jugada1.length(); i += 2) {
				System.out.print(jugada1.charAt(i));
			}
			System.out.print("), J2(");
			for (int i = 1; i < jugada1.length(); i += 2) {
				System.out.print(jugada1.charAt(i));
			}
			System.out.print(")");
			if ((avanza1 == avanza2) || (trampa1 && trampa2))
				System.out.print(" ---> EMPATE");
			else if (avanza1 > avanza2)
				System.out.print(" ---> GANA J1: " + avanza1 * 2 + " euros");
			else if (avanza2 > avanza1)
				System.out.print(" ---> GANA J2: " + avanza2 * 2 + " euros");
			else if ((avanza1 > 0) && trampa2)
				System.out.print(" ---> GANA J1: " + avanza1 * 2 + " euros");
			else if ((avanza2 > 0) && trampa1)
				System.out.print(" ---> GANA J2: " + avanza2 * 2 + " euros");
			else
				System.out.print(" ---> EMPATE");

		}

	}

	public static void main(String[] args) throws IOException {
		System.out.println("Introduzca su Jugada");
		Scanner scan = new Scanner(System.in);
		String jugada = scan.nextLine(); // cogemos del teclado las jugadas
		String[] jugadas = jugada.split(","); // las guardamos en un array de
												// string las jugadas
		Afd camellos = new Afd("camellos.jff"); // cargamos el atuomata en
												// camellos
		UnJugador(jugadas, camellos);

		Afd afdCamello1 = new Afd("camellos.jff");
		Afd afdCamello2 = new Afd("camellos.jff");
		MultiJugador(jugadas, afdCamello1, afdCamello2);

	}

}
