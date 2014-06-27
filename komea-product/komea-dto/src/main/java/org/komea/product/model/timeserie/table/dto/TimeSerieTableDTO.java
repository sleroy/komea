/**
 *
 */

package org.komea.product.model.timeserie.table.dto;



import java.util.Collections;
import java.util.List;



/**
 * @author sleroy
 */
public class TimeSerieTableDTO
{


    private List<TimeSerieSimpleDTO> items = Collections.emptyList();
    
    
    
    /**
     * Returns the value of the field items.
     *
     * @return the items
     */
    public List<TimeSerieSimpleDTO> getItems() {
    
    
        return items;
    }
    
    
    /**
     * Sets the field items with the value of _items.
     *
     * @param _items
     *            the items to set
     */
    public void setItems(final List<TimeSerieSimpleDTO> _items) {


        items = _items;
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "TimeSerieTableDTO [\\n\\titems=" + items + "]";
    }


}
