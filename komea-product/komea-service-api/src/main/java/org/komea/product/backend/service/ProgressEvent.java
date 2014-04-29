/**
 * 
 */

package org.komea.product.backend.service;



/**
 * @author sleroy
 */
public class ProgressEvent
{
    
    
    private String eventMessage = "";
    
    private int    expected     = 0;
    
    private int    progress     = 0;
    
    
    
    public ProgressEvent() {
    
    
        super();
    }
    
    
    public ProgressEvent(final String _eventMessage, final int _expected, final int _progress) {
    
    
        super();
        eventMessage = _eventMessage;
        expected = _expected;
        progress = _progress;
    }
    
    
    public String getEventMessage() {
    
    
        return eventMessage;
    }
    
    
    public int getExpected() {
    
    
        return expected;
    }
    
    
    public int getProgress() {
    
    
        return progress;
    }
    
    
    public void setEventMessage(final String _eventMessage) {
    
    
        eventMessage = _eventMessage;
    }
    
    
    public void setExpected(final int _expected) {
    
    
        expected = _expected;
    }
    
    
    public void setProgress(final int _progress) {
    
    
        progress = _progress;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "ProgressEvent [eventMessage="
                + eventMessage + ", expected=" + expected + ", progress=" + progress + "]";
    }
}
