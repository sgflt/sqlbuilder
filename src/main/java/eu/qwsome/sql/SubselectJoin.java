package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;
import eu.qwsome.sql.condition.ValueConstructor;

/**
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
abstract class SubselectJoin implements Join {

  /**
   * Subselect used in JOIN clause.
   */
  private final SubselectValueHolder subselect;

  /**
   * Condition in ON clause.
   */
  private final Condition condition;
  private final String alias;


  SubselectJoin(final SubselectValueHolder subselect, final String alias, final Condition condition) {
    this.subselect = subselect;
    this.condition = condition;
    this.alias = alias;
  }


  public CharSequence get() {
    final StringBuilder builder = new StringBuilder();
    appendTo(builder);
    return builder;
  }


  @Override
  public void appendTo(final StringBuilder builder) {
    builder.append(getPrefix())
        .append(" JOIN ")
        .append(this.subselect.getSql())
        .append(' ')
        .append(this.alias)
        .append(" ON ");

    this.condition.appendTo(builder);
  }


  /**
   * @return variables to be bound in ON clause's conditions
   */
  public ValueConstructor toValues() {
    return new ValueConstructor()
        .add(subselect)
        .add(this.condition.getValues());
  }


  /**
   * @return tyoe if join
   */
  abstract CharSequence getPrefix();


  /**
   * This enum determines type of join.
   */
  enum Type {
    LEFT,
    INNER
  }
}
