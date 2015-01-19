/**
 *
 */
package org.komea.event.queries.factory;

/**
 * @author sleroy
 */
public enum Impl {

    H2_MEM_JACKSON, H2_MEM_KRYO, H2_DISK_JACKSON, H2_DISK_KRYO, MYSQL_JACKSON, MYSQL_KRYO, ORIENTDB_MEM, ORIENTDB_DISK;

}
