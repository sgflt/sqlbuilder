/*
 * This class simplifies dynamic sql generation.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.Condition
import eu.qwsome.sql.condition.ValueConstructor

class Select private constructor(vararg columns: String) : Query {

    /**
     * List of columns that will be selected.
     */
    private val columns: MutableList<String> = columns.toMutableList()

    /**
     * A list of joins that generates multiple JOIN clauses.
     */
    private val joins: MutableList<Join> = mutableListOf()

    /**
     * A list of conditions from subselects
     */
    private val subselectConditions: MutableList<Condition> = mutableListOf()

    /**
     * True if SELECT DISTINCT shall be used
     */
    private var distinct: Boolean = false

    /**
     * Datasource after FROM (might be table or sub-query).
     */
    private var source: String? = null

    /**
     * Condition used in WHERE clause.
     */
    private var condition: Condition? = null

    private var orderBy: OrderBy? = null
    private var groupBy: GroupBy? = null

    /**
     * This may contain conditions from union used as subselect
     */
    private val union: MutableList<ValueBinding> = mutableListOf()

    /**
     * This method enables use of distinction.
     *
     * @return select with enabled distinction
     */
    fun distinct(): Select = apply {
        distinct = true
    }

    /**
     * This method sets a source table for select.
     *
     * @param table to be used in FROM clause
     * @return next phase that allows only relevant methods
     */
    fun from(table: String): TableSelectedPhase {
        source = table
        return TableSelectedPhase()
    }

    /**
     * This method sets a source source sub-query for select.
     *
     * @param subquery source sub-query
     * @param alias alias of the sub-query
     * @return next selection phase
     */
    fun from(subquery: Query, alias: String): TableSelectedPhase {
        source = "( ${subquery.toSql()} ) AS $alias"

        when (subquery) {
            is ConditionsBuiltPhase -> {
                subquery.getSelect().condition?.let { subselectConditions.add(it) }
            }

            is ValueBinding -> {
                union.add(subquery)
            }
        }

        return TableSelectedPhase()
    }

    /**
     * Generates final SQL.
     *
     * @return generated SQL
     */
    override fun toSql(): String = buildString {
        append("SELECT ")
        if (distinct) {
            append("DISTINCT ")
        }
        append(getColumns())
        append(" FROM ")
        append(source)

        joins.forEach { join ->
            join.appendTo(this)
        }

        condition?.let {
            append(" WHERE ")
            it.appendTo(this)
        }

        groupBy?.appendTo(this)
        orderBy?.appendTo(this)
    }

    /**
     * Adds an order by clause to the statement.
     *
     * @param columns that defines ordering
     * @return order by phase
     */
    private fun orderBy(vararg columns: Column): OrderByPhase {
        orderBy = OrderBy(*columns)
        return OrderByPhase()
    }

    private fun groupBy(vararg groupByColumns: Column): GroupByPhase {
        groupBy = GroupBy(*groupByColumns)
        return GroupByPhase()
    }

    private fun toValuesInternal(): ValueConstructor {
        val values = ValueConstructor()

        joins.forEach { values.add(it.toValues()) }
        subselectConditions.forEach { values.add(it.getValues()) }
        union.forEach { values.add(it.toValues()) }

        condition?.let { values.add(it.getValues()) }

        return values
    }

    /**
     * This method generates a list of columns concatenated with comma.
     *
     * @return list of columns usable in SQL
     */
    private fun getColumns(): String {
        return columns.joinToString(", ")
    }

    inner class TableSelectedPhase : ValueBinding {
        /**
         * @see Select.toSql
         */
        override fun toSql(): String {
            return this@Select.toSql()
        }

        /**
         * This method allows creation of where clause.
         *
         * @param condition condition used to filter data
         * @return next phase that allows only relevant methods
         */
        fun where(condition: Condition?): ConditionsBuiltPhase {
            this@Select.condition = condition
            return ConditionsBuiltPhase()
        }

        /**
         * Adds an order by clause to the statement.
         *
         * @param columns that defines ordering
         * @return order by phase
         */
        fun orderBy(vararg columns: Column): OrderByPhase {
            return this@Select.orderBy(*columns)
        }

        /**
         * Adds a join clause to the statement.
         *
         * @param joinTable table to be joined
         * @return next phase that allows only relevant methods
         */
        fun join(joinTable: String): TableJoinPhase {
            return TableJoinPhase(joinTable, TableJoin.Type.INNER)
        }

        /**
         * Adds a join clause to the statement.
         *
         * @param subselect to be joined
         * @param alias for subselect
         * @return next phase that allows only relevant methods
         */
        fun join(subselect: SubselectValueHolder, alias: String): SubselectJoinPhase {
            return SubselectJoinPhase(subselect, alias, SubselectJoin.Type.INNER)
        }

        /**
         * Adds a left join clause to the statement.
         *
         * @param joinTable table to be joined
         * @return next phase that allows only relevant methods
         */
        fun leftJoin(joinTable: String): TableJoinPhase {
            return TableJoinPhase(joinTable, TableJoin.Type.LEFT)
        }

        /**
         * Adds a join clause to the statement.
         *
         * @param subselect to be joined
         * @param alias for subselect
         * @return next phase that allows only relevant methods
         */
        fun leftJoin(subselect: SubselectValueHolder, alias: String): SubselectJoinPhase {
            return SubselectJoinPhase(subselect, alias, SubselectJoin.Type.LEFT)
        }

        override fun toValues(): ValueConstructor {
            return toValuesInternal()
        }

        fun groupBy(vararg groupByColumns: Column): GroupByPhase {
            return this@Select.groupBy(*groupByColumns)
        }
    }

    /**
     * Final phase.
     */
    inner class ConditionsBuiltPhase : ValueBinding {
        /**
         * @return generated SQL
         * @see Select.toSql
         */
        override fun toSql(): String {
            return this@Select.toSql()
        }

        override fun toValues(): ValueConstructor {
            return toValuesInternal()
        }

        internal fun getSelect(): Select {
            return this@Select
        }

        /**
         * Adds an order by clause to the statement.
         *
         * @param columns that defines ordering
         * @return order by phase
         */
        fun orderBy(vararg columns: Column): OrderByPhase {
            return this@Select.orderBy(*columns)
        }

        fun groupBy(vararg columns: Column): GroupByPhase {
            return this@Select.groupBy(*columns)
        }

        fun union(otherSelect: ConditionsBuiltPhase): Union {
            return Union.of(this, otherSelect)
        }
    }

    /**
     * Phase available after [TableSelectedPhase]
     */
    inner class TableJoinPhase(
        private val joinTable: String,
        private val type: TableJoin.Type,
    ) {

        /**
         * Creates a join clause with condition.
         *
         * @param condition that will be used in ON clause
         * @return next phase that allows only relevant methods
         */
        fun on(condition: Condition): TableSelectedPhase {
            val join = when (type) {
                TableJoin.Type.INNER -> InnerTableJoin(joinTable, condition)
                TableJoin.Type.LEFT -> LeftTableJoin(joinTable, condition)
            }
            this@Select.joins.add(join)
            return TableSelectedPhase()
        }
    }

    /**
     * Phase available after [TableSelectedPhase]
     */
    inner class SubselectJoinPhase(
        private val subselect: SubselectValueHolder,
        private val alias: String,
        private val type: SubselectJoin.Type,
    ) {

        /**
         * Creates a join clause with condition.
         *
         * @param condition that will be used in ON clause
         * @return next phase that allows only relevant methods
         */
        fun on(condition: Condition): TableSelectedPhase {
            val join = when (type) {
                SubselectJoin.Type.INNER -> SubselectInnerJoin(subselect, alias, condition)
                SubselectJoin.Type.LEFT -> SubselectLeftJoin(subselect, alias, condition)
            }
            this@Select.joins.add(join)
            return TableSelectedPhase()
        }
    }

    inner class OrderByPhase : ValueBinding {
        /**
         * @return generated SQL
         */
        override fun toSql(): String {
            return this@Select.toSql()
        }

        override fun toValues(): ValueConstructor {
            return toValuesInternal()
        }
    }

    inner class GroupByPhase : ValueBinding {
        /**
         * @return generated SQL
         */
        override fun toSql(): String {
            return this@Select.toSql()
        }

        override fun toValues(): ValueConstructor {
            return toValuesInternal()
        }

        fun orderBy(vararg orderByColumns: Column): OrderByPhase {
            return this@Select.orderBy(*orderByColumns)
        }
    }

    companion object {
        /**
         * This method creates a select instance that can be effectively used as following snippet:
         *
         * ```
         * select().from("table").toSql()
         * ```
         *
         * that is translated into:
         *
         * ```
         * SELECT * FROM table
         * ```
         *
         * @return select builder
         */
        @JvmStatic
        fun select() = Select("*")

        /**
         * This method creates a select instance that can be effectively used as following snippet:
         *
         * ```
         * select("column").from("table").toSql()
         * ```
         *
         * that is translated into:
         *
         * ```
         * SELECT column FROM table
         * ```
         *
         * @param column name that will be selected
         * @return select builder
         */
        @JvmStatic
        fun select(column: String) = Select(column)

        /**
         * This method creates a select instance that can be effectively used as following snippet:
         *
         * ```
         * select("column1", "column2", ...).from("table").toSql()
         * ```
         *
         * that is translated into:
         *
         * ```
         * SELECT column1, column2, ... FROM table
         * ```
         *
         * @return select builder
         */
        @JvmStatic
        fun select(vararg columns: String): Select {
            return Select(*columns)
        }
    }
}
