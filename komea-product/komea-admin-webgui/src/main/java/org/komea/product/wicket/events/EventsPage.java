
package org.komea.product.wicket.events;



import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.wicket.LayoutPage;
import org.ocpsoft.prettytime.PrettyTime;



/**
 * Events page
 * 
 * @author sleroy
 */
public class EventsPage extends LayoutPage
{
    
    
    @SpringBean
    private IEventViewerService service;
    
    
    
    public EventsPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        final List<IEvent> hourEvents = service.getHourEvents();
        
        final ListView<IEvent> listView = new ListView<IEvent>("events", hourEvents)
        {
            
            
            @Override
            protected void populateItem(final ListItem<IEvent> _item) {
            
            
                final IModel<IEvent> model = _item.getModel();
                
                _item.add(new Label("project"));
                _item.add(new Label("severity"));
                _item.add(new Label("icon"));
                _item.add(new Label("message"));
                final PrettyTime prettyTime = new PrettyTime();
                _item.add(new Label("date", prettyTime.format(model.getObject().getDate())));
                _item.setOutputMarkupId(true);
            }
        };
        // listView.setReuseItems(true);
        
        add(listView);
        
    }
    
    
    public IEventViewerService getService() {
    
    
        return service;
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "Events received by Komea";
    }
    
    
    public void setService(final IEventViewerService _service) {
    
    
        service = _service;
    }
    
    
}
