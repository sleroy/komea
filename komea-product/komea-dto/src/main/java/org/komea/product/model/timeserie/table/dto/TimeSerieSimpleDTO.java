
package org.komea.product.model.timeserie.table.dto;



import java.io.Serializable;
import java.util.List;

import org.komea.product.service.dto.EntityKey;

import com.google.common.collect.Lists;



public class TimeSerieSimpleDTO implements Serializable
{


    private List<PointDTO> coordinates = Lists.newArrayList();
    private EntityKey      entityKey;

    private String         kpiName;
    
    
    
    /**
     * Returns the value of the field coordinates.
     *
     * @return the coordinates
     */
    public List<PointDTO> getCoordinates() {


        return coordinates;
    }
    
    
    /**
     * Returns the value of the field entityKey.
     *
     * @return the entityKey
     */
    public EntityKey getEntityKey() {


        return entityKey;
    }
    
    
    /**
     * Returns the value of the field kpiName.
     *
     * @return the kpiName
     */
    public String getKpiName() {


        return kpiName;
    }
    
    
    /**
     * Sets the field coordinates with the value of _coordinates.
     *
     * @param _coordinates
     *            the coordinates to set
     */
    public void setCoordinates(final List<PointDTO> _coordinates) {


        coordinates = _coordinates;
    }
    
    
    /**
     * Sets the field entityKey with the value of _entityKey.
     *
     * @param _entityKey
     *            the entityKey to set
     */
    public void setEntityKey(final EntityKey _entityKey) {


        entityKey = _entityKey;
    }
    
    
    /**
     * Sets the field kpiName with the value of _kpiName.
     *
     * @param _kpiName
     *            the kpiName to set
     */
    public void setKpiName(final String _kpiName) {


        kpiName = _kpiName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "TimeSerieSimpleDTO [\\n\\tcoordinates="
                + coordinates + ", \\n\\tentityKey=" + entityKey + ", \\n\\tkpiName=" + kpiName
                + "]";
    }
    
    
}
