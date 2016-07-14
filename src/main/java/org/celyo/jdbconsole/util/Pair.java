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

import java.util.Objects;

public class Pair<T1, T2> {
  private T1 value1;
  private T2 value2;

  public Pair() {
  }

  public Pair(T1 value1, T2 value2) {
    this.value1 = value1;
    this.value2 = value2;
  }

  public T1 getValue1() {
    return value1;
  }

  public void setValue1(T1 value1) {
    this.value1 = value1;
  }

  public T2 getValue2() {
    return value2;
  }

  public void setValue2(T2 value2) {
    this.value2 = value2;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 97 * hash + Objects.hashCode(this.value1);
    hash = 97 * hash + Objects.hashCode(this.value2);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Pair<?, ?> other = (Pair<?, ?>) obj;
    if (!Objects.equals(this.value1, other.value1)) {
      return false;
    }
    if (!Objects.equals(this.value2, other.value2)) {
      return false;
    }
    return true;
  }
  
  
}
