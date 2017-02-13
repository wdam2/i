package salasa.controller;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import salasa.model.Event;
import salasa.model.User;
import salasa.service.EventService;

@SessionScoped
@Named
public class CalendarController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EventService eventService;
	
	private ScheduleModel eventModel;
	private Event event;

	@PostConstruct
	public void init() {
		if (event == null) {
			event = new Event();
			eventModel = null;
		}
	}

	public ScheduleModel getEventModel() {
		if (eventModel == null) {
			eventModel = new LazyScheduleModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public void loadEvents(Date start, Date end) {
					// TODO Auto-generated method stub
					super.loadEvents(start, end);
					clear();
					User user = getLoggedInUser();
					List<Event> events = user.getRole().equals("CLIENT")
							? eventService.findUserAndFreeEvents(start, end, user)
							: eventService.findAllEvents();
					for (Event event : events) {
						String title = event.getTitle();
						Date startDate = event.getStartDate();
						Date endDate = event.getEndDate();
						String description = event.getDescription();
						String styleClass = event.getCreatedFor() == null ? "free" : "busy";

						DefaultScheduleEvent defaultScheduleEvent = new DefaultScheduleEvent(title, startDate, endDate, event);
						defaultScheduleEvent.setDescription(description);
						defaultScheduleEvent.setStyleClass(styleClass);
						addEvent(defaultScheduleEvent);
					}
				}
			};
		}
		return eventModel;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) {
		if (event.getCreatedBy() == null) {
			event.setCreatedBy(getLoggedInUser());
			eventService.saveEvent(event);
			
		} else {
			eventService.updateEvent(event);
		}
		eventModel = null; // refreshing view
		event = new Event();
		sendToWebsocket();
	}

	public void deleteEvent(ActionEvent actionEvent) {
		eventService.deleteEvent(event);
		eventModel = null;
		event = new Event();
		sendToWebsocket();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		ScheduleEvent scheduleEvent = (ScheduleEvent) selectEvent.getObject();
		event = (Event) scheduleEvent.getData();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new Event();
		event.setStartDate((Date) selectEvent.getObject());
		event.setEndDate((Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent evt) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved",
				"Day delta:" + evt.getDayDelta() + ", Minute delta:" + evt.getMinuteDelta());
		ScheduleEvent scheduleEvent = evt.getScheduleEvent();
		Event event = (Event) scheduleEvent.getData();
		event = eventService.findEventById(event.getId());
		event.setStartDate(scheduleEvent.getStartDate());
		event.setEndDate(scheduleEvent.getEndDate());
		eventService.updateEvent(event);
		addMessage(message);
		sendToWebsocket();
	}

	public void onEventResize(ScheduleEntryResizeEvent evt) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized",
				"Day delta:" + evt.getDayDelta() + ", Minute delta:" + evt.getMinuteDelta());
		ScheduleEvent scheduleEvent = evt.getScheduleEvent();
		Event event = (Event) scheduleEvent.getData();
		event = eventService.findEventById(event.getId());
		event.setStartDate(scheduleEvent.getStartDate());
		event.setEndDate(scheduleEvent.getEndDate());
		eventService.updateEvent(event);
		addMessage(message);
		sendToWebsocket();
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	private void sendToWebsocket() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/calendar");
	}
}