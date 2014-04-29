
package org.komea.eventory.cache.guava;


public class Django {
    
    private final String name;
    
    public Django(final String _name) {
    
        super();
        name = _name;
    }
    
    public String getName() {
    
        return name;
    }
    
    @Override
    public String toString() {
    
        return "Django [name=" + name + "]";
    }
    
}
