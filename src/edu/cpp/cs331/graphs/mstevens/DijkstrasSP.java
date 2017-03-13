package edu.cpp.cs331.graphs.mstevens;

import edu.cpp.cs331.graphs.*;

import java.util.*;

/**
 * Created by matthew on 3/9/17.
 */

public class DijkstrasSP implements ShortestPath{
    @Override
    public List<Edge> genShortestPath(Graph G, Vertex source, Vertex goal) {
        confirmed.add(new DijkstraRecord(source, 0, new Vertex(-1)));
        confirmedNodesOnly.add(source);

        /*
        * Loops until our target node is in our confirmed list (in which case
        * we're done. Similar to the Prim's code, but I opted not to modularize
        * this part - the different looping condition makes it more trouble
        * than it's worth. Had I been working with a language that supported
        * higher-order functions, I'd have made a method and passed the looping
        * condition as a parameter
        * */
        while(!confirmedNodesOnly.contains(goal)){
            /*
            * This block of code is responsible for visiting the new nodes and
            * adjusting the tentative list accordingly
            * */
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

            /*
            * This block adds the element from our tentative list with the
            * smallest weight to our confirmed list
            * */
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
        //System.out.println(confirmedNodesOnly.contains(goal));
        //System.out.println(traceback(confirmed));
        //System.out.println(Tools.convertToEdgeList(G, traceback(confirmed)));

        /*
        * Now that we've found our vertices, we trace a route back to the source
        * and return the corresponding edges
        * */
        return Tools.convertToEdgeList(G, traceback(confirmed));
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

    public DijkstrasSP() {
        this.tentative = new HashMap<>();
        this.confirmed = new ArrayList<>();
        this.stage = new ArrayList<>();
        this.confirmedNodesOnly = new ArrayList<>();
    }

    /*
    * This method is called after the main part of Dijkstra's is complete
    * Essentially, it starts at the final DijstraRecord and, using nextHop,
    * finds the way back to the source and stores its path in a list.
    * It returns the list after reversing it (so we have the path from the
    * source to the destination)
    * */
    public ArrayList<Vertex> traceback(ArrayList<DijkstraRecord> dijkstraRecords){
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(dijkstraRecords.get(dijkstraRecords.size() - 1).getVertex());
        Vertex next = dijkstraRecords.get(dijkstraRecords.size() - 1).getNextHop();
        while(next.getId() != -1){
            for(DijkstraRecord record : dijkstraRecords){
                if(record.getVertex() == next){
                    result.add(record.getVertex());
                    next = record.getNextHop();
                    break;
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    /*
   * This method, given a vertex, finds the nodes that the neighbor can visit
   * and returns an ArrayList of DijkstraRecords for processing
   * */
    public ArrayList<DijkstraRecord> getDestinations(Graph G, DijkstraRecord dijkstraRecord){
        int cummulativeWeight = dijkstraRecord.getWeight();
        Vertex departure = dijkstraRecord.getVertex();
        List elist = G.geteList();
        ArrayList destinations = new ArrayList();
        Vertex potential;
        for(int i = 0; i < elist.size(); i++){
            potential = ((DirectedEdge) elist.get(i)).getOne();
            if(potential.equals(departure)){
                destinations.add(new DijkstraRecord(((DirectedEdge) elist.get(i)).getTwo(), cummulativeWeight + ((DirectedEdge) elist.get(i)).getWeight(), departure));
            }
        }
        return destinations;
    }
}
