/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.bvt.sql.postgresql.select;

import com.alibaba.druid.sql.PGTest;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.postgresql.parser.PGSQLStatementParser;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import org.junit.Assert;

import java.util.List;

public class PGSelectTest23 extends PGTest {
    public void test_0() throws Exception {
        String sql = "select id, login_name, name, password, salt, roles, register_date from user WHERE ( name like ? ) limit ? offset ?";

        PGSQLStatementParser parser = new PGSQLStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement stmt = statementList.get(0);
//        print(statementList);

        Assert.assertEquals("SELECT id, login_name, name, password, salt"
                + "\n\t, roles, register_date"
                + "\nFROM user"
                + "\nWHERE (name LIKE ?)"
                + "\nLIMIT ? OFFSET ?", SQLUtils.toPGString(stmt));

        Assert.assertEquals("select id, login_name, name, password, salt"
                + "\n\t, roles, register_date"
                + "\nfrom user"
                + "\nwhere (name like ?)"
                + "\nlimit ? offset ?", SQLUtils.toPGString(stmt, SQLUtils.DEFAULT_LCASE_FORMAT_OPTION));

        Assert.assertEquals(1, statementList.size());

        PGSchemaStatVisitor visitor = new PGSchemaStatVisitor();
        stmt.accept(visitor);

//        System.out.println("Tables : " + visitor.getTables());
//        System.out.println("fields : " + visitor.getColumns());
//        System.out.println("coditions : " + visitor.getConditions());

        Assert.assertEquals(7, visitor.getColumns().size());
        Assert.assertEquals(1, visitor.getTables().size());
    }
}
