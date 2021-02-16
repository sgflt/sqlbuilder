package eu.qwsome.sql;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Lukáš Kvídera
 */
public class Union implements Query {

    private final Query[] queries;
    private final boolean all;

    public Union(final Query[] queries, final boolean all) {
        this.queries = queries;
        this.all = all;
    }

    /**
     * Creates UNION clause.
     *
     * @param queries that will be used in UNION
     * @return the union clause
     */
    public static Union of(final Query... queries) {
        return new Union(queries, false);
    }

    /**
     * Creates UNION ALL clause.
     *
     * @param queries that will be used in UNION without distinct values
     * @return the union all clause
     */
    public static Union allOf(final Query... queries) {
        return new Union(queries, true);
    }

    @Override
    public String toSql() {
        final String keyword = this.all ? " UNION ALL " : " UNION ";
        return Stream.of(this.queries)
                .map(Query::toSql)
                .collect(Collectors.joining(keyword));
    }
}
