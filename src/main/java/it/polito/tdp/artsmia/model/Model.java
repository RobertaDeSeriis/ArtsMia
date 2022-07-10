package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	
	private Graph <ArtObject, DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao; 
	//Identity Map, mantiene la corrispondenza tra l'id dell'oggetto e l'oggetto stesso
	private Map <Integer, ArtObject> idMap; 	
	
			public Model() {
				dao= new ArtsmiaDAO(); //conviene crearlo qui			
				idMap= new HashMap <Integer, ArtObject>(); 
			}
			
			//il grafo viene ogni volta creato da 0, così siamo sicuri che sia pulito
			public void creaGrafo() {
				grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 	
			
			//AGGIUNGO VERTICI
			dao.listObject(idMap);
			Graphs.addAllVertices(this.grafo, idMap.values()); 
			
			
			//AGGIUNGO VERTICI
			
			//PER PIù DI 30 VERTICI IL METODO 1 NON CONVIENE
		/*	for (ArtObject a1: this.grafo.vertexSet()) {
				for(ArtObject a2: this.grafo.vertexSet()) {
					//se i 2 vertici sono diversi e se non c'è già un arco
					if(!a1.equals(a2) && !this.grafo.containsEdge(a1, a2)){
						//chiedo al db se devo collegare a1 e a2
						int peso= dao.getPeso(a1,a2);
								//se il peso è maggiore di 0
								if (peso>0) {
									Graphs.addEdgeWithVertices(this.grafo, a1, a2, peso);
								}
							}		
						}
					}
			*/
			
			//APPROCCIO 2
			for (CoppiaId c: this.dao.getCopppiaId(idMap)) {
				Graphs.addEdgeWithVertices(this.grafo, c.getA1(),c.getA2(), c.getPeso()); 
			}
			
			
			/*System.out.println("Grafo creato!");
			System.out.println("Vertici:"+this.grafo.vertexSet().size());
			System.out.println("Archi:"+this.grafo.edgeSet().size());
			*/
			}
			
		public int nVertici() {
			return this.grafo.vertexSet().size(); 
		}
		
		public int nArchi() {
			return this.grafo.edgeSet().size(); 
		}

		public int getComponenteConnessa(ArtObject vertice) {
			Set<ArtObject> visitati= new HashSet<>(); 
			DepthFirstIterator<ArtObject, DefaultWeightedEdge> it= new DepthFirstIterator<ArtObject, DefaultWeightedEdge> (this.grafo, vertice); 
			
			while(it.hasNext()) {
				visitati.add(it.next());
			}
			
			//connectoringSector.
			return visitati.size();
		}
		
		public ArtObject getObject(int objectId) {
			return idMap.get(objectId);
		}
	}