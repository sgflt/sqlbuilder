package eu.qwsome.sql;

import org.junit.jupiter.api.Test;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lukáš Kvídera
 */
public class SelectWithJoinTest {


  @Test
  public void testSimpleSelectWithJoin() {
    final String sql = select().from("table")
      .join("joinedtable").on(comparedField(column("x")).isEqualTo(column("y")))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table JOIN joinedtable ON x = y");
  }


  @Test
  public void testSimpleSelectWithMultipleJoins() {
    final String sql = select().from("table")
      .join("joinedtable").on(comparedField(column("x")).isEqualTo(column("y")))
      .join("anothertable").on(comparedField(column("a")).isEqualTo(column("b")))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table JOIN joinedtable ON x = y JOIN anothertable ON a = b");
  }

  @Test
  public void testSimpleSelectWithLeftJoin() {
    final String sql = select().from("table")
      .leftJoin("joinedtable").on(comparedField(column("x")).isEqualTo(column("y")))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table LEFT JOIN joinedtable ON x = y");
  }


  @Test
  public void testSimpleSelectWithMultipleLeftJoins() {
    final String sql = select().from("table")
      .leftJoin("joinedtable").on(comparedField(column("x")).isEqualTo(column("y")))
      .leftJoin("anothertable").on(comparedField(column("a")).isEqualTo(column("b")))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table LEFT JOIN joinedtable ON x = y LEFT JOIN anothertable ON a = b");
  }

  @Test
  public void testSimpleSelectWithMultipleJoinsCombined1() {
    final String sql = select().from("table")
      .leftJoin("joinedtable").on(comparedField(column("x")).isEqualTo(column("y")))
      .join("anothertable").on(comparedField(column("a")).isEqualTo(column("b")))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table LEFT JOIN joinedtable ON x = y JOIN anothertable ON a = b");
  }

  @Test
  public void testSimpleSelectWithMultipleJoinsCombined2() {
    final String sql = select().from("table")
      .join("joinedtable").on(comparedField(column("x")).isEqualTo(column("y")))
      .leftJoin("anothertable").on(comparedField(column("a")).isEqualTo(column("b")))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table JOIN joinedtable ON x = y LEFT JOIN anothertable ON a = b");
  }

}
