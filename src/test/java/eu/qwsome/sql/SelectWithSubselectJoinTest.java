package eu.qwsome.sql;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author Lukáš Kvídera
 */
class SelectWithSubselectJoinTest {


  @Test
  void testSimpleSelectWithJoin() {
    final String sql = select().from("table")
        .join(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("zaza")))),
            "huhu"
        )
        .on(comparedField(column("x")).isEqualTo(column("y")))
        .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON x = y");
  }


  @Test
  void testSimpleSelectWithMultipleJoins() {
    final var select = select().from("table")
        .join(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("zaza")))),
            "huhu"
        ).on(comparedField(column("x")).isEqualTo(column("y")))
        .join(
            SubselectValueHolder.subselect(select("y").from("anothersubtable")
                .where(comparedField(column("x")).isEqualTo(column("zaza")))),
            "huhua"
        ).on(comparedField(column("a")).isEqualTo(column("b")));

    assertThat(select.toSql()).isEqualTo(
        "SELECT * FROM table JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON x = y"
        + " JOIN (SELECT y FROM anothersubtable WHERE x = zaza) huhua ON a = b");
    assertThat(select.toValues()).containsExactly("zaza");
  }


  @Test
  void testSimpleSelectWithLeftJoin() {
    final var select = select().from("table")
        .leftJoin(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("zaza")))),
            "huhu"
        ).on(comparedField(column("x")).isEqualTo(column("y")));

    assertThat(select.toSql()).isEqualTo("SELECT * FROM table LEFT JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON x = y");
    assertThat(select.toValues()).containsExactly("zaza");
  }


  @Test
  void testSimpleSelectWithMultipleLeftJoins() {
    final var select = select().from("table")
        .leftJoin(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("zaza")))),
            "huhu"
        ).on(comparedField(column("x")).isEqualTo(column("y")))
        .leftJoin(
            SubselectValueHolder.subselect(select("x").from("anothersubtable")
                .where(comparedField(column("x")).isEqualTo(value("gaga")))),
            "huhua"
        ).on(comparedField(column("a")).isEqualTo(value("b")));

    assertThat(select.toSql()).isEqualTo(
        "SELECT * FROM table LEFT JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON x = y"
        + " LEFT JOIN (SELECT x FROM anothersubtable WHERE x = ?) huhua ON a = ?");
    assertThat(select.toValues()).containsExactly("zaza", "gaga", "b");
  }


  @Test
  void testSimpleSelectWithMultipleJoinsCombined1() {
    final var select = select().from("table")
        .leftJoin(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("gaga")))),
            "huhu"
        ).on(comparedField(column("x")).isEqualTo(column("y")))
        .join("anothertable").on(comparedField(column("a")).isEqualTo(column("b")));

    assertThat(select.toSql()).isEqualTo(
        "SELECT * FROM table LEFT JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON x = y JOIN anothertable ON a = b");
    assertThat(select.toValues()).containsExactly("gaga");
  }


  @Test
  void testSimpleSelectWithMultipleJoinsCombined2() {
    final var select = select().from("table")
        .join(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("gaga")))),
            "huhu"
        )
        .on(comparedField(column("x")).isEqualTo(column("y")))
        .leftJoin("anothertable").on(comparedField(column("a")).isEqualTo(column("b")));

    assertThat(select.toSql()).isEqualTo(
        "SELECT * FROM table JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON x = y"
        + " LEFT JOIN anothertable ON a = b");
    assertThat(select.toValues()).containsExactly("gaga");
  }


  @Test
  void testSimpleSelectWithValueConditionInJoin() {
    final var select = select().from("table")
        .join(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("gaga")))),
            "huhu"
        ).on(comparedField(column("huhu", "x")).isEqualTo(column("y")))
        .leftJoin(
            SubselectValueHolder.subselect(select("x").from("anothersubtable")
                .where(comparedField(column("huhu", "x")).isEqualTo(value("sasa")))),
            "huhua"
        ).on(comparedField(column("a")).isEqualTo(value("b")));

    final String sql = select.toSql();
    assertThat(sql).isEqualTo(
        "SELECT * FROM table JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON huhu.x = y"
        + " LEFT JOIN (SELECT x FROM anothersubtable WHERE huhu.x = ?) huhua ON a = ?");

    assertThat(select.toValues()).containsExactly("gaga", "sasa", "b");
  }


  @Test
  void testWhereSelectWithValueConditionInJoin() {
    final var select = select().from("table")
        .join(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("gaga")))),
            "huhu"
        ).on(comparedField(column("huhu", "x")).isEqualTo(column("y")))
        .leftJoin(
            SubselectValueHolder.subselect(select("x").from("anothersubtable")
                .where(comparedField(column("x")).isEqualTo(value("dafa")))),
            "huhua"
        ).on(comparedField(column("a")).isEqualTo(value("b")))
        .where(comparedField(column("d")).isEqualTo(value("e")));

    final String sql = select.toSql();
    assertThat(sql).isEqualTo(
        "SELECT * FROM table JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON huhu.x = y"
        + " LEFT JOIN (SELECT x FROM anothersubtable WHERE x = ?) huhua ON a = ? WHERE d = ?");

    assertThat(select.toValues()).containsExactly("gaga", "dafa", "b", "e");
  }


  @Test
  void testNullCondition() {
    final ValueBinding binding = select()
        .from("table")
        .join(
            SubselectValueHolder.subselect(select("x").from("subtable")
                .where(comparedField(column("x")).isEqualTo(value("gaga")))),
            "huhu"
        ).on(comparedField(column("huhu", "x")).isEqualTo(column("y")))
        .where(null);

    final String sql = binding.toSql();

    assertEquals(
        sql,
        "SELECT * FROM table JOIN (SELECT x FROM subtable WHERE x = ?) huhu ON huhu.x = y"
    );

    assertThat(binding.toValues()).containsExactly("gaga");
  }
}
