//package ejercicio3;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.callback.TextOutputCallback;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 * 
 * @author Cristian Folea
 * @author Jose Manuel Lorenzo Salmeron
 *
 */

/**
 * Esta clase crea un formulario, que tiene campos obligatorios indicados por
 * (*) y campos no obligatorios. Mediante las expresiones regulares el
 * formulario se encarga de que los datos introducidos tengan un formato
 * correcto. En el caso de que el formato de los datos introducidos sea
 * incorrecto, a la hora de enviar el formulario se indicara que un campo es
 * incorrecto y se resaltara la linea en color naranja. Una vez introducido los
 * datos de uno o varios clientes el formulario permite guardar los datos en un
 * fichero con extension .csv.
 */

@SuppressWarnings("serial")
public class Formulario extends JDialog implements KeyListener, ActionListener {

	// A�ADIR ATRIBUTOS NECESARIOS DEBAJO
	StringBuffer contenido = new StringBuffer("");
	Pattern patEmail = Pattern
			.compile("\\w[\\w.%+-]*@(\\w[\\w-]*\\.)+[a-zA-Z]{2,6}");// \w[\w.%+-]*@(\w[\w-]*\.)+[a-zA-Z]{2,6}
	Pattern patNombre = Pattern
			.compile("\\p{Lu}\\p{Ll}{2,}(\\s+\\p{Lu}\\.)?(\\s+\\p{Lu}\\p{Ll}{2,}\\s+(de|de\\s+la)\\s+\\p{Lu}\\p{Ll}{2,})(\\s+\\p{Lu}\\p{Ll}{2,}\\s+(de|de\\s+la)\\s+\\p{Lu}\\p{Ll}{2,})?"
					// nombre con los apellidos compuestos con de la
					// \p{Lu}\p{Ll}{2,}(\s+\p{Lu}\.|\s+\p{Lu}\p{Ll}{2,})?(\s+\p{Lu}\p{Ll}{2,}\s+(de|de\s+la)\s+\p{Lu}\p{Ll}{2,})(\s+\p{Lu}\p{Ll}{2,}\s+(de|de\s+la)\s+\p{Lu}\p{Ll}{2,})?
					+ "|\\p{Lu}\\p{Ll}{2,}(\\s+\\p{Lu}\\.)?(\\s+\\p{Lu}\\p{Ll}{2,}(-\\p{Lu}\\p{Ll}{2,})?)(\\s+\\p{Lu}\\p{Ll}{2,}(-\\p{Lu}\\p{Ll}{2,})?)?"
					// nombre con los apellidos compuestos con -
					// \p{Lu}\p{Ll}{2,}(\s+\p{Lu}\.|\s+\p{Lu}\p{Ll}{2,})?(\s+\p{Lu}\p{Ll}{2,}(-\p{Lu}\p{Ll}{2,})?)(\s+\p{Lu}\p{Ll}{2,}(-\p{Lu}\p{Ll}{2,})?)?
					+ "|\\p{Lu}\\p{Ll}{2,}(\\s+\\p{Lu}\\.)?(\\s+\\p{Lu}\\p{Ll}{2,})(\\s+\\p{Lu}\\p{Ll}{2,})?");
	// \p{lu}\p{Ll}{2,}(\s+\p{lu}\.||\p{lu}\p{Ll}{2,})(\s+\p{lu}\p{Ll}{2,})(\s+\p{lu}\p{Ll}{2,})?
	// TODO Cambiar expresion regular Nombre
	// nombre con dos o un apellido
	// En todas se permite el nombre compuesto.

	Pattern patDni = Pattern.compile("[XYZ]?(\\d){7}-?[A-Z]|\\d{8}-?[A-Z]");
	Pattern patTelfono = Pattern
			.compile("(\\+\\d{1,3}\\s+)?\\d{3}((\\s+\\d{2}){3}|(\\s+\\d{3}){2}|\\d{6})");
	Pattern patCCC = Pattern.compile("\\d{4}-\\d{4}-?\\d{2}-?\\d{10}|\\d{20}");// \d{4}-?\d{4}-?\d{2}-?\d{10}|\d{20}
	Pattern patMatricula = Pattern
			.compile("(E\\d{4}[A-Z]{3})|([A-Z]{1,2}\\d{4}[A-Z]{0,3})"); // (E\d{4}[A-Z]{3})|([A-Z]{1,2}\d{4}[A-Z]{0,3})
	Pattern patFechaMatriculacion = Pattern// (0[1-9]|3[01]|[12][0-9])/(0[0-9]|1[0-2])/(19(7[1-9]|[89][0-9]))|20(0[0-9]|1[012])
			.compile("(0[1-9]|[123][0-9])/[01][0-9]/[12][0-9]([789][0-9]|0[0-9]|1[012])");

	// DECLARACI�N y CREACI�N DE LOS COMPONENTES GR�FICOS DE LA VENTANA
	private JButton botonEnviar = new JButton("Enviar"); // al pulsarlo se
															// disparar� la
															// validaci�n de
															// los datos
	private JLabel labelMensaje = new JLabel("(*) indica campo obligatorio"); // etiqueta
																				// aclaratoria
																				// sobre
																				// obligatoriedad
																				// de
																				// campos

	private JLabel labelNombre = new JLabel("(*) Apellidos, Nombre"); // etiqueta
																		// que
																		// acompa�a
																		// al
																		// campo
																		// de
																		// texto
																		// del
																		// nombre
	private JTextField textNombre = new JTextField(30); // campo de texto para
														// introducir nombre de
														// anchura 30 caracteres
														// m�ximo
	private JLabel labelDni = new JLabel("(*) DNI/NIE"); // etiqueta que
															// acompa�a al campo
															// de texto del DNI
	private JTextField textDni = new JTextField(30); // campo para introducir un
														// DNI de maximo 30
														// caracteres, aunque la
														// expresion regular no
														// lo permite
														// se ha elegido este
														// tama�o del campo para
														// que el formulario
														// quede uniforme
	private JLabel labelEmail = new JLabel("E-Mail");// etiqueta que acompa�a
														// al campo de texto de
														// email
	private JTextField textEmail = new JTextField(30); // campo para introducir
														// un email de 30
														// caracteres maximoximo
	private JLabel labelTelefono = new JLabel("Telefono"); // etiqueta que
															// acompa�ar al
															// campo de texto
															// del telefono
	private JTextField textTelefono = new JTextField(30);// campo para
															// introducir un
															// Telefono de
															// maximo 30
															// caracteres,
															// aunque la
															// expresion regular
															// no lo permite
															// se ha elegido
															// este tama�o del
															// campo para que el
															// formulario quede
															// uniforme
	private JLabel labelMatricula = new JLabel("(*) Matricula del vehiculo"); // etiqueta
																				// para
																				// acompa�ar
																				// al
																				// campo
																				// de
																				// texto
																				// Matricula
	private JTextField textMatricula = new JTextField(30); // campo para
															// introducir una
															// Matricula de
															// maximo 30
															// caracteres,
															// aunque la
															// expresion regular
															// no lo permite
															// se ha elegido
															// este tama�o del
															// campo para que el
															// formulario quede
															// uniforme
	private JLabel labelFechaMatriculacion = new JLabel("Fecha Matriculación"); // etiqueta
																					// para
																					// acompa�ar
																					// al
																					// campo
																					// de
																					// texto
																					// Fecha
																					// Matriculaci�n
	private JTextField textFechaMatriculacion = new JTextField(30); // campo
																	// para
																	// introducir
																	// Fecha de
																	// Matriculaci�n
																	// de maximo
																	// 30
																	// caracteres,
																	// aunque la
																	// expresion
																	// regular
																	// no lo
																	// permite
																	// se ha
																	// elegido
																	// este
																	// tama�o
																	// del campo
																	// para que
																	// el
																	// formulario
																	// quede
																	// uniforme
	private JLabel labelCCC = new JLabel("(*) CCC"); // etiqueta para acompa�ar
														// al campo de texto de
														// la cuenta
	private JTextField textCCC = new JTextField(30); // campo para introducir
														// una CCC de maximo 30
														// caracteres, aunque la
														// expresion regular no
														// lo permite
														// se ha elegido este
														// tama�o del campo para
														// que el formulario
														// quede uniforme

	// --------DECLARAR DEBAJO M�S COMPONENTES DE ETIQUETA Y CAMPO DE TEXTO AL
	// AMPLIAR FORMULARIO------------

	/**
	 * Constructor de Formulario.
	 */
	public Formulario() {
		super(); // crea ventana JDialog vac�a
		this.setModal(true); // la ventana permanece a la espera hasta pulsar
								// cerrar o enviar
		this.setTitle("Formulario"); // establece el titulo en la barra de la
										// ventana
		this.setSize(350, 400); // tama�o de la ventana

		// CREA UN PANEL CONTENEDOR PARA A�ADIR COMPONENTES A LA VENTANA
		Container panel = this.getContentPane();
		panel.setLayout(new FlowLayout());
		// indica que se colocar�n componentes de izda-der, arriba-abajo
		// seg�n se a�aden

		// A�ADE LOS COMPONENTES GRAFICOS DECLARADOS AL PANEL CONTENEDOR DE LA
		// VENTANA, POR ORDEN
		panel.add(labelNombre); // a�ade la etiqueta que acompa�a al campo de
								// texto Nombre
		panel.add(textNombre); // a�ade el campo de texto Nombre
		panel.add(labelEmail); // a�ade la etiqueta que acompa�a al campo de
								// texto Email
		panel.add(textEmail); // a�ade el campo de texto Email
		panel.add(labelDni); // a�ade la etiqueta que acompa�a al campo de texto
								// DNI
		panel.add(textDni); // a�ade el campo de texto DNI
		panel.add(labelTelefono); // a�ade la etiqueta que acompa�a al campo de
									// texto Telefono
		panel.add(textTelefono); // a�ade el campo de texto Telefono
		panel.add(labelMatricula); // a�ade la etiqueta que acompa�a al campo de
									// texto Matricula
		panel.add(textMatricula); // a�ade el campo de texto Matricula
		panel.add(labelFechaMatriculacion); // a�ade la etiqueta que acompa�a al
											// campo de texto FechaMatriculaci�n
		panel.add(textFechaMatriculacion); // a�ade el campo de texto
											// FechaMatriculaci�n
		panel.add(labelCCC); // a�ade la etiqueta que acompa�a al campo de texto
								// CCC
		panel.add(textCCC); // a�ade el campo de texto CCC

		// -------- A�ADIR COMPONENTES AL PANEL AL AMPLIAR FORMULARIO (como
		// arriba) -------

		// -------- FIN DE ZONA DE A�ADIR COMPONENTES NUEVOS -----------

		panel.add(labelMensaje); // a�ade el mensaje aclaratorio al panel
		panel.add(botonEnviar); // �ltimo componente a�adido al panel

		// A�ADE OYENTES (listeners) DE EVENTOS
		/*
		 * A botonEnviar se le asocia un ActionListener para que el bot�n
		 * "oiga" el evento de pulsaci�n. Si el "oyente del bot�n" detecta
		 * pulsaci�n --> dispara m�todo actionPerformed
		 */
		botonEnviar.addActionListener(this);

		/*
		 * A cada campo de texto JTextField se le asocia un addKeyListener para
		 * que "oiga" el evento de pulsaci�n de tecla y en ese caso -->
		 * dispara m�todo keyTyped
		 */
		textNombre.addKeyListener(this); // addKeyListener correspondiente al
											// campo Nombre
		textDni.addKeyListener(this); // addKeyListener correspondiente al campo
										// DNI
		textEmail.addKeyListener(this); // addKeyListener correspondiente al
										// campo Email
		textCCC.addKeyListener(this); // addKeyListener correspondiente al campo
										// CCC
		textFechaMatriculacion.addKeyListener(this); // addKeyListener
														// correspondiente al
														// campo
														// FechaMatriculaci�n
		textMatricula.addKeyListener(this); // addKeyListener correspondiente al
											// campo Matricula
		textTelefono.addKeyListener(this); // addKeyListener correspondiente al
											// campo Telefono

		// -----A�ADIR OYENTES DE CAMPOS DE TEXTO AL AMPLIAR FORMULARIO (como
		// arriba) ---

		// --------FIN DE ZONA DE A�ADIR OYENTES -----------

		/*
		 * GESTION DE EVENTO DE CIERRE DE VENTANA: Se asocia un oyente a la
		 * ventana y se indica que al detectar el evento de cerrar la ventana
		 * guarde en fichero los datos recogidos de todos los formularios y
		 * retorne. (NO HAY QUE MODIFICAR m�todo windowClosing).
		 */
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				guardarTodo(); // pasa a guardar datos v�lidos de todos los
								// clientes en un fichero

			}
		});

		setVisible(true); // hace visible la ventana del formulario

	} // FIN-CONSTRUCTOR

	// ///////////// M�TODOS DE REACCI�N A EVENTOS ////////////

	/**
	 * Implementaci�n de m�todo ActionPerformed. Se dispara por evento de
	 * pulsaci�n de boton y pasa a validar el formulario para un cliente. (NO
	 * MODIFICAR ESTE M�TODO).
	 * 
	 * @param eventoBoton
	 *            Evento de pulsaci�n de bot�n detectado
	 */
	public void actionPerformed(ActionEvent eventoBoton) {
		procesarDatos(); // pasa a procesar los campos del formulario
							// correspondientes a un cliente

	}

	/**
	 * Implementaci�n de m�todo keyTyped. Se dispara por evento de tecleo en
	 * un de campo de texto (evento de tipo KeyEvent) y en este caso se cambia
	 * el fondo del campo de texto a blanco (modo normal). Como hay varios
	 * campos entonces se tiene que saber qu� campo ha generado el evento
	 * (source) y para ello se usa eventoTecla.getSource()
	 * 
	 * @param eventoTecla
	 *            Evento de pulsaci�n de tecla detectado en campo de texto
	 */
	public void keyTyped(KeyEvent eventoTecla) {
		if (eventoTecla.getSource() == textNombre)
			// se tecle� en el campo de introducci�n de nombre de usuario
			textNombre.setBackground(Color.white);
		else if (eventoTecla.getSource() == textEmail)
			// se tecle� en el campo de email de usuario
			textEmail.setBackground(Color.white);
		else if (eventoTecla.getSource() == textDni)
			textDni.setBackground(Color.white); // se teclea en el campo de
												// introducci�n de DNI
		else if (eventoTecla.getSource() == textTelefono)
			textTelefono.setBackground(Color.white); // se teclea en el campo de
														// introducci�n de
														// Telefono
		else if (eventoTecla.getSource() == textCCC)
			textCCC.setBackground(Color.white); // se teclea en el campo de
												// introducci�n de CCC
		else if (eventoTecla.getSource() == textFechaMatriculacion)
			textFechaMatriculacion.setBackground(Color.white); // se teclea en
																// el campo de
																// introducci�n
																// de
																// FechaMatriculaci�n
		else if (eventoTecla.getSource() == textMatricula)
			textMatricula.setBackground(Color.white); // se teclea en el campo
														// de introducci�n de
														// Matricula

		// --- A�ADIR CASO PARA CADA JtextField EXTRA AL AMPLIAR FORMULARIO --

	}

	public void keyPressed(KeyEvent e) {
	} // NO MODIFICAR: no es necesario indicar acciones, pero hay que dejarlo

	public void keyReleased(KeyEvent e) {
	} // NO MODIFICAR: no es necesario indicar acciones, pero hay que dejarlo

	/**
	 * M�todo que permite resaltar fondo de campo de texto. Se invoca cuando
	 * se ha detectado error de formato en la validaci�n del texto (NO HAY QUE
	 * MODIFICARLO).
	 * 
	 * @param campo
	 *            Es un campo de texto que se desea resaltar
	 */
	public void resaltaCampo(JTextField campo) {
		campo.setBackground(Color.orange);
	}

	// //////////// M�TODOS RELACIONADOS CON LA VALIDACI�N y ALMACENAMIENTO
	// DE DATOS ////////////

	/**
	 * Este m�todo comprueba que los datos introducidos en el formulario son
	 * correctos y, en el caso de que no lo sean devuelve un panel de error con
	 * el campo correspondiente y resalta la casilla en naranja
	 */
	private void procesarDatos() {
		Matcher matNombre = patNombre.matcher(textNombre.getText()); // aplica
																		// la
																		// expresi�n
																		// regular
																		// y en
																		// el
																		// caso
																		// de
																		// que
																		// el
																		// nombre
																		// sea
																		// correcto
																		// lo
																		// guarda
																		// en
																		// matNombre
		Matcher matDni = patDni.matcher(textDni.getText()); // aplica la
															// expresi�n regular
															// y en el caso de
															// que el DNI sea
															// correcto lo
															// guarda en matDNI
		Matcher matTelefono = patTelfono.matcher(textTelefono.getText()); // aplica
																			// la
																			// expresi�n
																			// regular
																			// y
																			// en
																			// el
																			// caso
																			// de
																			// que
																			// el
																			// Telefono
																			// sea
																			// correcta
																			// la
																			// guarda
																			// en
																			// matTelefono
		Matcher matMatricula = patMatricula.matcher(textMatricula.getText()); // aplica
																				// la
																				// expresi�n
																				// regular
																				// y
																				// en
																				// el
																				// caso
																				// de
																				// que
																				// la
																				// Matricula
																				// sea
																				// correcta
																				// la
																				// guarda
																				// en
																				// matMatricula
		Matcher matFechaMatricula = patFechaMatriculacion
				.matcher(textFechaMatriculacion.getText());// aplica la
															// expresi�n regular
															// y en el caso de
															// que la Fecha
															// Matriculaci�n sea
															// correcta lo
															// guarda en
															// matFechaMAtriculaci�n
		Matcher matEmail = patEmail.matcher(textEmail.getText()); // aplica la
																	// expresi�n
																	// regular y
																	// en el
																	// caso de
																	// que el
																	// Email sea
																	// correcto
																	// lo guarda
																	// en
																	// matEmail
		Matcher matCCC = patCCC.matcher(textCCC.getText()); // aplica la
															// expresi�n regular
															// y en el caso de
															// que el CCC sea
															// correcto lo
															// guarda en matCCC

		if (!matNombre.matches()) { // si al aplicar el patron el formato del
									// Nombre introducido no es correcto
			resaltaCampo(textNombre); // resalta el campo
			JOptionPane.showMessageDialog(null, "El nombre es incorrecto",
					"Error", JOptionPane.ERROR_MESSAGE); // muestra un mensaje
															// de error
		} else if (!matDni.matches()) { // si al aplicar el patron el formato
										// del DNI introducido no es correcto
			resaltaCampo(textDni); // resalta el campo
			JOptionPane.showMessageDialog(null, "El DNI es incorrecto",
					"Error", JOptionPane.ERROR_MESSAGE); // y muestra un mensaje
															// de error
		} else if (!matTelefono.matches() && !textTelefono.getText().isEmpty()) { // si
																					// al
																					// aplicar
																					// el
																					// patron
																					// el
																					// formato
																					// del
																					// Telefono,
																					// en
																					// el
																					// caso
																					// de
																					// que
																					// el
																					// campo
																					// no
																					// es
																					// vacio,
																					// no
																					// es
																					// correcto
			resaltaCampo(textTelefono); // resalta el campo
			JOptionPane.showMessageDialog(null, "El Telefono es incorrecto",
					"Error", JOptionPane.ERROR_MESSAGE); // muestra un mensaje
															// de error
		} else if (!matMatricula.matches()) { // si al aplicar el patron el
												// formato de la Matricula no es
												// correcto
			resaltaCampo(textMatricula); // resalta el campo
			JOptionPane.showMessageDialog(null, "La matricula es incorrecta",
					"Error", JOptionPane.ERROR_MESSAGE); // muestra un mensaje
															// de error
		} else if (!matFechaMatricula.matches()
				&& !textFechaMatriculacion.getText().isEmpty()) { // si al
																	// aplicar
																	// el patron
																	// el
																	// formato
																	// de la
																	// Fecha de
																	// Matriculaci�n,
																	// en el
																	// caso de
																	// que el
																	// campo no
																	// es vacio,
																	// no es
																	// correcto
			resaltaCampo(textFechaMatriculacion); // resalta el campo
			JOptionPane.showMessageDialog(null,
					"La fecha de la matricula es incorrecta", "Error",
					JOptionPane.ERROR_MESSAGE); // muestra mensaje de error
		} else if (!matEmail.matches() && !textEmail.getText().isEmpty()) { // si
																			// al
																			// aplicar
																			// el
																			// patron
																			// el
																			// formato
																			// del
																			// Email,
																			// en
																			// el
																			// caso
																			// de
																			// que
																			// el
																			// campo
																			// no
																			// es
																			// vacio,
																			// no
																			// es
																			// correcto
			resaltaCampo(textEmail); // resalta el campo
			JOptionPane.showMessageDialog(null, "El Email es incorrecto",
					"Error", JOptionPane.ERROR_MESSAGE); // muestra un mensaje
															// de error
		} else if (!matCCC.matches()) { // si al aplicar el patron el formato
										// del CCC no es el correcto
			resaltaCampo(textCCC); // resalta el campo
			JOptionPane.showMessageDialog(null,
					"El formato de la cuenta es incorrecto", "Error",
					JOptionPane.ERROR_MESSAGE); // muestra un mensaje de error
		} else {
			JOptionPane.showMessageDialog(null, "El formulario es correcto",
					"Correcto", JOptionPane.INFORMATION_MESSAGE); // en el caso
																	// de que
																	// todo sea
																	// correcto
			contenido.append(textNombre.getText().replaceAll("\\s", " ")); // guarda
																			// el
																			// nombre
																			// en
																			// una
																			// cadena
			contenido.append(";"); // introduce un ; en la cadena para separar
									// los datos
			contenido.append(textEmail.getText()); // guarda el Email en la
													// cadena
			contenido.append(";"); // introduce un ; en la cadena para separar
									// los datos
			contenido.append(textDni.getText().replace("[-]", "")); // guarda el
																	// DNI en la
																	// cadena
																	// quitando
																	// las -
			contenido.append(";"); // introduce un ; en la cadena para separar
									// los datos
			contenido.append(textTelefono.getText().replaceAll(" ", "")); // guarda
																			// el
																			// Telefono
																			// en
																			// la
																			// cadena
																			// quitando
																			// los
																			// espacios
			contenido.append(";"); // introduce un ; en la cadena para separar
									// los datos
			contenido.append(textMatricula.getText()); // guarda la Matricula en
														// la cadena
			contenido.append(";"); // introduce un ; en la cadena para separar
									// los datos
			contenido.append(textFechaMatriculacion.getText()); // guarda la
																// Fecha de
																// Matriculaci�n
																// en la cadena
			contenido.append(";"); // introduce un ; en la cadena para separar
									// los datos
			contenido.append(textCCC.getText().replaceAll("[-]", "")); // guarda
																		// el
																		// CCC
																		// en la
																		// cadena
																		// quitando
																		// las -
			contenido.append("\n"); // guarda un salto de linea
			textCCC.setText(""); // Vacia el campo
			textDni.setText(""); // Vacia el campo
			textEmail.setText(""); // Vacia el campo
			textFechaMatriculacion.setText(""); // Vacia el campo
			textMatricula.setText(""); // Vacia el campo
			textNombre.setText(""); // Vacia el campo
			textTelefono.setText(""); // Vacia el campo
		}
	}

	/**
	 ** Este m�todo se lanza al cerrar la ventana del formulario y te pregunta
	 * donde quieres guardar el fichero; no hay que poner .csv ya que lo guarda
	 * solo.
	 */
	private void guardarTodo() {

		String fichero = JOptionPane
				// Pide el nombre del Fichero que se va a guardar
				.showInputDialog(
						null,
						"Introduzca el nombre del fichero donde quiere guardar los datos:",
						"Entrada", JOptionPane.QUESTION_MESSAGE);
		if (fichero == null) // si se le ha introducido un nombre
			return;
		fichero = fichero + ".csv"; // se le a�ade la extension .csv
		File ficheroDatos = new File(fichero); // se crea un fichero con ese
												// nombre
		try {

			Ficheros.escribirEnFichero(contenido.toString(), ficheroDatos); // se
																			// a�aden
																			// los
																			// datos
																			// al
																			// fichero
																			// creado

		} catch (FileNotFoundException e) {
			guardarTodo();
		}

	}

	// INCLUIR EL RESTO DE M�TODOS

	/**
	 * Lanza la ventana del formulario.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		Formulario formularioClientes = new Formulario();
		// lanza el formulario y termina cuando se cierra la ventana (ejecuta
		// m�todo windowClosing)
		formularioClientes.dispose(); // destruye el formulario y finaliza

	}

}
