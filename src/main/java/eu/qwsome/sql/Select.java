package eu.qwsome.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.qwsome.sql.TableJoin.Type;
import eu.qwsome.sql.condition.Condition;
import eu.qwsome.sql.condition.ValueConstructor;

/**
 * This class simplifies dynamic sql generation.
 *
 * @author Lukáš Kvídera
 */
public class Select implements Query {

  /**
   * List of columns that will be selected.
   */
  private final List<String> columns = new ArrayList<>();
  /**
   * A list of joins that generates multiple JOIN clauses.
   */
  private final List<Join> joins = new ArrayList<>();
  /**
   * A list of conditions from subselects
   */
  private final List<Condition> subselectConditions = new ArrayList<>();
  /**
   * True if SELECT DISTINCT shall be used
   */
  private boolean distinct;
  /**
   * Datasource after FROM (might be table or sub-query).
   */
  private String source;
  /**
   * Condition used in WHERE clause.
   */
  private Condition condition;
  private OrderBy orderBy;
  private GroupBy groupBy;
  /**
   * This may contain conditions from unin used as subselect
   */
  private final List<ValueBinding> union = new ArrayList<>();


  /**
   * Creates select with single column.
   *
   * @param column name of column to be selected
   */
  private Select(final String column) {
    this.columns.add(column);
  }


  /**
   * Creates select with multiple columns.
   *
   * @param columns names of columns to be selected
   */
  private Select(final String[] columns) {
    this.columns.addAll(Arrays.asList(columns));
  }


  /**
   * This mehod creates a select instance that can be effectively used as following snippet:
   * <p>
   * {@code
   * select().from("table").toSql()
   * }
   * <p>
   * that is translated into:
   * <p>
   * {@code
   * SELECT * FROM table
   * }
   *
   * @return select builder
   */
  public static Select select() {
    return new Select("*");
  }


  /**
   * This mehod creates a select instance that can be effectively used as following snippet:
   * <p>
   * {@code
   * select("column").from("table").toSql()
   * }
   * <p>
   * that is translated into:
   * <p>
   * {@code
   * SELECT column FROM table
   * }
   *
   * @param column name that will be selected
   *
   * @return select builder
   */
  public static Select select(final String column) {
    return new Select(column);
  }


  /**
   * This mehod creates a select instance that can be effectively used as following snippet:
   * <p>
   * {@code
   * select("column1", "column2", ...).from("table").toSql()
   * }
   * <p>
   * that is translated into:
   * <p>
   * {@code
   * SELECT column1, column2, ... FROM table
   * }
   *
   * @return select builder
   */
  public static Select select(final String... columns) {
    return new Select(columns);
  }


  /**
   * This method enables use of distinction.
   *
   * @return select with enabled distinction
   */
  public Select distinct() {
    this.distinct = true;
    return this;
  }


  /**
   * This method sets a source table for select.
   *
   * @param table to be used in FROM clause
   *
   * @return next phase that allows only relevant methods
   */
  public TableSelectedPhase from(final String table) {
    this.source = table;
    return new TableSelectedPhase();
  }


  /**
   * This method sets a source source sub-query for select.
   *
   * @param subquery source sub-query
   * @param alias alias of the sub-query
   *
   * @return next selection phase
   */
  public TableSelectedPhase from(final Query subquery, final String alias) {
    this.source = "( "
        .concat(subquery.toSql())
        .concat(" ) AS ")
        .concat(alias);

    if (subquery instanceof ConditionsBuiltPhase) {
      this.subselectConditions.add(((ConditionsBuiltPhase) subquery).getSelect().condition);
    } else if (subquery instanceof ValueBinding) {
      this.union.add((ValueBinding) subquery);
    }

    return new TableSelectedPhase();
  }


  /**
   * Generates final SQL.
   *
   * @return generated SQL
   */
  @Override
  public String toSql() {
    final StringBuilder builder = new StringBuilder();
    builder.append("SELECT ")
        .append(this.distinct ? "DISTINCT " : "")
        .append(getColumns())
        .append(" FROM ")
        .append(this.source)
    ;

    if (!this.joins.isEmpty()) {
      for (final var join : this.joins) {
        join.appendTo(builder);
      }
    }

    if (this.condition != null) {
      builder.append(" WHERE ");
      this.condition.appendTo(builder);
    }

    if (this.groupBy != null) {
      this.groupBy.appendTo(builder);
    }

    if (this.orderBy != null) {
      this.orderBy.appendTo(builder);
    }


    return builder.toString();
  }


  /**
   * Adds an order by clause to the statement.
   *
   * @param columns that defines ordering
   *
   * @return order by phase
   */
  private OrderByPhase orderBy(final Column... columns) {
    this.orderBy = new OrderBy(columns);
    return new OrderByPhase();
  }


  private GroupByPhase groupBy(final Column... groupByColumns) {
    this.groupBy = new GroupBy(groupByColumns);
    return new GroupByPhase();
  }


  private ValueConstructor toValuesInternal() {
    final var values = new ValueConstructor();

    Select.this.joins.stream()
        .map(Join::toValues)
        .forEach(values::add)
    ;

    Select.this.subselectConditions.stream()
        .map(Condition::getValues)
        .forEach(values::add)
    ;

    Select.this.union.stream()
        .map(ValueBinding::toValues)
        .forEach(values::add)
    ;

    if (this.condition != null) {
      values.add(Select.this.condition.getValues());
    }

    return values;
  }


  /**
   * This method generates a list of columns concatenated with comma.
   *
   * @return list of columns usable in SQL
   */
  private String getColumns() {
    return String.join(", ", this.columns);
  }


  public class TableSelectedPhase implements ValueBinding {
    /**
     * @see Select#toSql()
     */
    @Override
    public String toSql() {
      return Select.this.toSql();
    }


    /**
     * This method allows creation of where clause.
     *
     * @param condition condition used to filter data
     *
     * @return next phase that allows only relevant methods
     */
    public ConditionsBuiltPhase where(final Condition condition) {
      Select.this.condition = condition;
      return new ConditionsBuiltPhase();
    }


    /**
     * Adds an order by clause to the statement.
     *
     * @param columns that defines ordering
     *
     * @return order by phase
     */
    public OrderByPhase orderBy(final Column... columns) {
      return Select.this.orderBy(columns);
    }


    /**
     * Adds a join clause to the statement.
     *
     * @param joinTable table to be joined
     *
     * @return next phase that allows only relevant methods
     */
    public TableJoinPhase join(final String joinTable) {
      return new TableJoinPhase(joinTable, Type.INNER);
    }


    /**
     * Adds a join clause to the statement.
     *
     * @param subselect to be joined
     *      * @param alias for subselect
     *
     * @return next phase that allows only relevant methods
     */
    public SubselectJoinPhase join(final SubselectValueHolder subselect, final String alias) {
      return new SubselectJoinPhase(subselect, alias, Type.INNER);
    }


    /**
     * Adds a left join clause to the statement.
     *
     * @param joinTable table to be joined
     *
     * @return next phase that allows only relevant methods
     */
    public TableJoinPhase leftJoin(final String joinTable) {
      return new TableJoinPhase(joinTable, Type.LEFT);
    }


    /**
     * Adds a join clause to the statement.
     *
     * @param subselect to be joined
     * @param alias for subselect
     *
     * @return next phase that allows only relevant methods
     */
    public SubselectJoinPhase leftJoin(final SubselectValueHolder subselect, final String alias) {
      return new SubselectJoinPhase(subselect, alias, Type.LEFT);
    }


    @Override
    public ValueConstructor toValues() {
      return toValuesInternal();
    }


    public GroupByPhase groupBy(final Column... groupByColumns) {
      return Select.this.groupBy(groupByColumns);
    }
  }

  /**
   * Final phase.
   */
  public class ConditionsBuiltPhase implements ValueBinding {
    /**
     * @return generated SQL
     *
     * @see Select#toSql()
     */
    @Override
    public String toSql() {
      return Select.this.toSql();
    }


    @Override
    public ValueConstructor toValues() {
      return toValuesInternal();
    }


    Select getSelect() {
      return Select.this;
    }


    /**
     * Adds an order by clause to the statement.
     *
     * @param columns that defines ordering
     *
     * @return order by phase
     */
    public OrderByPhase orderBy(final Column... columns) {
      return Select.this.orderBy(columns);
    }


    public GroupByPhase groupBy(final Column... columns) {
      return Select.this.groupBy(columns);
    }


    public Union union(final ConditionsBuiltPhase otherSelect) {
      return Union.of(this, otherSelect);
    }
  }

  /**
   * Phase available after {@link TableSelectedPhase}
   */
  public class TableJoinPhase {

    /**
     * Table that will be joined.
     */
    private final String joinTable;
    private final Type type;


    private TableJoinPhase(final String joinTable, final Type type) {
      this.joinTable = joinTable;
      this.type = type;
    }


    /**
     * Creates a join clues with condition.
     *
     * @param condition that will be used in ON clause
     *
     * @return next phase that allows only relevant methods
     */
    public TableSelectedPhase on(final Condition condition) {
      switch (this.type) {
        case INNER:
          Select.this.joins.add(new InnerTableJoin(this.joinTable, condition));
          break;
        case LEFT:
          Select.this.joins.add(new LeftTableJoin(this.joinTable, condition));
          break;
        default:
          throw new UnsupportedOperationException(this.type.toString());
      }
      return new TableSelectedPhase();
    }
  }

  /**
   * Phase available after {@link TableSelectedPhase}
   */
  public class SubselectJoinPhase {

    /**
     * Table that will be joined.
     */
    private final SubselectValueHolder subselect;
    private final String alias;
    private final Type type;


    private SubselectJoinPhase(final SubselectValueHolder subselect, String alias, final Type type) {
      this.subselect = subselect;
      this.alias = alias;
      this.type = type;
    }


    /**
     * Creates a join clues with condition.
     *
     * @param condition that will be used in ON clause
     *
     * @return next phase that allows only relevant methods
     */
    public TableSelectedPhase on(final Condition condition) {
      switch (this.type) {
        case INNER:
          Select.this.joins.add(new SubselectInnerJoin(this.subselect, this.alias, condition));
          break;
        case LEFT:
          Select.this.joins.add(new SubselectLeftJoin(this.subselect, this.alias, condition));
          break;
        default:
          throw new UnsupportedOperationException(this.type.toString());
      }
      return new TableSelectedPhase();
    }
  }

  public class OrderByPhase implements ValueBinding {
    /**
     * @return generated SQL
     */
    @Override
    public String toSql() {
      return Select.this.toSql();
    }


    @Override
    public ValueConstructor toValues() {
      return toValuesInternal();
    }


  }

  public class GroupByPhase implements ValueBinding {
    /**
     * @return generated SQL
     */
    @Override
    public String toSql() {
      return Select.this.toSql();
    }


    @Override
    public ValueConstructor toValues() {
      return toValuesInternal();
    }


    public OrderByPhase orderBy(final Column... orderByColumns) {
      return Select.this.orderBy(orderByColumns);
    }
  }

}
