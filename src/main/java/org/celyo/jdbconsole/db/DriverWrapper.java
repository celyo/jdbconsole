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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class DriverWrapper implements Driver {

  private Driver orgDriver;
  
  public DriverWrapper(Driver orgDriver) {
    this.orgDriver = orgDriver;
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return orgDriver.connect(url, info);
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    return orgDriver.acceptsURL(url);
  }

  @Override
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
    return orgDriver.getPropertyInfo(url, info);
  }

  @Override
  public int getMajorVersion() {
    return orgDriver.getMajorVersion();
  }

  @Override
  public int getMinorVersion() {
    return orgDriver.getMinorVersion();
  }

  @Override
  public boolean jdbcCompliant() {
    return orgDriver.jdbcCompliant();
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return orgDriver.getParentLogger();
  }
  
}
