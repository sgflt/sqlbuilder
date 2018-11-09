package eu.qwsome.sql;

import eu.qwsome.sql.api.Appendable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * @author Lukáš Kvídera
 */
class OrderBy implements Appendable {

  private final Column[] columns;

  OrderBy(final Column[] columns) {
    this.columns = Objects.requireNonNull(columns, "Provide valid columns!");
  }

  @Override
  public void appendTo(final StringBuilder builder) {
    builder.append(" ORDER BY ");
    Arrays.stream(this.columns)
      .map(Column::getSql)
      .collect(
        Collector.of(
          () -> builder,
          new ColumnJoiner(),
          StringBuilder::append,
          Function.identity()
        )
      );
  }

  /**
   * Helper class that concatenates columns with comma.
   */
  private static class ColumnJoiner implements BiConsumer<StringBuilder, String> {

    private boolean first = true;

    @Override
    public void accept(final StringBuilder sql, final String column) {
      if (this.first) {
        sql.append(column);
        this.first = false;
        return;
      }

      sql.append(',');
      sql.append(column);
    }
  }
}
