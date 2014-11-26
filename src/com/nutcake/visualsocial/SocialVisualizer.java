package com.nutcake.visualsocial;

import com.renren.api.RennClient;
import com.renren.api.RennException;
import com.renren.api.service.User;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Dawei YANG
 * @since 11/25/14
 */
public class SocialVisualizer {
    public static void main(String[] args) throws RennException {

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

        // 测试用户
        long daweiId = 356795949L;
        User user = renn.getUserService().getUser(daweiId);
        System.out.println(user.getName() + "'s friend: (10)");

        //显示10个好友
        Integer[] friendsId = renn.getFriendService().listFriend(daweiId, 1000, 1);
        for (Integer aFriendsId : friendsId) {
            System.out.println(aFriendsId);
        }
        /*
        List<String> names = new ArrayList<>();
        for (Integer i : friendsId) {
            User friend = renn.getUserService().getUser(i.longValue());
            names.add(friend.getName());
        }
        names.forEach(System.out::println);
        */
    }
}
