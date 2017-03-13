package edu.cpp.cs331.graphs.mstevens;

import edu.cpp.cs331.graphs.Vertex;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by matthew on 3/12/17.
 */

/*
* Acts as a container for disjoint sets.
* Can you think of a better class name? I can't.
* */
public class SetThingy {
    public ArrayList<HashSet> getSets() {
        return sets;
    }

    //container for the HashSets
    ArrayList<HashSet> sets;

    //creates a new HashSet, adds it to sets, and returns the set
    public HashSet createSet(Vertex v){
        HashSet<Vertex> toAdd = new HashSet<>();
        toAdd.add(v);
        sets.add(toAdd);
        return toAdd;
    }

    //given an element, finds and returns the set that contains it
    public HashSet findSet(Vertex v){
        for(HashSet set : sets){
            if(set.contains(v)){
                return set;
            }
        }
        return null;
    }

    /*
    * Given two vertices, this function will find the respective
    * containing sets and unite them
    * */
    public void unite(Vertex v, Vertex u){
        HashSet<Vertex> toAdd = new HashSet<>();
        toAdd.addAll(findSet(v));
        toAdd.addAll(findSet(u));
        sets.remove(findSet(v));
        sets.remove(findSet(u));
        sets.add(toAdd);
    }

    public SetThingy(){
        sets = new ArrayList<>();
    }
}
