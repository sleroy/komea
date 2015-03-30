
package org.komea.core.schema.impl;



public class SchemaNamingConvention
{
    
    public static final String SEPARATOR = "_";
    public static final String TRUE      = "true";
    public static final String ONE   = "ONE";
    public static final String ARITY   = "arity";
    public static final String INDEXED   = "indexed";
    public static final String UNIQUE    = "unique";
    public static final String MANDATORY = "mandatory";
    public static final String TYPE      = "type";
    
    public static String formatEdgeType(final EdgeDefinition definition) {
    
        return definition.getType() + SEPARATOR + definition.getName();
    }
    
    public static EdgeDefinition extractEdgeDefinition(final String value) throws NamingConventionException {
    
        String[] fragments = value.split(SEPARATOR);
        if (fragments.length != 3) {
            throw new NamingConventionException("Illegal edge naming : " + value + ". Exepecting 3 fragments separated by '" + SEPARATOR
                    + "'");
        }
        return new EdgeDefinition(fragments[1], fragments[2]);
    }
    
    public final static class EdgeDefinition
    {
        
        private final String type;
        private final String name;
        
        public EdgeDefinition(final String type, final String name) {
        
            super();
            this.type = type;
            this.name = name;
        }
        
        public String getName() {
        
            return this.name;
        }
        
        public String getType() {
        
            return this.type;
        }
    }
}
