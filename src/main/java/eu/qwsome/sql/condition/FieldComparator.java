package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public class FieldComparator {

  private FieldComparator() {
    // just a factory
  }

  public static ComparableField comparedField(final ValueHolder value) {
    return new ComparableFieldImpl(value);
  }
}
