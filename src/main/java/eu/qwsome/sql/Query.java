package eu.qwsome.sql;

/**
 * @author Martin Procházka
 */
public interface Query {

    /**
     * Transforms the Query to (hopefully) interpretable SQL code.
     *
     * @return SQL code as a String
     */
    String toSql();
}
