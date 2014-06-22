
package org.komea.product.plugins.bugzilla.core;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.komea.product.backend.utils.PojoUtils;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.BugzillaMethod;



/**
 * Allows users to retrieve a specific bug for which the ID is already known
 *
 * @author Tom
 */
public class GetBugHistory implements BugzillaMethod
{


    /**
     * The method name for this {@link BugzillaMethod}
     */
    private static final String       METHOD_NAME = "Bug.history";

    private Map<Object, Object>       hash        = new HashMap<Object, Object>();
    private final Map<Object, Object> params      = new HashMap<Object, Object>();



    /**
     * Creates a new {@link GetBugHistory} object to retrieve the {@code Bug} specified
     * by the ID parameter
     *
     * @param id
     *            An {@code int} representing the ID of an existing bug in the
     *            installation connected to
     */
    public GetBugHistory(final int id) {


        params.put("ids", id);
    }


    /**
     * Creates a new {@link GetBugHistory} object to retrieve the {@code Bug} specified by the
     * unique alias.
     *
     * @param alias
     *            A {@code String} representing a bug alias in the Bugzilla installation.
     */
    public GetBugHistory(final String alias) {


        params.put("ids", alias);
    }


    /**
     * Gets bug history.
     */

    @SuppressWarnings("unchecked")
    public List<BugHistory> getBugHistory() {


        List<BugHistory> result = null;
        if (hash.containsKey("bugs")) {

            final Object[] bugs = (Object[]) hash.get("bugs");
            if (bugs.length == 0) {
                return result; // early return if map is empty
            }

            result = new ArrayList<BugHistory>();

            for (final Object bugHistoryDto : bugs) {

                final List<BugHistory> histItems =
                        convertIntoHistory((HashMap<String, Object>) bugHistoryDto);
                for (final BugHistory bugHistory : histItems) {
                    
                    result.add(bugHistory);
                }


            }
        }
        return result;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getMethodName() {


        return METHOD_NAME;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Object, Object> getParameterMap() {


        return Collections.unmodifiableMap(params);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setResultMap(final Map<Object, Object> hash) {


        this.hash = hash;
    }


    /**
     * @param _bugHistoryInformations
     * @return
     */
    private List<BugHistory> convertIntoHistory(final Map<String, Object> _bugHistoryInformations) {


        final Object[] maps = (Object[]) _bugHistoryInformations.get("history");
        final List<BugHistory> histories = Lists.newArrayList();
        for (final Object history0 : maps) {
            final Map curBugHistoryObjectMap = (Map) history0;
            final BugHistory bugHistory = new BugHistory();
            bugHistory.setWhen(new DateTime((Date) curBugHistoryObjectMap.get("when")));
            bugHistory.setWho((String) curBugHistoryObjectMap.get("who"));
            final ArrayList<BugChange> changes = Lists.newArrayList();
            final Object[] changeArray = (Object[]) curBugHistoryObjectMap.get("changes");
            for (final Object changeData : changeArray) {
                changes.add(PojoUtils.injectInPojo(BugChange.class, (Map) changeData));
            }
            bugHistory.setBugChanges(changes);
            histories.add(bugHistory);
        }
        return histories;
    }
}
