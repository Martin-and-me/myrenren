package com.nutcake.visualsocial.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @since 11/26/14
 */

class Node<K> implements Serializable {
    private Set<K> neighbors = new HashSet<>();

    public void addNeighbor(K key) {
        neighbors.add(key);
    }
}

public class RelationGraph<K> implements Serializable {
    private Map<K, Node<K>> nodes = new HashMap<>();

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

    public boolean contain(K key) {
        return nodes.containsKey(key);
    }
}
