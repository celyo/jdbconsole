package org.celyo.jdbconsole.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

@SuppressWarnings("serial")
public class OrderedProperties extends Properties {

  @Override
  public synchronized Enumeration<Object> keys() {
    return Collections.enumeration(new TreeSet<Object>(super.keySet()));
  }

}
