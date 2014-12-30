/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nutcake.visualsocial.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Martin
 */
class Nodes<K,V> implements Serializable{
    
    public Map<K,V> strength =new HashMap<>();
    public void addStrength(K key, V value){
        strength.put(key,value);
    }
}
public class RelationStrengthGraph<K,V> implements Serializable {
    /*这个类显示了用户之间关系强度
    改进了一下RelationGraph
    在一个HashMap对象<K,V>的V设置成另一个HashMap
    存储好友及其关系强度
    即<UserId,<Friendlist,RelationStrength>>*/
    private Map<K, Nodes<K,V>> nodes = new HashMap<>();
     public void addVertex(K key) {
        nodes.put(key, new Nodes<>());
    }
     public void addEdge(K n1, K n2,V v) {
        nodes.get(n1).addStrength(n1,v);
        nodes.get(n2).addStrength(n2,v);
    }
     public void addVertexWithEdge(K key, K old, V v) {
        addVertex(key);
        addEdge(key, old, v);
    }
     public boolean contain(K key) {
        return nodes.containsKey(key);
    }
    
}
