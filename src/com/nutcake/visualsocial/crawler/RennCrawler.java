package com.nutcake.visualsocial.crawler;

import com.nutcake.visualsocial.graph.RelationGraph;
import com.renren.api.AuthorizationException;
import com.renren.api.RennClient;
import com.renren.api.RennException;
import com.renren.api.service.User;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 人人网朋友关系抓取图
 * @since 11/26/14
 */
public class RennCrawler implements Crawler<Long, User> {
    private String m_apiKey;
    private String m_apiSecret;
    private RennClient m_client;
    private RelationGraph<Long, User> m_resultGraph;

    public RennCrawler(String apiKey, String apiSecret) {
        m_apiKey = apiKey;
        m_apiSecret = apiSecret;
    }

    @Override
    public void init() {
        m_client = new RennClient(m_apiKey, m_apiSecret);
        try {
            m_client.authorizeWithClientCredentials();
        } catch (AuthorizationException e) {
            System.out.println("Renren Client initialize failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void crawl(Long userId) {
        User source;
        try {
            source = m_client.getUserService().getUser(userId);

            m_resultGraph = new RelationGraph<>();
            m_resultGraph.addVertex(source.getId());

            int maxVertices = 1000;
            Queue<User> queue = new ArrayDeque<>();
            queue.add(source);
            int idx = 0;
            while (idx < maxVertices && !queue.isEmpty()) {
                User current = queue.poll();
                Thread.sleep(30000);

            }
        } catch (InterruptedException | RennException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RelationGraph<Long, User> graphResult() {
        return m_resultGraph;
    }
}
