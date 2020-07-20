package com.hotmail.flowerimsnow.bungee.chatrecord.manager;

import com.hotmail.flowerimsnow.bungee.chatrecord.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager {
    private static Connection con;
    public static void connect() {
        loadDriver(); // 加载jdbc驱动
        String ip = ConfigManager.getIP(); // 获取IP
        int port = ConfigManager.getPort(); // 获取端口
        String user = ConfigManager.getUser(); // 获取用户名
        String password = ConfigManager.getPassword(); // 获取密码
        String schema = ConfigManager.getSchema();
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" +
                    port + "/" + schema + "?user=" + user + "&password=" + password + "&useUnicode=true&characterEncoding=utf8" +
                    "&autoReconnect=true&failOverReadOnly=false"); // 连接数据库
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }
    }
    public static void initTable() {
        update("create table if not exists chatrecord (\n" +
                "    id int primary key auto_increment,\n" +
                "    player varchar(255) not null,\n" +
                "    uuid varchar(255) not null,\n" +
                "    msg varchar(255) not null,\n" +
                "    ip varchar(255) not null,\n" +
                "    time datetime not null\n" +
                ")");
    }
    public static ResultSet query(String sql) {
        try {
            return con.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe(sql);
            Main.getInstance().getLogger().severe(e.toString());
            for (StackTraceElement stack : e.getStackTrace()) {
                Main.getInstance().getLogger().severe("\t位于 " + stack.toString());
            }
            return null;
        }
    }
    private static void update(String sql) {
        try {
            con.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe(sql);
            Main.getInstance().getLogger().severe(e.toString());
            for (StackTraceElement stack : e.getStackTrace()) {
                Main.getInstance().getLogger().severe("\t位于 " + stack.toString());
            }
        }
    }
    public static void record(ProxiedPlayer player,String msg) {
        update("insert into chatrecord (player,uuid,msg,ip,time) values ('" + player.getName() +
                "','" + player.getUniqueId().toString() + "','" + msg + "','" + player.getSocketAddress().toString() + "',now())");
    }
    public static void recordAsync(ProxiedPlayer player,String msg) {
        Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), () -> {
            record(player,msg);
        });
    }
}
