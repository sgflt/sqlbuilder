/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.api

interface Appendable {
    /**
     * Appends this entity to predefined builder.
     *
     * @param builder to be filled with this entity
     */
    fun appendTo(builder: StringBuilder)
}