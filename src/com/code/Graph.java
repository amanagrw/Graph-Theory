package com.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph {

    private class Vertex{
        HashMap<String,Integer> nbrs = new HashMap<>();
    }

    HashMap<String,Vertex> vertices = new HashMap<>();

    public int numVertex(){
        return this.vertices.size();
    }

    public boolean containsVertex(String vname){
        return this.vertices.containsKey(vname);
    }

    public void addVertex(String vname){

        //Making an Empty Hashmap for isolated new vertex
        Vertex vtx = new Vertex();

        //Adding key and value to the macro Vertices Hashmap
        vertices.put(vname,vtx);
    }

    public void removeVertex(String vname){

        Vertex vtx = vertices.get(vname);

        //1.Getting list of all connected vertices to the one that has to be deleted
        ArrayList<String> keys = new ArrayList<>(vtx.nbrs.keySet());

        //2.Remove vname from all the vertices in the list
        for(String key : keys){
            Vertex v = vertices.get(key);
            v.nbrs.remove(vname);
        }

        //3.Finally remove the given vertex from the given Vertices Hashmap
        vertices.remove(vname);
    }

    //Learn Hashmaps well to be able to execute this method
    public int numEdges(){
        int count = 0;

        //returns an ArrayList of all keys from the vertices Hashmap
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());

        for(String str : keys){
            Vertex vtx = vertices.get(str);
            count += vtx.nbrs.size();
        }

        return count/2;
    }

    public boolean containsEdge(String vname1, String vname2){
        Vertex vtx1 = vertices.get(vname1);
        Vertex vtx2 = vertices.get(vname2);

        //Check if vertices not present in the graph or there doesn't exist the pairing vertex in 1st nbrs Hashmap
        if(vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vname2)){
            return false;
        }
        return true;
    }

    public void addEdge(String vname1, String vname2, int cost){
        Vertex vtx1 = vertices.get(vname1); //Let's say 2k
        Vertex vtx2 = vertices.get(vname2); //Let's say 4k

        //Check if vertices not present in the graph or there exists the pairing vertex already in 1st nbrs Hashmap
        if(vtx1 == null || vtx2 == null || vtx1.nbrs.containsKey(vname2)){
            return;
        }

        vtx1.nbrs.put(vname2,cost); //Put C with given cost in nbrs of 2k
        vtx2.nbrs.put(vname1,cost); //Put A with given cost in nbrs of 4k
    }

    public void removeEdge(String vname1, String vname2){
        Vertex vtx1 = vertices.get(vname1); //Let's say 2k
        Vertex vtx2 = vertices.get(vname2); //Let's say 4k

        //Check if vertices not present in the graph or there doesn't exist pairing vertex in 1st nbrs Hashmap
        if(vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vname2)){
            return;
        }
        vtx1.nbrs.remove(vname2); //Remove C from nbrs of 2k
        vtx2.nbrs.remove(vname1); //Remove A from nbrs of 4k

    }

    public boolean hasPath(String vname1,String vname2){
        return this.hasPath(vname1,vname2,new HashMap<>());
    }

    private boolean hasPath(String vname1, String vname2, HashMap<String, Boolean> processed){

        processed.put(vname1,true);

        //1.Check for direct edge
        if(containsEdge(vname1, vname2))
            return true;

        Vertex vtx = vertices.get(vname1);

        //2.Get neighbours
        ArrayList<String> neighbours = new ArrayList<>(vtx.nbrs.keySet());

        for(String nb : neighbours){
            //3.Check if node is already processed to avoid stack overflow
            if(!processed.containsKey(nb) && hasPath(nb,vname2,processed)){
                return true;
            }
        }
        return false;
    }

    private class Pair{
        //two properties to be added as one in the queue
        String vname;
        String psf; //path so far
    }

    public boolean bfs(String src, String dst){
        HashMap<String,Boolean> processed = new HashMap<>();
        LinkedList<Pair> queue = new LinkedList<>();

        //Create a new pair
        Pair sp = new Pair();
        sp.vname = src;
        sp.psf = src;

        //put new pair in queue
        queue.addLast(sp);

        while(!queue.isEmpty()){

            //remove pair
            Pair rp = queue.removeFirst();

            //so that if a vertex occurs multiple times then processing skips for second time onwards
            if(processed.containsKey(rp.vname)){
                continue;
            }

            //add it in processed Hashmap
            processed.put(rp.vname,true);

            //Check for direct edge
            if(containsEdge(rp.vname,dst)) {
                System.out.println(rp.psf + dst);
                return true;
            }

            //Get neighbours list
            Vertex rpvtx = vertices.get(rp.vname);
            ArrayList<String> keys = new ArrayList<>(rpvtx.nbrs.keySet());

            for(String key : keys){

                //Process only unprocessed neighbours
                if(!processed.containsKey(key)){

                    //make a new pair of neighbour and put in queue
                    Pair np = new Pair();
                    np.vname = key;
                    np.psf = rp.psf + key;

                    queue.addLast(np);
                }
            }

        }
        return false;
    }

    public void display(){

        System.out.println("-------------------------------------");
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());

        for(String key : keys){
            Vertex vtx = vertices.get(key);
            System.out.println(key + " : " + vtx.nbrs);
        }
        System.out.println("-------------------------------------");
    }

}
