package com.alibaba.druid.sql.dialect.oracle.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObjectImpl;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;

public class OracleExceptionStatement extends OracleSQLObjectImpl implements OracleStatement {

    private static final long serialVersionUID = 1L;
    private List<Item>        items            = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item extends OracleSQLObjectImpl {

        private static final long serialVersionUID = 1L;
        private SQLExpr           when;
        private SQLStatement      statement;

        public SQLExpr getWhen() {
            return when;
        }

        public void setWhen(SQLExpr when) {
            this.when = when;
        }

        public SQLStatement getStatement() {
            return statement;
        }

        public void setStatement(SQLStatement statement) {
            this.statement = statement;
        }

        @Override
        public void accept0(OracleASTVisitor visitor) {
            if (visitor.visit(this)) {
                acceptChild(visitor, when);
                acceptChild(visitor, statement);
            }
            visitor.endVisit(this);
        }

    }

    @Override
    public void accept0(OracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, items);
        }
        visitor.endVisit(this);
    }
}
