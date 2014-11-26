package com.nutcake.visualsocial.crawler;

import com.nutcake.visualsocial.graph.RelationGraph;

/**
 * @author Dawei YANG
 * @since 11/26/14
 */
public interface Crawler<K> {

    /**
     * 实行初始配置
     */
    void init();

    /**
     * 实行抓取
     */
    void crawl(K key);

    /**
     * 得到抓取结果
     */
    RelationGraph<K> graphResult();
}
