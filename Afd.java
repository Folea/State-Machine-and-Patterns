


import java.io.IOException;
import java.util.*;
/**
 * La clase Afd representa autómatas finitos deterministas  y  proporciona métodos
 *  necesarios para  validar cadenas carácter a carácter o aplicar transiciones simulando el funcionamiento teórico  de un AFD.
 * @author Los profesores 
 */

public class Afd{
	/*El  conjunto de estados no se declara: cada estado se representa con un nombre único, que es una cadena de caracteres. */ 
	private Set<String>  alfabeto; 
	/** Representa el alfabeto   del autómata */
	private Map<String, Map<String, String>> tablaTransicion; 	
	/** Representa  la función de transición */
	private Set<String>  finales; 
	/** Representa el conjunto de estados finales */
	private String estadoInicial;
	/** Representa el estado inicial del autómata */
	private String estadoActual;
	/** Representa el estado actual del autómata en cierto instante de ejecución */
	private Character simboloActual; 
	/** Representa el símbolo actual del autómata en cierto instante de ejecución;
	 * se supone que será instanciado con un carácter (símbolo representado con carácter) 
	 */  

	/**
	 * Construye un  Afd a partir de un fichero .jff de JFLAP
	 * @param nombreFichero Nombre del fichero .jff que contiene la información del autómata finito.
	 * @trows IOException Excepción que se lanza si hay algún error al leer el fichero.
	 */

	public Afd (String nombreFichero) throws IOException {
		LectorAFD_JFLAP lectorAFD = new LectorAFD_JFLAP();
		lectorAFD.leeFichero(nombreFichero);
		this.alfabeto = lectorAFD.getAlfabeto();
		this.estadoInicial = lectorAFD.getEstadoInicial();
		this.finales = lectorAFD.getEstadosFinales();
		this.tablaTransicion = lectorAFD.getTablaTransicion();
	}

	////////////////////// OTROS MÉTODOS PUBLICOS DE Afd 
	/**
	 * Para resetear el autómata: comienza por estado inicial y simbolo inicial nulo.
	 */
	public void reset(){
		this.estadoActual = this.estadoInicial; //comienza simulación por estado inicial
		this.simboloActual='\0'; //carácter nulo al inicio de ejecución
	}

	/**
	 * Para obtener el estadoActual
	 */
	public String getEstadoActual(){
		return this.estadoActual;
	}
	
	/**
	 * Para establecer el estadoActual
	 */
	public void setEstadoActual(String estado){
		this.estadoActual=estado;
	}
	
	/**
	 * Para obtener el simboloActual
	 */

	public Character getSimboloActual(){
		return this.simboloActual;
	}


	/**
	 * Para establecer el simboloActual
	 */
	public void setSimboloActual(Character simbolo){
		this.simboloActual=simbolo;
	}


	/**
	 *  El método transición devuelve el nombre de estado al que hay que cambiar según tabla de 
	 *  transición para la  combinación de valores de los parámetros. 
	 *  Si no hay transición definida en AFD para esa combinación (includo el caso de  símbolo  
	 *  no válido o estado no válido) entonces devuelve null. 
	 * @param estado Es un nombre de estado válido del autómata.
	 * @param simbolo De tipo char(símbolo representado como un carácter)
	 */
	public String transicion(String estado, Character simbolo){
		

		// Si el alfabeto no contiene a símbolo entonces devuelve null
		// Sino accede a la tabla de transicion. Si la tabla de transición no tiene una entrada para el estado y el símbolo
		// entonces devuelve null
		if (!this.alfabeto.contains(simbolo.toString()))
			return null;
		else if (this.tablaTransicion.get(estado) == null)
			return null;
		else
			return this.tablaTransicion.get(estado).get(simbolo.toString()); 
	}

	/**
	 * Comprueba si un estado es final
	 * @param estado
	 * @return true si encontrado
	 */
	public boolean esFinal(String estado){
		return this.finales.contains(estado);
		
	}


} //end-Afd

