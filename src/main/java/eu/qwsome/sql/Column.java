package eu.qwsome.sql;

import eu.qwsome.sql.condition.Between;
import eu.qwsome.sql.condition.Condition;
import eu.qwsome.sql.condition.IsEqual;
import eu.qwsome.sql.condition.IsLessThan;
import eu.qwsome.sql.condition.IsNull;
import eu.qwsome.sql.condition.IsNotNull;

/**
 * @author Lukáš Kvídera
 */
public class Column {

  private final String name;

  private Column(final String name) {
    this.name = name;
  }

  public static Column column(final String name) {
    return new Column(name);
  }

  public String get() {
    return this.name;
  }

  public Condition isEqualTo(final Column another) {
    return new IsEqual(this, another);
  }

  public Condition isBetween(final Column from, final Column to) {
    return new Between(this, from, to);
  }

  public Condition isNull() {
    return new IsNull(this);
  }

  public Condition isLessThan(final Column another) {
    return new IsLessThan(this, another);
  }

  public Condition isNotNull() {
    return new IsNotNull(this);
  }
}
