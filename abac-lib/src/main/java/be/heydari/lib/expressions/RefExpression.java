package be.heydari.lib.expressions;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@Data
@NoArgsConstructor
public class RefExpression{//<T> {
    private String table;
    private String column;
    //private T entityId; // TODO: in the visitor it's only Double atm.

}