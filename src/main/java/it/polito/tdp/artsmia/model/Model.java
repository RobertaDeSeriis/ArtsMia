package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	ArtsmiaDAO dao; 
	Graph<ArtObject, DefaultWeightedEdge> grafo;
	List<ArtObject> oggetti; 
	Map <Integer, ArtObject> idMap; 
	
	public Model() {
		super();
		this.dao= new ArtsmiaDAO(); 
	} 
	
	public String creaGrafo() {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		idMap= new HashMap<>();
		this.oggetti= dao.getVertici(idMap);
		Graphs.addAllVertices(this.grafo, this.oggetti);
		
		for (Adiacenza a: dao.getArchi(idMap)) {
			Graphs.addEdge(this.grafo, a.getA1(),a.getA2(), a.getPeso());
		}
		
		return "Grafo creato con " +this.grafo.vertexSet().size()+ " vertici e "
		+ this.grafo.edgeSet().size()+ " archi";
		
	}

	public ArtObject getOgg(int id) {
		return idMap.get(id); //restituisce l'oggetto corrispondente dato l'id
	}
	
	public List<ArtObject> getAdiacenti(ArtObject a) { 
		//COMPONENTE CONNESSA
		ConnectivityInspector<ArtObject, DefaultWeightedEdge> ci = new ConnectivityInspector<>(grafo);
		List<ArtObject> oggettiConn = new ArrayList<>(ci.connectedSetOf(a)); //torna la componente connessa a partire da a
		//actors.remove(actorsConnessi.get(0));
		return oggettiConn;
	}
	
	
	}