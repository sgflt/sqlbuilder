package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueHolder;

/**
 * This class represents a literal value in sql.
 *
 * For example: column = 1
 * where 1 is a literal.
 *
 * @author Lukáš Kvídera
 */
public class ValueLiteral implements ValueHolder {

  private final Object value;

  private ValueLiteral(final Object value) {
    this.value = value;
  }

  public static ValueLiteral value(final Object value) {
    return new ValueLiteral(value);
  }

  @Override
  public String getSql() {
    return "?";
  }

  @Override
  public Object getValue() {
    return this.value;
  }
}
