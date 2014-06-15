/**
 *
 */

package org.komea.product.backend.utils;



import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class ThreadUtils
{


    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtils.class);



    /**
     * Creates a thread with a task.
     *
     * @param _task
     * @param _runnable
     * @return
     */
    public static Thread createThread(final String _task, final Runnable _runnable) {


        Validate.notNull(_task);
        Validate.notNull(_runnable);
        LOGGER.info("Executing the {} task", _task);
        final Thread thread = new Thread(_runnable);
        thread.setName(_task);

        return thread;

    }


    /**
     * Launchs a thread async.
     *
     * @param _task
     * @param _runnable
     */
    public static void execThreadAsync(final String _task, final Runnable _runnable) {


        createThread(_task, _runnable).start();

    }

}
