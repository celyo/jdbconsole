/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.celyo.jdbconsole.ui;

import org.celyo.jdbconsole.model.ConnectionInfo;

/**
 *
 * @author master
 */
public interface ConnectionChangeListener {
  void onConnectionChange(ConnectionInfo conn);
}
