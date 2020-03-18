package com.secondstudio.letsrun.uselessCode;

import com.secondstudio.letsrun.model.Player;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WebUtils {

    public static String web_url = "http://47.103.0.188/CardGame_war/gameServlet";

    public static int doTask(String code) {
        URL url = null;
        try {
            String tmp_web_url = web_url + "?code=" + code +"&account=" + Player.mAccount;
            url = new URL(tmp_web_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            BufferedReader buff = new BufferedReader(reader);
            StringBuffer sbuff = new StringBuffer();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            int r = Integer.valueOf(sbuff.toString());
            return r;
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            BufferedReader buff = new BufferedReader(reader);
            StringBuffer sbuff = new StringBuffer();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            int r = Integer.valueOf(sbuff.toString());
            return r;
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            StringBuffer sbuff = new StringBuffer();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            int r = Integer.valueOf(sbuff.toString());
            return r;
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            BufferedReader buff = new BufferedReader(reader);
            StringBuffer sbuff = new StringBuffer();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            int r = Integer.valueOf(sbuff.toString());
            return r;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 2;
    }

    public static String PLAY_INFO = "player_info";
    public static String getInfo(String code, String account) {
        URL url = null;
        try {
            account = URLEncoder.encode(account, "UTF-8");// 如有中文一定要加上，在接收方用相应字符转码即可
            String tmp_web_url = web_url + "?code=" + code + "&account=" + account;
            url = new URL(tmp_web_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            BufferedReader buff = new BufferedReader(reader);
            StringBuffer sbuff = new StringBuffer();
            String temp = null;
            while ((temp = buff.readLine()) != null) {
                sbuff.append(temp);
            }
            buff.close();
            reader.close();
            in.close();

            return sbuff.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //向服务器发送数据
    public static int doPost(JSONObject info) {
        try {
            URL url = new URL(web_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);     //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(info.toString().length()));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(info.toString().getBytes());

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                return 1;
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return 0;
        }
        return 0;
    }
}
