package eu.qwsome.sql.builder;


import eu.qwsome.sql.condition.Condition;

import java.util.Objects;

/**
 * This builder uses left associativity when appendeing conditions.
 *
 * @author Lukáš Kvídera
 */
public class ConditionBuilder {

  private Condition root;

  /**
   * Creates a new instance of {@link ConditionBuilder}
   *
   * @return {@link ConditionBuilder}
   */
  public static ConditionBuilder create() {
    return new ConditionBuilder();
  }

  /**
   * Appends another condition to current context with AND if applicable.
   *
   * @param another condition to be composed
   * @return this builder
   */
  public ConditionBuilder and(final Condition another) {
    Objects.requireNonNull(another, "provide another condition");
    this.root = this.root == null ? another : this.root.and(another);
    return this;
  }

  /**
   * Appends another condition to current context with OR if applicable.
   *
   * @param another condition to be composed
   * @return this builder
   */
  public ConditionBuilder or(final Condition another) {
    Objects.requireNonNull(another, "provide another condition");
    this.root = this.root == null ? another : this.root.or(another);
    return this;
  }

  /**
   * @return condition or null if empty
   */
  public Condition build() {
    return this.root;
  }
}
