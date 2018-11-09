package eu.qwsome.sql.builder;

import org.junit.jupiter.api.Test;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lukáš Kvídera
 */
class ConditionBuilderTest {

  @Test
  void create() {
    assertNotNull(ConditionBuilder.create(), "factory method returned null");
  }

  @Test
  void and() {
    final ConditionBuilder builder = ConditionBuilder.create()
      .and(comparedField(value(4)).isEqualTo(column("c1")));

    assertThat(builder.get()).isEqualTo("? = c1");
  }

  @Test
  void and_null() {
    assertThrows(NullPointerException.class, () -> ConditionBuilder.create().and(null));
  }

  @Test
  void and_twice() {
    final ConditionBuilder builder = ConditionBuilder.create()
      .and(comparedField(value(4)).isEqualTo(column("c1")))
      .and(comparedField(column("b2")).isEqualTo(column("c2")));

    assertThat(builder.get()).isEqualTo("(? = c1 AND b2 = c2)");
  }

  @Test
  void or() {
    final ConditionBuilder builder = ConditionBuilder.create()
      .or(comparedField(value(4)).isEqualTo(column("c1")));

    assertThat(builder.get()).isEqualTo("? = c1");
  }

  @Test
  void or_null() {
    assertThrows(NullPointerException.class, () -> ConditionBuilder.create().or(null));
  }

  @Test
  void or_twice() {
    final ConditionBuilder builder = ConditionBuilder.create()
      .or(comparedField(value(4)).isEqualTo(column("c1")))
      .or(comparedField(column("b2")).isEqualTo(column("c2")));

    assertThat(builder.get()).isEqualTo("(? = c1 OR b2 = c2)");
  }


  @Test
  void orAnd_twice() {
    final ConditionBuilder builder = ConditionBuilder.create()
      .or(comparedField(value(4)).isEqualTo(column("c1")))
      .and(comparedField(column("b2")).isEqualTo(column("c2")))
      .and(comparedField(column("b3")).isEqualTo(column("c3")))
      .or(comparedField(column("b4")).isEqualTo(column("c4")));

    assertThat(builder.get()).isEqualTo("(((? = c1 AND b2 = c2) AND b3 = c3) OR b4 = c4)");
  }

  @Test
  void getValues() {
    final ConditionBuilder builder = ConditionBuilder.create()
      .or(comparedField(value(4)).isEqualTo(column("c1")))
      .and(comparedField(column("b2")).isEqualTo(column("c2")))
      .and(comparedField(column("b3")).isEqualTo(column("c3")))
      .or(comparedField(column("b4")).isEqualTo(column("c4")));

    assertArrayEquals(builder.getValues().toArray(), new Object[]{4});
  }
}