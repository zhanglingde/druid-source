/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
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
package com.alibaba.druid.pool.demo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JMXUtils;

public class Demo0 extends TestCase {
    private String jdbcUrl;
    private String user;
    private String password;
    private String driverClass;
    private int initialSize = 2;
    private int minIdle = 1;
    private int maxIdle = 5;
    private int maxActive = 12;

    protected void setUp() throws Exception {
        jdbcUrl = "jdbc:fake:dragoon_v25masterdb";
        user = "dragoon25";
        password = "dragoon25";
        driverClass = "com.alibaba.druid.mock.MockDriver";
    }

    public void test_0() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();

        JMXUtils.register("com.alibaba:type=DruidDataSource", dataSource);

        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setFilters("stat,wall,log4j2");


        List<Connection> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Connection conn = dataSource.getConnection();
            list.add(conn);
        }
        for (Connection conn : list) {
            conn.close();
        }

        System.out.println();
    }
}
