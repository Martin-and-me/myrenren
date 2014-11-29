package com.nutcake.visualsocial.crawler;

import com.nutcake.visualsocial.graph.RelationGraph;
import com.renren.api.AuthorizationException;
import com.renren.api.RennClient;
import com.renren.api.RennException;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 人人网朋友关系抓取图
 * @since 11/26/14
 */
public class RennCrawler implements Crawler<Long> {
    private String m_apiKey;
    private String m_apiSecret;
    private RennClient m_client;
    private RelationGraph<Long> m_resultGraph;
    private int m_maxUserCount = 20000;

    public RennCrawler(String apiKey, String apiSecret, int maxUserCount) {
        m_apiKey = apiKey;
        m_apiSecret = apiSecret;
        m_maxUserCount = maxUserCount;
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
        try {
            m_resultGraph = new RelationGraph<>();
            m_resultGraph.addVertex(userId);

            int maxVertices = m_maxUserCount;
            Queue<Long> queue = new ArrayDeque<>();
            queue.add(userId);
            int idx = 0;
            while (idx < maxVertices && !queue.isEmpty()) {
                Long current = queue.poll();
                Thread.sleep(30000);
                Integer[] friendsId;
                try {
                    friendsId = m_client.getFriendService().listFriend(current, 2000, 1);
                } catch (RennException ignored) {
                    continue;
                }
                for (Integer i : friendsId) {
                    queue.add(i.longValue());
                    m_resultGraph.addVertexWithEdge(i.longValue(), current);
                    idx++;
                }
            }

            while (!queue.isEmpty()) {
                Long current = queue.poll();
                Thread.sleep(30000);
                Integer[] friendsId;
                try {
                    friendsId = m_client.getFriendService().listFriend(current, 2000, 1);
                } catch (RennException e) {
                    continue;
                }
                for (Integer i : friendsId) {
                    if (m_resultGraph.contain(i.longValue())) {
                        m_resultGraph.addVertexWithEdge(i.longValue(), current);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RelationGraph<Long> graphResult() {
        return m_resultGraph;
    }
}
