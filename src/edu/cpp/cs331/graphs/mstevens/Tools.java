package edu.cpp.cs331.graphs.mstevens;

import edu.cpp.cs331.graphs.DirectedEdge;
import edu.cpp.cs331.graphs.Edge;
import edu.cpp.cs331.graphs.Graph;
import edu.cpp.cs331.graphs.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 3/11/17.
 */

/*
* This is a "kitchen sink" class to encapsulate various
* tools to be used in other areas of the program.
* Hence the name.
* */
public class Tools {

    /*
    * Here, we take a list of vertices and, using the
    * vertices' graph, find all the corresponding edges
    * and return those in the form of a List
    * */
    public static List<Edge> convertToEdgeList(Graph g, ArrayList<Vertex> vertices){
        List<Edge> list = new ArrayList();
        ArrayList<Edge> elist = (ArrayList) g.geteList();
        for(int i = 0; i < vertices.size() - 1; i++){
            for(Edge edge : elist){
                if(edge.getOne().equals(vertices.get(i)) && edge.getTwo().equals(vertices.get(i+1))){
                    list.add(edge);
                    break;
                }
            }
        }
        return list;
    }
}
