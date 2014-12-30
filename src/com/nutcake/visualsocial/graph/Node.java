/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nutcake.visualsocial.graph;

import java.io.Serializable;
import java.util.*;
import java.util.Set;

/**
 *
 * @author Martin
 */
public class Node<K,Float> implements Serializable {
    final static long serialVersionUID=7003349727122592352L;
    public Map<K,Float> neighbors = new HashMap<>();

    public void addNeighbor(K key) {
        neighbors.put(key, null);
    }
    public void addStrength(K key, Float s){
        neighbors.put(key,s);
    }
    
    public Map<K,Float> getNeighbor(){
        return neighbors;
    }
    
    public Float getStrength(K key){
        return neighbors.get(key);
    }
    public boolean contains(K key){
        return neighbors.containsKey(key);
    }
}