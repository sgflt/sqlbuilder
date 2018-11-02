package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lukáš Kvídera
 */
public class Select {

  private String sourceTable;
  private final List<String> columns = new ArrayList<>();
  private final List<Condition> conditions= new ArrayList<>();

  private Select(final String columns) {
    this.columns.add(columns);
  }

  public Select(final String[] columns) {
    this.columns.addAll(Arrays.asList(columns));
  }

  public static Select select() {
    return new Select("*");
  }

  public static Select select(final String column) {
    return new Select(column);
  }

  public static Select select(final String... columns) {
    return new Select(columns);
  }

  public TableSelectedPhase from(final String table) {
    this.sourceTable = table;
    return new TableSelectedPhase();
  }

  private String toSql() {
    final StringBuilder builder = new StringBuilder();
    builder.append("SELECT ")
        .append(getColumns())
        .append(" FROM " )
        .append(this.sourceTable);

    if (!this.conditions.isEmpty()) {
      builder.append(" WHERE ");
      for (final Condition condition : this.conditions) {
        builder.append(condition.get());
      }
    }
    return builder.toString();
  }

  private String getColumns() {
    return String.join(",", this.columns);
  }


  public class TableSelectedPhase {
    public String toSql() {
      return Select.this.toSql();
    }

    public ConditionsBuiltPhase where(final Condition eq) {
      Select.this.conditions.add(eq);
      return new ConditionsBuiltPhase();
    }
  }

  public class ConditionsBuiltPhase {
    public String toSql() {
      return Select.this.toSql();
    }

  }
}
