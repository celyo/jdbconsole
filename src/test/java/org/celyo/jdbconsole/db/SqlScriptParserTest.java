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

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqlScriptParserTest {
  
   @Test
   public void testAllStatements_emptyScript() {
     List<String> lines = new ArrayList<>();
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     assertNotNull(parser.getStatements());
     assertEquals(0, parser.getStatements().size());
   }

   @Test
   public void testAllStatements_simpleScript() {
     List<String> lines = new ArrayList<>();
     lines.add("select * from customers;");
     lines.add("select * from accounts;");
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     assertNotNull(parser.getStatements());
     assertEquals(2, parser.getStatements().size());
   }

   @Test
   public void testAllStatements_simpleScriptWithComments() {
     List<String> lines = new ArrayList<>();
     lines.add("-- selecting customers");
     lines.add("select * from customers;");
     lines.add("/* selecting accounts */");
     lines.add("select * from accounts;");
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     assertNotNull(parser.getStatements());
     assertEquals(2, parser.getStatements().size());
   }

   @Test
   public void testAllStatements_simpleScriptWithSemicolonInComments() {
     List<String> lines = new ArrayList<>();
     lines.add("-- selecting ; customers");
     lines.add("select * from customers;");
     lines.add("/* ++selecting-- ; accounts */");
     lines.add("select * from accounts;");
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     assertNotNull(parser.getStatements());
     assertEquals(2, parser.getStatements().size());
   }

   @Test
   public void testAllStatements_averageScriptWithComments() {
     List<String> lines = new ArrayList<>();
     lines.add("/*");
     lines.add("*");
     lines.add("* Copyright 2016 Celyo");
     lines.add("*");
     lines.add("*/");
     lines.add("select");
     lines.add("  a.id,");
     lines.add("  a.name,");
     lines.add("  a.balance,");
     lines.add("  a.type");
     lines.add("from");
     lines.add("  accounts a");
     lines.add("where 1=1");
     lines.add("  and a.balance > 10");
     lines.add(";");
     lines.add("");
     lines.add("select * from customers;select * from users;");
     lines.add("");
     lines.add("");
     lines.add("select x from dummy --; delete from dummy");
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     assertNotNull(parser.getStatements());
     assertEquals(4, parser.getStatements().size());
   }

   @Test
   public void testAllStatements_averageScriptWithComments2() {
     List<String> lines = new ArrayList<>();
     lines.add("select x from dual where x <> ''; /* And now ... ");
     lines.add("* Multi");
     lines.add("* Line");
     lines.add("* Comment");
     lines.add("*/");
     lines.add("select");
     lines.add("  a.id,");
     lines.add("  --a.name,");
     lines.add("  a.balance,");
     lines.add("  'some -- test ++ string' || '/* comment inside string*/' as text,");
     lines.add("  a.type");
     lines.add("from");
     lines.add("  accounts a");
     lines.add("where 1=1");
     lines.add("  and a.balance > 10");
     lines.add(";");
     lines.add("");
     lines.add("select * from /*new_*/customers;select * from users;");
     lines.add("");
     lines.add("");
     lines.add("select x from dummy --; delete from dummy");
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     assertNotNull(parser.getStatements());
     assertEquals(5, parser.getStatements().size());
   }

   @Test
   public void testCurrentStatement_emptyScript() {
     List<String> lines = new ArrayList<>();
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     assertNull(parser.getSattement(0, 0));
     assertNull(parser.getSattement(100, 100));
   }

   @Test
   public void testCurrentStatement_averageScriptWithComments2() {
     List<String> lines = new ArrayList<>();
     lines.add("select x from dual where x <> ''; /* And now ... ");
     lines.add("* Multi");
     lines.add("* Line");
     lines.add("* Comment");
     lines.add("*/");
     lines.add("select");
     lines.add("  a.id,");
     lines.add("  --a.name,");
     lines.add("  a.balance,");
     lines.add("  'some -- test ++ string' || '/* comment inside string*/' as text,");
     lines.add("  a.type");
     lines.add("from");
     lines.add("  accounts a");
     lines.add("where 1=1");
     lines.add("  and a.balance > 10");
     lines.add(";");
     lines.add("");
     lines.add("select * from /*new_*/customers;select * from users;");
     lines.add("");
     lines.add("");
     lines.add("select x from dummy --; delete from dummy");
     
     SqlScriptParser parser = new SqlScriptParser();
     parser.parse(lines);
     
     SqlStatement st = parser.getSattement(0, 0);
     assertNotNull(st);
     assertEquals("select x from dual where x <> '';", st.getSql());
     
     st = parser.getSattement(0, 20);
     assertNotNull(st);
     assertEquals("select x from dual where x <> '';", st.getSql());
     
     st = parser.getSattement(0, 32);
     assertNotNull(st);
     assertEquals("select x from dual where x <> '';", st.getSql());

     st = parser.getSattement(0, 33);
     assertNotNull(st);
     assertEquals(
        " /* And now ... " + 
        "\n* Multi" + 
        "\n* Line" + 
        "\n* Comment" + 
        "\n*/" + 
        "\nselect" + 
        "\n  a.id," + 
        "\n  --a.name," + 
        "\n  a.balance," + 
        "\n  'some -- test ++ string' || '/* comment inside string*/' as text," + 
        "\n  a.type" + 
        "\nfrom" + 
        "\n  accounts a" + 
        "\nwhere 1=1" + 
        "\n  and a.balance > 10" +
        "\n;"
        , st.getSql()
     );

     assertEquals(5, parser.getStatements().size());
   }
}
