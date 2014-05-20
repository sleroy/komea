
package org.komea.product.backend.olap;



import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;



/**
 * Used to specify what time to measure in {@link BenchmarkOptions}.
 */
public enum Clock {
    
    /**
     * Invokes {@link java.lang.management.ThreadMXBean#getCurrentThreadCpuTime()}
     */
    CPU_TIME {
        
        
        @Override
        public long time() {
        
        
            if (mxBean.isThreadCpuTimeSupported()) {
                return mxBean.getCurrentThreadCpuTime() / FACTOR;
            } else {
                throw new RuntimeException(
                        "ThreadCpuTime is not supported. Impossible to use Clock.CPU_TIME");
            }
        }
    },
    
    /**
     * Invokes {@link System#nanoTime()}
     */
    NANO_TIME {
        
        
        @Override
        public long time() {
        
        
            return System.nanoTime() / FACTOR;
        }
    },
    
    /**
     * Invokes {@link System#currentTimeMillis()}
     */
    REAL_TIME {
        
        
        @Override
        public long time() {
        
        
            return System.currentTimeMillis();
        }
    },
    /**
     * Invokes {@link java.lang.management.ThreadMXBean#getCurrentThreadUserTime()}
     */
    USER_TIME {
        
        
        @Override
        public long time() {
        
        
            if (mxBean.isThreadCpuTimeSupported()) {
                return mxBean.getCurrentThreadUserTime() / FACTOR;
            } else {
                throw new RuntimeException(
                        "ThreadCpuTime is not supported. Impossible to use Clock.USER_TIME");
            }
        }
    };
    
    
    private static final int    FACTOR = 1000000;
    private static ThreadMXBean mxBean;
    
    static {
        mxBean = ManagementFactory.getThreadMXBean();
        if (mxBean.isThreadCpuTimeSupported()) {
            mxBean.setThreadCpuTimeEnabled(true);
        }
    }
    
    
    
    public abstract long time();
}
