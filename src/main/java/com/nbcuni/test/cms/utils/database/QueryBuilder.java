package com.nbcuni.test.cms.utils.database;

import java.util.List;

/*********************************************************************
 * Query Builder
 *
 * Description: build string to execute as SQL query
 *
 * Revision Log--------------------------------------------------------- Date- , Author- , Change
 * Description-
 ***********************************************************************/

public class QueryBuilder {

    private StringBuilder query;

    private QueryBuilder() {
        query = new StringBuilder();
    }

    private QueryBuilder(final StringBuilder query) {
        this.query = query;
    }

    public static QueryBuilder select(final String... columns) {
        StringBuilder query = new StringBuilder();
        query = query.append("select ");
        if (columns == null || (columns != null && columns.length <= 0)) {
            query = query.append(" * ");
        }
        for (int index = 0; index < columns.length; index++) {
            query = index != columns.length - 1 ? query.append(columns[index]).append(", ") : query
                    .append(columns[index]);
        }
        return new QueryBuilder(query);
    }

    public static QueryBuilder insert(String tableName, final String... values) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query = query.append(tableName);
        query.append(" VALUES");
        query.append("(");
        for (int index = 0; index < values.length; index++) {
            query.append("'");
            query.append(values[index]);
            query.append("',");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return new QueryBuilder(query);
    }

    public QueryBuilder equals(final String columnName, final String value) {
        query = query.append(columnName).append("='").append(value + "'");
        return this;
    }

    public QueryBuilder notEquals(final String columnName, final String value) {
        query = query.append(columnName).append("!=").append(value);
        return this;
    }

    public QueryBuilder and() {
        query = query.append(" and ");
        return this;
    }

    public QueryBuilder or() {
        query = query.append(" or ");
        return this;
    }

    public QueryBuilder where() {
        query = query.append(" where ");
        return this;
    }

    public QueryBuilder in(final String columnName, final List<String> setOfStringValues) {
        query = query.append(columnName).append(" in (");
        for (final String value : setOfStringValues) {
            query = query.append("'").append(value).append("',");
        }
        query = query.deleteCharAt(query.length() - 1).append(")");
        return this;
    }

    public QueryBuilder greaterOrEqual(final String columnName, final String value) {
        query = query.append(columnName).append(">=").append(value);
        return this;
    }

    public QueryBuilder lessOrEqual(final String columnName, final String value) {
        query = query.append(columnName).append("<=").append(value);
        return this;
    }

    public QueryBuilder isNull(final String columnName) {
        query = query.append(columnName).append(" IS NULL ");
        return this;
    }

    public QueryBuilder from(final String table) {
        query = query.append(" from ").append(table);
        return this;
    }

    public QueryBuilder groupBy(final String columnName) {
        query = query.append(" group by ").append(columnName);
        return this;
    }

    public QueryBuilder join(final String table, final String one, final String another) {
        query = query.append(" join ").append(table).append(" on ").append(one).append("=").append(another);
        return this;
    }

    public QueryBuilder leftJoin(final String table, final String one, final String another) {
        query = query.append(" left join ").append(table).append(" on ").append(one).append("=").append(another);
        return this;
    }

    public QueryBuilder orderBy(final String columnName) {
        query = query.append(" order by ").append(columnName);
        return this;
    }

    public String build() {
        System.out.println(query.toString());
        return query.toString();
    }
}