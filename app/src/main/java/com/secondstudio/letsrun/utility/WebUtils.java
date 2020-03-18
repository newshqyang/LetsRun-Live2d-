package com.secondstudio.letsrun.utility;

import com.secondstudio.letsrun.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WebUtils {

    private static String web_url = "http://47.103.0.188/CardGame_war/gameServlet";

    public static int doTask(String code) {
        URL url = null;
        try {
            String tmp_web_url = web_url + "?code=" + code +"&account=" + Player.mAccount;
            url = new URL(tmp_web_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(reader);
            StringBuilder sbuff = new StringBuilder();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            return Integer.valueOf(sbuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 2;
    }

    /**
     * 无参，只有code，获取信息
     */
    public static int getInfoNoPara(String code) {
        URL url = null;
        try {
            String tmp_web_url = web_url + "?code=" + code;
            url = new URL(tmp_web_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(reader);
            StringBuilder sbuff = new StringBuilder();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            return Integer.valueOf(sbuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 2;
    }

    /**
     * 注册相关
     */
    public static int register(String account, String pwd, String name) {
        URL url = null;
        try {
            account = URLEncoder.encode(account, "UTF-8");// 如有中文一定要加上，在接收方用相应字符转码即可
            pwd = URLEncoder.encode(pwd, "UTF-8");
            String tmp_web_url = web_url + "?code=register&account=" + account + "&pwd=" + pwd + "&name=" + name;
            url = new URL(tmp_web_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(reader);
            StringBuilder sbuff = new StringBuilder();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            return Integer.valueOf(sbuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 2;
    }


    /**
     * 登陆方法
     * @param account
     * @param pwd
     */
    public static int login(String account, String pwd) {
        URL url = null;
        try {
            account = URLEncoder.encode(account, "UTF-8");// 如有中文一定要加上，在接收方用相应字符转码即可
            pwd = URLEncoder.encode(pwd, "UTF-8");
            String tmp_web_url = web_url + "?code=login&account=" + account + "&pwd=" + pwd;
            url = new URL(tmp_web_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(reader);
            StringBuilder sbuff = new StringBuilder();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            return Integer.valueOf(sbuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 2;
    }

    public static String getInfo(String code, String account) {
        URL url = null;
        try {
            // 如有中文一定要加上，在接收方用相应字符转码即可
            account = URLEncoder.encode(account, "UTF-8");
            String tmp_web_url = web_url + "?code=" + code + "&account=" + account;
            url = new URL(tmp_web_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader buff = new BufferedReader(reader);
            StringBuilder sbuff = new StringBuilder();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            return sbuff.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
