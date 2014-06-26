
package org.komea.product.plugins.bugzilla.core;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j2bugzilla.base.BugzillaMethod;



/**
 *
 */
public class GetHistory implements BugzillaMethod
{


    /**
     * The method name for this {@link BugzillaMethod}
     */
    private static final String       GET_HISTORY = "Bug.history";                //$NON-NLS-1$
    private Map<Object, Object>       hash        = new HashMap<Object, Object>();
    private final Map<Object, Object> params      = new HashMap<Object, Object>();



    public GetHistory() {


    }


    public GetHistory(final int id) {


        params.put("ids", id);
    }


    /*
     * (non-Javadoc)
     * @see com.j2bugzilla.base.BugzillaMethod#getMethodName()
     */
    @Override
    public String getMethodName() {


        return GET_HISTORY;
    }


    /**
     * Get the list of history
     * This history has only "changed" events.
     */
    public Date[] getModifiedTimestamps() {


        final List<Date> result = new ArrayList<Date>();
        if (hash.containsKey("bugs")) { //$NON-NLS-1$
            final Object[] bugs = (Object[]) hash.get("bugs"); //$NON-NLS-1$
            if (bugs.length == 0) {
                return new Date[0]; // early return if map is empty
            }
            for (final Object o : bugs) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> bugMap = (HashMap<String, Object>) o;
                if (bugMap.containsKey("history")) { //$NON-NLS-1$
                    final Object[] histories = (Object[]) bugMap.get("history"); //$NON-NLS-1$
                    for (final Object h : histories) {
                        @SuppressWarnings("unchecked")
                        final Map<String, Object> historyMap = (Map<String, Object>) h;
                        if (historyMap.containsKey("when")) { //$NON-NLS-1$
                            result.add((Date) historyMap.get("when")); //$NON-NLS-1$
                        }
                    }
                }
            }
        }
        final Date[] copyresult = new Date[result.size()];
        return result.toArray(copyresult);
    }


    /*
     * (non-Javadoc)
     * @see com.j2bugzilla.base.BugzillaMethod#getParameterMap()
     */
    @Override
    public Map<Object, Object> getParameterMap() {


        return Collections.unmodifiableMap(params);
    }


    /*
     * (non-Javadoc)
     * @see com.j2bugzilla.base.BugzillaMethod#setResultMap(java.util.Map)
     */
    @Override
    public void setResultMap(final Map<Object, Object> hash) {


        this.hash = hash;
    }
}
