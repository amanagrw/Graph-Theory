package com.code;

import java.util.HashMap;

public class GraphClient {

    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");

        graph.addEdge("A","B",3);
        graph.addEdge("A","D",2);
        graph.addEdge("B","C",4);
        graph.addEdge("C","D",7);
        graph.addEdge("D","E",10);
        graph.addEdge("E","F",9);
        graph.addEdge("E","G",8);
        graph.addEdge("F","G",6);

        graph.display();

        graph.removeEdge("A","B");
        graph.display();

        graph.removeVertex("F");
        graph.display();

        System.out.println(graph.hasPath("A","G"));
        System.out.println(graph.bfs("A","G"));


    }
}
