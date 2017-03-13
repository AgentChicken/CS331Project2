package edu.cpp.cs331.graphs.mstevens;

import edu.cpp.cs331.graphs.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matthew on 3/12/17.
 */
public class PrimsMST implements MinimumSpanningTree {
    @Override
    public Graph genMST(Graph G) {
        Vertex source = G.getvList().get(0);
        confirmed.add(new DijkstraRecord(source, 0, new Vertex(-1)));
        confirmedNodesOnly.add(source);

        /*
        * Similar to the Dijkstra code, but loops until we create a spanning
        * tree rather than a shortest path. Because of the different looping
        * conditions, I opted to copy the code rather than create a method.
        * If I were working with a language that supported higher-order
        * functions, I'd have modularized this part and passed the looping
        * condition in as a parameter.
        * */
        while(confirmedNodesOnly.size() < G.getvList().size()){
            stage.addAll(getDestinations(G, confirmed.get(confirmed.size() - 1)));
            for(DijkstraRecord stagedRecord : stage){
                if(confirmedNodesOnly.contains(stagedRecord.getVertex())){
                    //do nothing
                } else if(!(tentative.containsKey(stagedRecord.getVertex()))){
                    tentative.put(stagedRecord.getVertex(), stagedRecord);
                } else if(tentative.get(stagedRecord.getVertex()).getWeight() > stagedRecord.getWeight()){
                    tentative.remove(stagedRecord.getVertex());
                    tentative.put(stagedRecord.getVertex(), stagedRecord);
                }
            }

            Object[] tentativeArray = tentative.values().toArray();
            DijkstraRecord toAdd = (DijkstraRecord) tentativeArray[0];
            for(int i = 1; i < tentativeArray.length; i++){
                DijkstraRecord temp = (DijkstraRecord) tentativeArray[i];
                if(temp.getWeight() < toAdd.getWeight()){
                    toAdd = temp;
                }
            }
            confirmed.add(toAdd);
            confirmedNodesOnly.add(toAdd.getVertex());
            tentative.remove(toAdd.getVertex());
            stage.clear();
        }

        //System.out.println(confirmed);
        //System.out.println(confirmedNodesOnly);


        /*
        * This next section of code creates a new Graph and, based
        * on the nodes we have, adds vertices and edges until we've
        * constructed an MST.
        * */
        Graph result = new Graph();

        //add the vertices
        for(DijkstraRecord dijkstraRecord : confirmed){
            try {
                result.addVertex(dijkstraRecord.getVertex());
            } catch (Graph.VertexAlreadyExistsException e) {
                e.printStackTrace();
            }
        }

        //add the edges
        for(int i = 1; i < confirmed.size(); i++){
            try {
                result.addEdge(new UndirectedEdge(confirmed.get(i).getNextHop(), confirmed.get(i).getVertex(), confirmed.get(i).getWeight()));
            } catch (Graph.InconsistentEdgeException | Graph.DuplicateEdgeException e) {
                e.printStackTrace();
            }
        }

        //self-explanatory, I hope
        return result;
    }



    /*
    * As fields, create the canonical tentative and confirmed lists,
    * as well as a stage list used in determining whether or not changes
    * need to be made to the tentative list at each new visitation
    * and a confirmed list that only contains nodes, not full records
    * */
    private HashMap<Vertex, DijkstraRecord> tentative;
    private ArrayList<DijkstraRecord> confirmed;
    private ArrayList<DijkstraRecord> stage;
    private ArrayList<Vertex> confirmedNodesOnly;

    public PrimsMST() {
        this.tentative = new HashMap<>();
        this.confirmed = new ArrayList<>();
        this.stage = new ArrayList<>();
        this.confirmedNodesOnly = new ArrayList<>();
    }

    /*
    * This method, given a vertex, finds the nodes that the neighbor can visit
    * and returns an ArrayList of DijkstraRecords for processing. Different from
    * the one in the DijkstrasSP class as it just gets the weight from the current
    * node rather than from the root node
    * */
    public ArrayList<DijkstraRecord> getDestinations(Graph G, DijkstraRecord dijkstraRecord){
        Vertex departure = dijkstraRecord.getVertex();
        List elist = G.geteList();
        ArrayList destinations = new ArrayList();
        Vertex potential;
        for(int i = 0; i < elist.size(); i++){
            potential = ((UndirectedEdge) elist.get(i)).getOne();
            if(potential.equals(departure)){
                destinations.add(new DijkstraRecord(((UndirectedEdge) elist.get(i)).getTwo(), ((UndirectedEdge) elist.get(i)).getWeight(), departure));
            }
        }
        return destinations;
    }
}
