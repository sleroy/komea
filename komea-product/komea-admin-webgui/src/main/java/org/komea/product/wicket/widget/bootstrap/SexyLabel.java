
package org.komea.product.wicket.widget.bootstrap;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.string.Strings;



public class SexyLabel extends Label
{
    
    
    /**
     * New Danger Label.
     * 
     * @param _id
     *            the id
     * @param _model
     *            the model
     * @return the danger label.
     */
    public static SexyLabel newDangerLabel(final String _id, final String _model) {
    
    
        return new SexyLabel(_id, _model, "bg-danger");
        
        
    }
    
    
    /**
     * New Info Label.
     * 
     * @param _id
     *            the id
     * @param _model
     *            the model
     * @return the danger label.
     */
    public static SexyLabel newInfoLabel(final String _id, final String _model) {
    
    
        return new SexyLabel(_id, _model, "bg-info");
        
        
    }
    
    
    /**
     * New Info Label.
     * 
     * @param _id
     *            the id
     * @param _model
     *            the model
     * @return the danger label.
     */
    public static SexyLabel newPrimaryLabel(final String _id, final String _model) {
    
    
        return new SexyLabel(_id, _model, "bg-primary");
        
        
    }
    
    
    /**
     * New Info Label.
     * 
     * @param _id
     *            the id
     * @param _model
     *            the model
     * @return the danger label.
     */
    public static SexyLabel newSuccessLabel(final String _id, final String _model) {
    
    
        return new SexyLabel(_id, _model, "bg-success");
        
        
    }
    
    
    /**
     * New Info Label.
     * 
     * @param _id
     *            the id
     * @param _model
     *            the model
     * @return the danger label.
     */
    public static SexyLabel newWarningLabel(final String _id, final String _model) {
    
    
        return new SexyLabel(_id, _model, "bg-warning");
        
        
    }
    
    
    private SexyLabel(final String _id, final String _model, final String _class) {
    
    
        super(_id, "<p class=\"" + _class + "\">" + Strings.escapeMarkup(_model) + "</p>");
        setEscapeModelStrings(false);
        
        
    }
}
