


import java.io.IOException;
import java.util.*;
/**
 * La clase Afd representa aut�matas finitos deterministas  y  proporciona m�todos
 *  necesarios para  validar cadenas car�cter a car�cter o aplicar transiciones simulando el funcionamiento te�rico  de un AFD.
 * @author Los profesores 
 */

public class Afd{
	/*El  conjunto de estados no se declara: cada estado se representa con un nombre �nico, que es una cadena de caracteres. */ 
	private Set<String>  alfabeto; 
	/** Representa el alfabeto   del aut�mata */
	private Map<String, Map<String, String>> tablaTransicion; 	
	/** Representa  la funci�n de transici�n */
	private Set<String>  finales; 
	/** Representa el conjunto de estados finales */
	private String estadoInicial;
	/** Representa el estado inicial del aut�mata */
	private String estadoActual;
	/** Representa el estado actual del aut�mata en cierto instante de ejecuci�n */
	private Character simboloActual; 
	/** Representa el s�mbolo actual del aut�mata en cierto instante de ejecuci�n;
	 * se supone que ser� instanciado con un car�cter (s�mbolo representado con car�cter) 
	 */  

	/**
	 * Construye un  Afd a partir de un fichero .jff de JFLAP
	 * @param nombreFichero Nombre del fichero .jff que contiene la informaci�n del aut�mata finito.
	 * @trows IOException Excepci�n que se lanza si hay alg�n error al leer el fichero.
	 */

	public Afd (String nombreFichero) throws IOException {
		LectorAFD_JFLAP lectorAFD = new LectorAFD_JFLAP();
		lectorAFD.leeFichero(nombreFichero);
		this.alfabeto = lectorAFD.getAlfabeto();
		this.estadoInicial = lectorAFD.getEstadoInicial();
		this.finales = lectorAFD.getEstadosFinales();
		this.tablaTransicion = lectorAFD.getTablaTransicion();
	}

	////////////////////// OTROS M�TODOS PUBLICOS DE Afd 
	/**
	 * Para resetear el aut�mata: comienza por estado inicial y simbolo inicial nulo.
	 */
	public void reset(){
		this.estadoActual = this.estadoInicial; //comienza simulaci�n por estado inicial
		this.simboloActual='\0'; //car�cter nulo al inicio de ejecuci�n
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
	 *  El m�todo transici�n devuelve el nombre de estado al que hay que cambiar seg�n tabla de 
	 *  transici�n para la  combinaci�n de valores de los par�metros. 
	 *  Si no hay transici�n definida en AFD para esa combinaci�n (includo el caso de  s�mbolo  
	 *  no v�lido o estado no v�lido) entonces devuelve null. 
	 * @param estado Es un nombre de estado v�lido del aut�mata.
	 * @param simbolo De tipo char(s�mbolo representado como un car�cter)
	 */
	public String transicion(String estado, Character simbolo){
		

		// Si el alfabeto no contiene a s�mbolo entonces devuelve null
		// Sino accede a la tabla de transicion. Si la tabla de transici�n no tiene una entrada para el estado y el s�mbolo
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

