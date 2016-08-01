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
package org.celyo.jdbconsole.db;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import org.celyo.jdbconsole.util.MessageKey;
import org.celyo.jdbconsole.util.Messages;

public class DriverLoader {

  private final FilenameFilter driverFilter = new FilenameFilter() {
    @Override
    public boolean accept(File dir, String name) {
      return name.endsWith(".jar");
    }
  };

  private URLClassLoader classloader;

  public DriverLoader(String driverPath) {
    File f = new File(driverPath);

    if (!f.exists()) {
      throw new IllegalArgumentException(Messages.getString(MessageKey.EXCEPTION_DRIVER_PATH_NOT_EXIST));
    }

    if (!f.isDirectory()) {
      throw new IllegalArgumentException(Messages.getString(MessageKey.EXCEPTION_DRIVER_PATH_NOT_DIR));
    }

    File[] drivers = f.listFiles(driverFilter);
    URL[] driverUrls = new URL[drivers.length];

    try {
      for (int i = 0; i < drivers.length; i++) {
        driverUrls[i] = drivers[i].toURI().toURL();
      }
    } catch (MalformedURLException ex) {
      throw new RuntimeException(Messages.getString(MessageKey.EXCEPTION_DRIVER_CANNOT_READ_DRIVER), ex);
    }

    classloader = new URLClassLoader(driverUrls);
  }

  public Driver loadDriver(String driverClassName) {
    Driver drv;
    try {
      drv = (Driver) Class.forName(driverClassName, true, classloader).newInstance();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
      throw new RuntimeException(Messages.getString(MessageKey.EXCEPTION_DRIVER_CANNOT_LOAD_DRIVER), ex);
    }

    return new DriverWrapper(drv);
  }
}
