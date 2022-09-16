package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueHolder;

/**
 * @author Lukáš Kvídera
 */
public class FunctionWrappedHolder implements ValueHolder {

  private final String functionName;
  private final ValueHolder holder;

  public FunctionWrappedHolder(final String functionName, final ValueHolder holder) {
    this.functionName = functionName;
    this.holder = holder;
  }


  @Override
  public String getSql() {
    return String.format("%s(%s)", this.functionName, this.holder.getSql());
  }

  @Override
  public Object getValue() {
    return this.holder.getValue();
  }

  @Override
  public ValueHolder apply(final String functionName) {
    return new FunctionWrappedHolder(functionName, this);
  }
}
