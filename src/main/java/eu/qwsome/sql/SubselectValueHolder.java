package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueHolder;

/**
 * This class represents a subselect value in sql.
 *
 * @author Lukáš Kvídera
 */
public class SubselectValueHolder implements ValueHolder {

  private final Select.ConditionsBuiltPhase value;


  private SubselectValueHolder(final Select.ConditionsBuiltPhase value) {
    this.value = value;
  }


  public static SubselectValueHolder subselect(final Select.ConditionsBuiltPhase value) {
    return new SubselectValueHolder(value);
  }


  @Override
  public String getSql() {
    return '(' + this.value.toSql() + ')';
  }


  @Override
  public Object getValue() {
    return this.value.toValues();
  }

  @Override
  public ValueHolder apply(final String functionName) {
    return new FunctionWrappedHolder(functionName, this);
  }
}
