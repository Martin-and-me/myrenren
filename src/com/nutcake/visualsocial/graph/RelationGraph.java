package com.nutcake.visualsocial.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @since 11/26/14
 */

class Node<K, T> {
    private Set<K> neighbors = new HashSet<>();
    private T data;

    public void addNeighbor(K key) {
        neighbors.add(key);
    }
}

public class RelationGraph<K, T> {
    private Map<K, Node<K, T>> nodes = new HashMap<>();

    public void addVertex(K key) {
        nodes.put(key, new Node<>());
    }

    public void addEdge(K n1, K n2) {
        nodes.get(n1).addNeighbor(n2);
        nodes.get(n2).addNeighbor(n1);
    }

    public void addVertexWithEdge(K key, K old) {
        addVertex(key);
        addEdge(key, old);
    }
}
