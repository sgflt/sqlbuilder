# sqlbuilder
is a leightweight library that simplifies generating of SQL/JPQL/HQL.

In opposite to jOOQ or DSLQuery this implementation is not dialect aware.
What you write is what you get. There is no need to generate definitons of tables or similar things.

Just add dependency and use.

# Usage

select("column42", "column49").from("table")
      .where(
        comparedField(column("column1")).isEqualTo(column("column2"))
          .and(comparedField(column("column4")).isEqualTo(column("column5")))
      ).toSql();
      
produces:
SELECT column42,column49 FROM table WHERE (column1 = column2 AND column4 = column5)

# Compatibility

sqlbuilder is ready for use with raw PreparedStatement, JdbcTemplate or your custom implementation of database access.
