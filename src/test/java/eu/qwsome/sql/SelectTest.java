package eu.qwsome.sql;

import org.junit.jupiter.api.Test;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectTest {

  @Test
  public void testSimpleSelect() {
    final String sql = select().from("table").toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table");
  }

  @Test
  public void testSelectWithColumn() {
    final String sql = select("column1").from("table").toSql();
    assertThat(sql).isEqualTo("SELECT column1 FROM table");
  }

  @Test
  public void testSelectWithColumns() {
    final String sql = select("column1", "column2").from("table").toSql();
    assertThat(sql).isEqualTo("SELECT column1,column2 FROM table");
  }

  @Test
  public void testSimpleSelect_SingleCondition() {
    final String sql = select().from("table").where(column("column1").isEqualTo(column("column2"))).toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 = column2");
  }

  @Test
  public void testWithColumn_SingleCondition() {
    final String sql = select("column42").from("table").where(column("column1").isEqualTo(column("column2"))).toSql();
    assertThat(sql).isEqualTo("SELECT column42 FROM table WHERE column1 = column2");
  }

  @Test
  public void testSelectWithColumns_SingleCondition() {
    final String sql = select("column42", "column49").from("table")
      .where(column("column1").isEqualTo(column("column2"))).toSql();

    assertThat(sql).isEqualTo("SELECT column42,column49 FROM table WHERE column1 = column2");
  }

  @Test
  public void testSimpleSelect_MultipleConditions() {
    final String sql = select().from("table")
      .where(
        column("column1").isEqualTo(column("column2"))
          .and(column("column4").isEqualTo(column("column5")))
      ).toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE (column1 = column2 AND column4 = column5)");
  }

  @Test
  public void testWithColumn_MultipleConditions() {
    final String sql = select("column42").from("table")
      .where(
        column("column1").isEqualTo(column("column2"))
          .and(column("column4").isEqualTo(column("column5")))
      ).toSql();

    assertThat(sql).isEqualTo("SELECT column42 FROM table WHERE (column1 = column2 AND column4 = column5)");
  }

  @Test
  public void testSelectWithColumns_MultipleConditions() {
    final String sql = select("column42", "column49").from("table")
      .where(
        column("column1").isEqualTo(column("column2"))
          .and(column("column4").isEqualTo(column("column5")))
      ).toSql();

    assertThat(sql).isEqualTo("SELECT column42,column49 FROM table WHERE (column1 = column2 AND column4 = column5)");
  }

  @Test
  public void testSimpleSelect_Between() {
    final String sql = select().from("table")
      .where(column("x").isBetween(column("from"), column("to")))
      .toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE x BETWEEN from AND to");
  }

  @Test
  public void testSimpleSelect_BetweenOrSomethingElse() {
    final String sql = select().from("table")
      .where(
        column("x").isBetween(column("from"), column("to"))
          .or(column("y").isNull())
      )
      .toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE (x BETWEEN from AND to OR y IS NULL)");
  }

  @Test
  public void testAllVariants() {
    final String sql = select().from("table")
      .where(
        column("y1").isNull()
          .and(column("x").isBetween(column("from"), column("to")))
          .or(column("y").isNull())
          .or(
            column("a").isNotNull()
            .and(column("b").isLessThan(column("x")))
          )
      )
      .toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE (((y1 IS NULL AND x BETWEEN from AND to) OR y IS NULL) OR (a IS NOT NULL AND b < x))");
  }
}
