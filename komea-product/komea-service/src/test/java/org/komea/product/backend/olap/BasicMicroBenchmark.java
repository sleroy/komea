
package org.komea.product.backend.olap;



import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class BasicMicroBenchmark
{
    
    
    public long                         endTime;
    
    
    public DescriptiveStatistics        execTime          = new DescriptiveStatistics();
    
    
    public DescriptiveStatistics        gcTime            = new DescriptiveStatistics();
    
    
    public long                         startTime;
    
    
    public DescriptiveStatistics        warmTime          = new DescriptiveStatistics();
    
    
    private final Clock                 clock;
    
    
    private final Interval              gcInterval;
    
    
    private final Interval              interval;
    
    
    private Logger                      LOGGER            = LoggerFactory
                                                                  .getLogger("micro-benchmark");
    
    
    private final DescriptiveStatistics memoryConsumption = new DescriptiveStatistics();
    
    
    private final DescriptiveStatistics memoryFree        = new DescriptiveStatistics();
    
    
    private boolean                     needGC            = true;
    
    
    private int                         tries             = 30;
    
    
    private int                         warmupTries       = 5;
    
    
    
    public BasicMicroBenchmark() {
    
    
        clock = Clock.CPU_TIME;
        interval = new Interval(clock);
        gcInterval = new Interval(clock);
    }
    
    
    public BasicMicroBenchmark(final Clock _clock) {
    
    
        clock = _clock;
        interval = new Interval(clock);
        gcInterval = new Interval(clock);
    }
    
    
    public int getTries() {
    
    
        return tries;
    }
    
    
    public int getWarmupTries() {
    
    
        return warmupTries;
    }
    
    
    public boolean isNeedGC() {
    
    
        return needGC;
    }
    
    
    public void run(final Runnable _runnable) {
    
    
        launchGC();
        startTime = clock.time();
        
        for (int i = 0; i < warmupTries; ++i) {
            interval.start();
            launchOnce(_runnable);
            warmTime.addValue(interval.stop());
        }
        
        for (int i = 0; i < tries; ++i) {
            interval.start();
            launchOnce(_runnable);
            execTime.addValue(interval.stop());
        }
        
        endTime = clock.time();
        
        LOGGER.info(
                "Total time={}ms\tGC={}ms\twarmUp/1={}ms\twarmupSum={}ms\texec/1={}ms\texecSum={}ms \tfreeM={}ms\ttotalM={}ms",
                endTime - startTime, gcTime.getMean(), warmTime.getMean(), warmTime.getSum(),
                execTime.getMean(), execTime.getSum(), memoryFree.getMean(),
                memoryConsumption.getMean());
        
    }
    
    
    public void setNeedGC(final boolean _needGC) {
    
    
        needGC = _needGC;
    }
    
    
    public void setTestName(final String _testName) {
    
    
        LOGGER = LoggerFactory.getLogger("micro-benchmark-" + _testName);
    }
    
    
    public void setTries(final int _tries) {
    
    
        tries = _tries;
    }
    
    
    public void setWarmupTries(final int _warmupTries) {
    
    
        warmupTries = _warmupTries;
    }
    
    
    private void launchGC() {
    
    
        gcInterval.start();
        System.gc();
        gcTime.addValue(gcInterval.stop());
    }
    
    
    private void launchOnce(final Runnable _runnable) {
    
    
        if (needGC) {
            launchGC();
        }
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long freeMemory = Runtime.getRuntime().freeMemory();
        _runnable.run();
        final long totalMemory2 = Runtime.getRuntime().totalMemory();
        final long freeMemory2 = Runtime.getRuntime().freeMemory();
        memoryConsumption.addValue((totalMemory2 - totalMemory) / 1000.0);
        memoryFree.addValue((freeMemory2 - freeMemory) / 1000.0);
        
    }
}
