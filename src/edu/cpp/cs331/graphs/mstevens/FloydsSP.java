package edu.cpp.cs331.graphs.mstevens;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.cpp.cs331.graphs.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by matthew on 3/11/17.
 */
public class FloydsSP implements ShortestPath {
    @Override
    public List<Edge> genShortestPath(Graph G, Vertex source, Vertex goal) {
        //Calling the getters is unwieldy, so I just went ahead and defined these
        List<Vertex> vertices = G.getvList();
        List<Edge> edges = G.geteList();

        /*
        * Here, declare an adjacency matrix called distances
        * as well as a matrix called next which will later
        * tell us how to calculate the actual shortest paths.
        * */
        int[][] distances = new int[vertices.size()][vertices.size()];
        Vertex[][] next = new Vertex[vertices.size()][vertices.size()];

        //initialize distances to "infinity"
        for(int i = 0; i < vertices.size(); i++){
            for(int j = 0; j < vertices.size(); j++){
                distances[i][j] = Integer.MAX_VALUE;
            }
        }

        //initialize next to null
        for(int i = 0; i < vertices.size(); i++){
            for(int j = 0; j < vertices.size(); j++){
                next[i][j] = null;
            }
        }

        /*
        * Here, we actually populate the adjacency matrix and modify next
        * such that adjacent vertices know to go straight to each other
        * unless otherwise specified by the next block
        * */
        for(Edge edge : edges){
            distances[edge.getOne().getId()][edge.getTwo().getId()] = edge.getWeight();
            next[edge.getOne().getId()][edge.getTwo().getId()] = edge.getTwo();
        }

        /*
        * Here, we do the actual algorithm, testing every path between nodes
        * by inserting another node to see if it makes things any faster.
        * If inserting something does make it faster, then we modify the
        * next matrix accordingly
        * */
        for(int k = 0; k < vertices.size(); k++){
            for(int i = 0; i < vertices.size(); i++){
                for(int j = 0; j < vertices.size(); j++){
                    if(distances[i][j] > distances[i][k] + distances[k][j]){
                        distances[i][j] = distances[i][k] + distances[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        /*
        * Here, we simply trace the next matrix for instructions
        * on how to get from one node to another. We add all the
        * necessary nodes to an ArrayList
        * */
        ArrayList<Vertex> path = new ArrayList<>();

        if(next[source.getId()][goal.getId()] == null)
        {
            return new ArrayList<Edge>();
        }

        path.add(source);

        while(!source.equals(goal)){
            source = next[source.getId()][goal.getId()];
            path.add(source);
        }

        //Finally, we convert the ArrayList to a list of Edges and return
        return Tools.convertToEdgeList(G, path);
    }
}
