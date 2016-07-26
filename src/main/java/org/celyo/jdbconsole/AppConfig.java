/*
 * 
 * Copyright 2016 Celyo
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 */


package org.celyo.jdbconsole;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.celyo.jdbconsole.model.ConnectionInfo;
import org.celyo.jdbconsole.util.OrderedProperties;
import org.celyo.jdbconsole.util.StrUtils;

public class AppConfig {
  private static final String APP_NAME = "jdbconsole";
  private static final String APP_VERSION = "1.0";
  private static final String CONF_FILE_NAME = APP_NAME + ".cfg";

  private static Properties props = new OrderedProperties();
  private static List<ConnectionInfo> conns = new LinkedList<>();

  private AppConfig() {}

  public static String getAppName() {
    return APP_NAME;
  }

  public static String getAppVersion() {
    return APP_VERSION;
  }

  public static String getConfigDirName() {
    return System.getProperty("user.home") + System.getProperty("file.separator") + "."
        + getAppName().toLowerCase();
  }

  public static String getConfigFileName() {
    return CONF_FILE_NAME;
  }

  public static List<ConnectionInfo> getConnections() {
    return conns;
  }

  private static void loadConnections() {
    conns.clear();

    for (String key : props.stringPropertyNames()) {
      if (key.startsWith("connection.") && key.endsWith(".name")) {
        String connId = key.replace("connection.", "").replace(".name", "");
        conns.add(new ConnectionInfo(connId));
      }
    }

    for (ConnectionInfo conn : conns) {
      conn.setName(props.getProperty("connection." + conn.getId() + ".name"));
      conn.setUrl(props.getProperty("connection." + conn.getId() + ".url"));
      conn.setUser(props.getProperty("connection." + conn.getId() + ".user"));
      conn.setPassword(props.getProperty("connection." + conn.getId() + ".password"));
      conn.setDriver(props.getProperty("connection." + conn.getId() + ".driver"));
    }

  }

  private static void saveConnections() {
    Set<String> connKeys = new HashSet<>();
    for (String key : props.stringPropertyNames()) {
      if (key.startsWith("connection.")) {
        connKeys.add(key);
      }
    }
    for (String key : connKeys) {
      props.remove(key);
    }

    // save to properties
    for (ConnectionInfo conn : conns) {
      props.setProperty("connection." + conn.getId() + ".name", StrUtils.defaultString(conn.getName()));
      props.setProperty("connection." + conn.getId() + ".url", StrUtils.defaultString(conn.getUrl()));
      props.setProperty("connection." + conn.getId() + ".user", StrUtils.defaultString(conn.getUser()));
      props.setProperty("connection." + conn.getId() + ".password", StrUtils.defaultString(conn.getPassword()));
      props.setProperty("connection." + conn.getId() + ".driver", StrUtils.defaultString(conn.getDriver()));
    }
  }

  public static void load() throws IOException {
    props.clear();

    File cfgFile = new File(getConfigDirName() + File.separator + getConfigFileName());
    if (!cfgFile.exists()) {
      save(); // creates empty file
    } else {
      props.load(new FileReader(cfgFile));
    }

    loadConnections();
  }

  public static void save() throws IOException {
    saveConnections();

    File cfgDir = new File(getConfigDirName());
    if (!cfgDir.exists()) {
      cfgDir.mkdirs();
    }

    File cfgFile = new File(getConfigDirName() + File.separator + getConfigFileName());
    props.store(new FileWriter(cfgFile), null);
  }

  public static void print() {
    System.out.println("appName=" + getAppName());
    System.out.println("appVersion=" + getAppVersion());
    System.out.println("configDirName=" + getConfigDirName());
    System.out.println("configFileName=" + getConfigFileName());

    props.list(System.out);
  }

  public static String getProperty(String key) {
    return props.getProperty(key);
  }

  public static void setProperty(String key, String value) {
    props.setProperty(key, value);
  }
}
