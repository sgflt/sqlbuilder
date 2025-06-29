/*
 * @author Martin Procházka
 */
package eu.qwsome.sql

interface Query {
    /**
     * Transforms the Query to (hopefully) interpretable SQL code.
     *
     * @return SQL code as a String
     */
    fun toSql(): String
}