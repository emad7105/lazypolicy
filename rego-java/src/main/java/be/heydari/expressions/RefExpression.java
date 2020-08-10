package be.heydari.expressions;

import lombok.Data;

/**
 * Reference expressions describe the entity's contextual query
 * information such as:
 *  - table name
 *  - column name
 *  - entity id
 *  - etc.
 *
 *  Note: depending on different queries, entityId can be null.
 *
 * Example:
 *  accountState[25].brokerId
 *  table: accountState
 *  column: brokerId
 *  entity id (or row id / primary key): 25
 *
 * @author Emad Heydari Beni
 */
@Data
public class RefExpression<T> {
    private String table;
    private String column;
    private T entityId; // TODO: in the visitor it's only Double atm.

}
