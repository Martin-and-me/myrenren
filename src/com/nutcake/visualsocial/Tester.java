/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nutcake.visualsocial;

import com.nutcake.visualsocial.graph.RelationGraph;
import com.nutcake.visualsocial.grouping.Grouper;
import com.renren.api.RennClient;
import com.renren.api.RennException;
import com.renren.api.service.User;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Martin
 */
public class Tester {
    public static void main(String[] args) throws RennException {

        Reader reader = new Reader();

        reader.getGraph();
        reader.getStrengthGraph();
        RelationGraph<Long> graph = reader.getRelationGraph();
        Grouper grouper = new Grouper(graph);
        System.out.println(grouper.clique3(graph));
        

        String apiKey, apiSecret;

        // 读取应用配置信息
        try {
            Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream("conf/secret.txt")));
            apiKey = scanner.nextLine();
            apiSecret = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // 初始化应用接口
        RennClient renn = new RennClient(apiKey, apiSecret);
        renn.authorizeWithClientCredentials();
        Set<Set<Long>> setLong = grouper.clique3(graph);
        Set<Set<Long>> setLongs=grouper.strongGroup(graph, setLong);
        for (Set<Long> longs : setLongs) {
            User[] users = renn.getUserService().batchUser(longs.toArray(new Long[longs.size()]));

            for (User user : users) {
                System.out.print(user.getName() + " ");
            }
            System.out.println();
        }
    }
}
