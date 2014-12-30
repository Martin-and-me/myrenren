/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*package com.nutcake.visualsocial.grouping;

import com.nutcake.visualsocial.graph.RelationGraph;
import java.util.*;
*/
/**
 *
 * @author Martin
 */
/*public class Grouper {
   private RelationGraph<Long> graph;
   private Long[] UserID=new Long[11];//获得的所有好友ID列表
   private Long[] sub_UserID=new Long[11];//计算时用的辅助ID列表
   private float max_meanstrength=0;//最大平均关系强度
   private Long[] maxstrength_UserID;//取得最大关系强度时的那些ID
   //构造函数，建立graph和ID列表 
   public Grouper(RelationGraph<Long> graph){
        this.graph=graph;
        Set users=graph.nodes.keySet();
        Iterator it=users.iterator();
        int i=0;
        while(it.hasNext()){
            UserID[i]=(Long)it.next();
            i++;
        }
    }
   //计算n个ID的平均关系强度，如果两个ID之间没有共同好友，那么他们之间的强度为0
    public Float meanStrength(Long[] ID){
        int num_edge=0;
        float gross_strength=0;
        float meanstrength;
        for(int i=0;i<ID.length;i++){
            if(ID[i]==null){}
            else{
            for(int j=i+1;j<ID.length;j++){
            if(ID[j]==null){}      //排除列表里原先那些空的元素
            else{
            if(graph.nodes.containsKey(ID[i])&&graph.nodes.get(ID[i]).contains(ID[j])){
                gross_strength=gross_strength+graph.nodes.get(ID[i]).getStrength(ID[j]);
            }
            else{}
                num_edge++;
            }
        }
            }
    }
        meanstrength=gross_strength/num_edge;
        return meanstrength;
        
}
    //调用时i为0，即for循环由1开始，n为递归次数，即遍历n个元素的组合
    public void strongestGroup(int i,int n){
        if(n>=0){
            for(int k=i+1;k<=UserID.length;k++){
                sub_UserID[k-1]=UserID[k-1];//问题出在sub_UserID怎么改。。。。
                float sub_strength=meanStrength(sub_UserID);
                if(sub_strength>=max_meanstrength){
                    max_meanstrength=sub_strength;
                    maxstrength_UserID=sub_UserID;
                } 
                else{}
                strongestGroup(k,n-1);//因为遍历次数跟总共的数量有关，所以就用递推了
                //sub_UserID[k-1]=null;//但是这里有问题。不知道怎么改
            }
        }
        else{}
    }
    public Long[] getmaxstrength_UserID(){
        return maxstrength_UserID;
    }
    public Float getmax_meanstrength(){
        return max_meanstrength;
    }
}
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nutcake.visualsocial.grouping;

import com.nutcake.visualsocial.graph.Node;
import com.nutcake.visualsocial.graph.RelationGraph;
import com.nutcake.visualsocial.misc.CloneUtils;
import com.nutcake.visualsocial.Reader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


/**
 * @author Martin
 */
/*public class Grouper {
    private RelationGraph<Long> graph;

    public Grouper(RelationGraph<Long> graph) {
        this.graph = graph;
    }

    // 找出graph中包含3个结点的互相认识的3个人作为增长的“核心”，进而找出团
    public List<Long> clique3(RelationGraph<Long> graph) {
        List<Long> clique=new ArrayList();
        for (Long userId : graph.nodes.keySet()) {
            Node<Long, Float> node = graph.nodes.get(userId);

            // 遍历node的所有朋友，看他们是否有两个互相认识
            for (Long friend1 : node.getNeighbor().keySet()) {
                for (Long friend2 : node.getNeighbor().keySet()) {
                    if (friend1 <= friend2)
                        continue; //避免重复查找

                    if (graph.nodes.get(friend1).getNeighbor().containsKey(friend2)) {
                        // 核心3人：userId, friend1, friend2
                   
                        Queue <Long>temp=new ArrayDeque();
                        Set<Long> neighbors = new HashSet<>();
                        neighbors.addAll(graph.nodes.get(friend1).getNeighbor().keySet());
                        neighbors.addAll(graph.nodes.get(userId).getNeighbor().keySet());
                        neighbors.addAll(graph.nodes.get(friend2).getNeighbor().keySet());
                        temp.addAll(neighbors);
                        while(!temp.isEmpty()){
                            Long current=temp.poll();
                           if(graph.nodes.get(friend1).getNeighbor().containsKey(current)&&
                               graph.nodes.get(friend2).getNeighbor().containsKey(current)&&
                               graph.nodes.get(userId).getNeighbor().containsKey(current)){                            
                                temp.addAll(graph.nodes.get(current).getNeighbor().keySet());
                            }
                        }
                       if(temp.size()>clique.size()){
                           clique=new ArrayList(temp);
                       }
                       else continue;

                        // TODO: 找出neighbors里面与userId, friend1, friend2都认识的人friend，再把friend的邻居也加进去，如此一
                        // TODO: 直找到最大完全子图（或者叫团）并返回
                    }
                }
            }
        }

        return clique;
    }

    public List<List<Long>> strongGroup() {
        // 找最大密度子图的算法需要用到网络流知识，直接暴力算太慢，而且据他说会出现找到的子图较小的情况。
        // 他给的第二个算法：
        List<List<Long>> result=new ArrayList();
        boolean doable=true;
        while(doable){
            List<Long> clique=clique3(graph);
            List<Long> stronggroup=new ArrayList(clique);
            Iterator <Long>it=clique.iterator();
            while(it.hasNext()){
                Long current=it.next();
                Set currentsfriends=graph.nodes.get(current).getNeighbor().keySet();
                Iterator<Long> thisit=currentsfriends.iterator();
                while(thisit.hasNext()){
                    Long currentmutualfriends=thisit.next();
                    if(clique.contains(currentmutualfriends)){
                        continue;
                    }
                    else {
                        if(graph.nodes.get(current).getNeighbor().get(currentmutualfriends)>0.2){
                            stronggroup.add(currentmutualfriends);
                        }
                    }
                }
            }
            result.add(stronggroup);
            Iterator<Long> removeit=clique.iterator();
            while(removeit.hasNext()){
            graph.nodes.remove(removeit.next());
            }
            if (clique.isEmpty()){
                doable=false;
            }
        }
      
        // TODO: 先找到一个最大子图G，然后把子图的结点的邻居们与该子图连接的边强度一一进行测试
        // TODO: 如果大于一个阈值，就加入到该图中，一直到加入不了为止，最终得到图G'即为所求的一个集合
        // TODO: 阈值调整是比较灵活的，比如直接连接该子图的阈值可以低一点，二层的可以高一点等等

        // TODO: 然后把G0中的G去掉（不是去掉G'），再重复以上过程。
        // TODO: 这个算法最后可能存在有些结点不属于某集合，或者属于多个集合。
    return result;
    }
    
}
*/

public class Grouper {
    private RelationGraph<Long> graph;
    Reader reader=new Reader();

    public Grouper(RelationGraph<Long> graph) {
        this.graph = graph;
    }

    // 找出graph中包含3个结点的互相认识的3个人作为增长的“核心”，进而找出所有团
    public Set<Set<Long>> clique3(RelationGraph<Long> graph) {
        // 拷贝graph
        graph = CloneUtils.clone(graph);

        Set<Set<Long>> cliques = new HashSet<>();

        while (true) {
            Set<Long> clique = new HashSet<>();
            Set<Long> neighbors = new HashSet<>();
            boolean found = false;
            for (Long userId : graph.nodes.keySet()) {
                Node<Long, Float> node = graph.nodes.get(userId);

                // 遍历node的所有朋友，看他们是否有两个互相认识
                for (Long friend1 : node.getNeighbor().keySet()) {
                    for (Long friend2 : node.getNeighbor().keySet()) {
                        if (friend1 <= friend2) {
                            continue; //避免重复查找
                        }

                        if (graph.nodes.get(friend1).getNeighbor().containsKey(friend2)) {
                            // 核心3人：userId, friend1, friend2

                            clique.add(userId);
                            clique.add(friend1);
                            clique.add(friend2);

                            neighbors.addAll(graph.nodes.get(friend1).getNeighbor().keySet());
                            neighbors.addAll(graph.nodes.get(userId).getNeighbor().keySet());
                            neighbors.addAll(graph.nodes.get(friend2).getNeighbor().keySet());
                            neighbors.removeAll(clique);

                            found = true;

                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }

            if (!found) {
                break;
            }

            Set<Long> visited = new HashSet<>();
            while (!neighbors.isEmpty()) {
                Long current = neighbors.iterator().next();
                visited.add(current);

                // 如果current与clique里面所有人都认识，则加入clique，否则不加入。
                Set<Long> temp = graph.nodes.get(current).getNeighbor().keySet();
                System.out.println(temp.size());
                boolean allKnow = temp.containsAll(clique);

                if (allKnow) {
                    clique.add(current);
                    neighbors.addAll(graph.nodes.get(current).getNeighbor().keySet());
                    neighbors.removeAll(clique);
                }
                neighbors.removeAll(visited);
            }
            cliques.add(clique);
            graph.removeVertices(clique);
        }

        return cliques;
    }

    public Set<Set<Long>> strongGroup(RelationGraph<Long> graph,Set<Set<Long>> cliques) {
        // 找最大密度子图的算法需要用到网络流知识，直接暴力算太慢，而且据他说会出现找到的子图较小的情况。
        // 他给的第二个算法：
        Set<Set<Long>> stronggroup=cliques;
        
        Iterator<Set<Long>> cliquesit=cliques.iterator();
        //对团遍历
        while(cliquesit.hasNext()){
           Set<Long> currentclique=cliquesit.next();
           Set<Long> currentneighbors=new HashSet();
           Iterator<Long> currentit=currentclique.iterator();
           //获得第i个团的邻居
           while(currentit.hasNext()){
               currentneighbors.addAll(graph.nodes.get(currentit.next()).getNeighbor().keySet());
           }
           currentneighbors.removeAll(currentclique);
           //用于储存新加入成员
           Set<Long> group=new HashSet();
         
           Iterator<Long> tester=currentneighbors.iterator();
           while(tester.hasNext()){
               Float s1=0F;
               Float s2=0F;
               Long current=tester.next();
               //对当前的邻居计算s1和s2
               Iterator<Long> thiscliqueit=currentclique.iterator();
               while(thiscliqueit.hasNext()){
                   Long currenttested=thiscliqueit.next();
                   if(graph.nodes.get(currenttested).getNeighbor().containsKey(current)){
                       s1=s1+graph.nodes.get(currenttested).getNeighbor().get(current);
                       s2=s2+graph.nodes.get(currenttested).getNeighbor().get(current);
                   }
                   else{
                       s2=s2+reader.getStrength(graph,currenttested,current);
                   }
               }
               if(s1/s2>0.5){
                   group.add(current);
               }
           }
           stronggroup.iterator().next().addAll(group);
               
        }


        // TODO: 先找到一个最大子图G，然后把子图的结点的邻居们与该子图连接的边强度一一进行测试
        // TODO: 如果大于一个阈值，就加入到该图中，一直到加入不了为止，最终得到图G'即为所求的一个集合
        // TODO: 阈值调整是比较灵活的，比如直接连接该子图的阈值可以低一点，二层的可以高一点等等

        // TODO: 然后把G0中的G去掉（不是去掉G'），再重复以上过程。
        // TODO: 这个算法最后可能存在有些结点不属于某集合，或者属于多个集合。
        return stronggroup;
    }

}
