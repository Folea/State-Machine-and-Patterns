

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**^
 * Clase para leer un AF desde un fichero .jff
 *
 */
public class LectorAFD_JFLAP {
	private Map<String, Map<String, String>> tablaTransicion;
	private Map<String, String> estados;
	private Set<String> estadosFinales;
	private String estadoInicial;
	private Set<String> alfabeto;
	
	/**
	 * Lee un fichero .jff
	 * @param nombreFichero Ruta y nombre del fichero .jff que contiene el AF que se va a leer.
	 */
	public void leeFichero(String nombreFichero) throws IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		this.estadosFinales = new HashSet<String>();
		this.estados = new HashMap<String, String>();
		this.tablaTransicion = new HashMap<String, Map<String,String>>();
		this.alfabeto = new HashSet<String>();
		
		try {
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document doc = builder.parse(new File(nombreFichero));
			
			// Lee raiz <structure>
			Element raiz = doc.getDocumentElement();

			// Lee tipo <type> y comprueba si es Automata finito 
			Node node = raiz.getElementsByTagName("type").item(0);
			if (!node.getTextContent().equals("fa"))
				throw new IOException("El fichero no es de tipo Autómata Finito");
			
			// Lee el automata
			Element automata = (Element) raiz.getElementsByTagName("automaton").item(0);
			// Lee los estados <state>
			NodeList nl = automata.getElementsByTagName("state");
			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				this.estados.put(e.getAttribute("id"), e.getAttribute("name"));
				this.tablaTransicion.put(e.getAttribute("name"), new HashMap<String, String>());
				
				// Lee si es final
				if (e.getElementsByTagName("final").getLength() > 0) 
					this.estadosFinales.add(e.getAttribute("name"));
				// Lee si es inicial
				if (e.getElementsByTagName("initial").getLength() > 0)
					this.estadoInicial = e.getAttribute("name");
			}
			
			// Lee las transiciones <transition>
			nl = automata.getElementsByTagName("transition");
			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				// Lee origen <from>
				Element from = (Element) e.getElementsByTagName("from").item(0);
				String origen = this.estados.get(from.getTextContent());
				
				// Lee destino <to>
				Element to = (Element) e.getElementsByTagName("to").item(0);
				String destino = this.estados.get(to.getTextContent());
				
				// Lee caracter entrada <read>
				Element read = (Element) e.getElementsByTagName("read").item(0);
				String cadena = read.getTextContent();
				this.alfabeto.add(cadena);

				this.tablaTransicion.get(origen).put(cadena, destino);
			}
			
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		} catch (SAXException e) {
			throw new IOException(e);
		}
	}
	
	
	public Set<String> getAlfabeto() {
		return this.alfabeto;
	}
	
	public Set<String> getEstadosFinales() {
		return this.estadosFinales;
	}
	
	public Map<String, Map<String, String>> getTablaTransicion() {
		return this.tablaTransicion;
	}
	
	public String getEstadoInicial() {
		return this.estadoInicial;
	}

	
	public static void main(String[] args) {
		LectorAFD_JFLAP lector = new LectorAFD_JFLAP();
		try {
			lector.leeFichero("proResuelto-termina-cd.jff");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
