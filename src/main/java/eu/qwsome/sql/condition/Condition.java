package eu.qwsome.sql.condition;



/**
 * @author Lukáš Kvídera
 */
public interface Condition {
  CharSequence get();

  default Condition and(final Condition another) {
    return new And(this, another);
  }

  default Condition or(final Condition another) {
    return new Or(this, another);
  }
}
