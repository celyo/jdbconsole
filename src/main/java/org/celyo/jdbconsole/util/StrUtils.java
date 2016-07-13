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


package org.celyo.jdbconsole.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrUtils {
  private static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat defaultDateTimeFormat =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private StrUtils() {}

  public static Integer asInteger(String param) {
    if (param == null) {
      return null;
    }

    if (param instanceof String) {
      return Integer.valueOf(param);
    } else {
      throw new IllegalArgumentException(
          "Invalid agrument type. Convertion can be made only from String.");
    }
  }

  public static Long asLong(String param) {
    if (param == null) {
      return null;
    }

    if (param instanceof String) {
      return Long.valueOf(param);
    } else {
      throw new IllegalArgumentException(
          "Invalid agrument type. Convertion can be made only from String.");
    }
  }

  public static Date asDate(String param) {
    return asDate(param, defaultDateFormat);
  }

  public static Date asDateTime(String param) {
    return asDate(param, defaultDateTimeFormat);
  }

  public static Date asDate(String param, String pattern) {
    return asDate(param, new SimpleDateFormat(pattern));
  }

  private static Date asDate(String param, SimpleDateFormat format) {
    if (param == null) {
      return null;
    }

    if (param instanceof String) {
      try {
        return format.parse(param);
      } catch (ParseException e) {
        throw new IllegalArgumentException(
            "Invalid date format. Expected format is: " + format.toPattern() + ".");
      }
    } else {
      throw new IllegalArgumentException(
          "Invalid agrument type. Convertion can be made only from String.");
    }
  }
}
