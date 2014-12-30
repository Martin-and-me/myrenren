package com.nutcake.visualsocial.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

/**
 * @since 11/26/14
 */

/**
 *
 * @param <K>
 * @since 11/26/14
 */
public class RelationGraph<K> implements Serializable {
    final static long serialVersionUID=-1077332291049239543L;
    public Map<K, Node<K,Float>> nodes = new HashMap<>();

    public void addVertex(K key) {
        nodes.put(key, new Node<>());
    }

    public void addEdge(K n1, K n2) {
        nodes.get(n1).addNeighbor(n2);
        nodes.get(n2).addNeighbor(n1);
    }
    
    public void setStrength(K n1,K n2,Float s){
        nodes.get(n1).addStrength(n2, s);
        nodes.get(n2).addStrength(n1, s);
    }

    public void addVertexWithEdge(K key, K old) {
        addVertex(key);
        addEdge(key, old);
    }
    public void removeVertices(Collection<K> vertices) {
        nodes.keySet().removeAll(vertices);
        for (Node<K, Float> node : nodes.values()) {
            node.neighbors.keySet().removeAll(vertices);
        }
    }


    public boolean contain(K key) {
        return nodes.containsKey(key);
    }
}
