package org.komea.product.wicket.events;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.ocpsoft.prettytime.PrettyTime;

import com.google.common.base.Strings;
import org.komea.product.wicket.widget.builders.DataTableBuilder;

/**
 * Events page
 *
 * @author sleroy
 */
public class EventsPage extends LayoutPage {

    private static final class EventTable extends ListView<IEvent> {

        private EventTable(final String _id, final IModel<? extends List<? extends IEvent>> _model) {

            super(_id, _model);
        }

        @Override
        protected void populateItem(final ListItem<IEvent> _item) {

            final IModel<IEvent> model = _item.getModel();
            final IEvent event = model.getObject();

            final Project project = event.getProject();
            if (project != null) {
                _item.add(new Label("project", Strings.nullToEmpty(project.getName())));
            } else {
                _item.add(new Label("project", ""));
            }
            final Link<String> link = new Link<String>("url") {

                @Override
                public void onClick() {

                    setResponsePage(new RedirectPage(event.getUrl()));
                }

            };
            _item.add(link);
            link.add(new Label("message", event.getMessage()));
            _item.add(new Label("severity", event.getEventType().getName()));

            _item.add(new Label("icon", event.getProvider().getIcon()));

            final PrettyTime prettyTime = new PrettyTime();
            _item.add(new Label("date", prettyTime.format(event.getDate())));
            _item.setOutputMarkupId(true);
            final Person persons = event.getPerson();
            _item.add(new UserList("users", Collections.singletonList(persons)));
            if (event.getPersonGroup() != null) {
                _item.add(new Label("group", event.getPersonGroup().getName()));

            } else {
                _item.add(new Label("group", ""));
            }
        }
    }

    private static final class UserList extends ListView<Person> {

        private UserList(final String _id, final List<Person> _persons) {

            super(_id, _persons);
        }

        @Override
        protected void populateItem(final ListItem<Person> _item) {

            final IModel<Person> model = _item.getModel();
            _item.setDefaultModel(new CompoundPropertyModel<Person>(model.getObject()));
            _item.add(new Label("useritem", "login"));

        }

    }

    @SpringBean
    private IEventViewerService service;

    public EventsPage(final PageParameters _parameters) {

        super(_parameters);
        final List<IEvent> hourEvents = service.getGlobalActivity();
        List<IEvent> subList = hourEvents;
        if (!hourEvents.isEmpty()) {
            subList = hourEvents.subList(0, Math.min(hourEvents.size(), 100));
        }
        final ListView<IEvent> listView
                = new EventTable("events", new CompoundPropertyModel<List<IEvent>>(subList));
        // listView.setReuseItems(true);

        add(listView);

    }

    @Override
    public String getTitle() {
        return getString("administration.title.events");
    }

    public IEventViewerService getService() {

        return service;
    }

    public void setService(final IEventViewerService _service) {

        service = _service;
    }

}
