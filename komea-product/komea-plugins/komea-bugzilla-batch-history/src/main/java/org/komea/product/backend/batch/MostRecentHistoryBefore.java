/**
 *
 */

package org.komea.product.backend.batch;



import java.util.Comparator;

import org.komea.product.database.dto.BugHistory;



/**
 * @author sleroy
 */
final class MostRecentHistoryBefore implements Comparator<BugHistory>
{


    @Override
    public int compare(final BugHistory _arg0, final BugHistory _arg1) {


        if (_arg0.getWhen().equals(_arg1.getWhen())) {
            return 0;
        }
        if (_arg0.getWhen().isAfter(_arg0.getWhen())) {
            return -1;
        } else {
            return 1;
        }

    }
}
