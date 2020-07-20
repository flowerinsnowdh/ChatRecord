package com.hotmail.flowerimsnow.bungee.chatrecord.manager;
import com.hotmail.flowerimsnow.bungee.chatrecord.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
public class ConfigManager {
    private static Configuration config;
    public static void saveDefaultConfig() {
        File dir = Main.getInstance().getDataFolder(); // 数据文件夹
        if (!dir.isDirectory()) { // 不存在配置文件夹
            dir.mkdirs(); // 创建配置文件夹
        }

        File file = new File(Main.getInstance().getDataFolder(),"config.yml"); // 配置文件
        if (!file.isFile()) { // 不存在配置文件
            try {
                file.createNewFile(); // 创建配置文件
                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file); // 获取配置文件yml
                config.set("sql.ip","127.0.0.1"); // SQL IP
                config.set("sql.port",3306); // SQL 端口
                config.set("sql.user","root"); // SQL 用户
                config.set("sql.password",""); // SQL 密码
                config.set("sql.schema","aerocraft"); // SQL 库
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file); // 保存默认
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Configuration getConfig() {
        return config;
    }
    public static void reloadConfig() {
        File file = new File(Main.getInstance().getDataFolder(),"config.yml");
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveConfig() {
        File file = new File(Main.getInstance().getDataFolder(),"config.yml");
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getIP() {
        return getConfig().getString("sql.ip");
    }
    public static int getPort() {
        return getConfig().getInt("sql.port");
    }
    public static String getUser() {
        return getConfig().getString("sql.user");
    }
    public static String getPassword() {
        return getConfig().getString("sql.password");
    }
    public static String getSchema() {
        return getConfig().getString("sql.schema");
    }
}
