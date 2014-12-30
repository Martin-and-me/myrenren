/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nutcake.visualsocial;

import com.nutcake.visualsocial.graph.Node;
import com.nutcake.visualsocial.graph.RelationGraph;
import com.nutcake.visualsocial.graph.RelationStrengthGraph;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
/**
 *
 * @author Martin
 */
public class Reader {
    private RelationGraph<Long> graph;
  
    private Set<Long>UsersToBeTested=new HashSet();
    
    public void getGraph(){
        RelationGraph<Long> f_graph=new RelationGraph();
        try{FileInputStream fs = new FileInputStream("test.txt");
        ObjectInputStream os = new ObjectInputStream(fs);
        f_graph=(RelationGraph<Long>)os.readObject();
        os.close();
        }catch(IOException e){
             e.printStackTrace();
             
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        this.graph=f_graph;
    }
    //两个集合共同好友数
    public int MutualFriends(Set<Long> a,Set<Long> b){
        int i=0;
        Iterator it_a=a.iterator();
        while(it_a.hasNext()){
            if(b.contains(it_a.next())){
                i++;
            }
        }
        return i;
    }
    //两个集合关系强度，用共同好友数平方除以各自好友数
    public Float getStrength(RelationGraph<Long> graph,Long K1,Long K2){
        int f_1=graph.nodes.get(K1).neighbors.size();
        int f_2=graph.nodes.get(K2).neighbors.size();
        int f_3=MutualFriends(graph.nodes.get(K1).neighbors.keySet(),graph.nodes.get(K2).neighbors.keySet());
        return (float)f_3*f_3/(f_1*f_2);
    }
    //把强度加进去
    
    public void getStrengthGraph(){
        UsersToBeTested=graph.nodes.keySet();
        Iterator it = UsersToBeTested.iterator();
        while(it.hasNext()){
            Long current=(Long)it.next();
            
            Set currentfriends=graph.nodes.get(current).neighbors.keySet();
            Iterator current_it=currentfriends.iterator();
            while(current_it.hasNext()){
                Long f_current=(Long)current_it.next();
                Float v=getStrength(graph,current,f_current);
                graph.setStrength(current, f_current, v);
            }
            }
        
        }
    public RelationGraph<Long> getRelationGraph(){
        return graph;
    }
        
    }
            
    

