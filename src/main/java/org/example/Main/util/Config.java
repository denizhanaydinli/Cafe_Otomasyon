package org.example.Main.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private String database;
    private String filePath = "config/config";
    private String link;
    Properties prop = new Properties();

    public Config() {
    }

    public void addConfigFile() {
        try {
            this.prop.setProperty("link", this.getLink());
            this.prop.setProperty("database", this.getDatabase());
            this.prop.store(new FileOutputStream(this.filePath), (String)null);
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Config c = new Config();
        c.setLink("org.sqlite.JDBC");
        c.setDatabase("jdbc:sqlite:DB\\satis.sqlite");
        c.addConfigFile();
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
