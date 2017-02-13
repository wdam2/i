package salasa.service;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import salasa.model.Event;
import salasa.model.User;

@Stateless
public class EventService {

	@Inject
	private EntityManager em;

	public EventService() {
	}

	public Event saveEvent(Event event) {
		em.persist(event);
		return event;
	}

	public void updateEvent(Event event) {
		em.merge(event);
	}

	public void deleteEvent(Event event) {
		Event entityToBeRemoved = em.find(Event.class, event.getId());
		em.remove(entityToBeRemoved);
	}

	public Event findEventById(int id) {
		return em.find(Event.class, id);
	}

	public List<Event> findAllEvents() {
		List<Event> resultList = (List<Event>) em.createNamedQuery("EVENT_FIND_ALL", Event.class).getResultList();
		return resultList;
	}

	public List<Event> findUserAndFreeEvents(Date start, Date end, User user) {
		TypedQuery<Event> query = em.createNamedQuery("EVENT_FIND_USER_CREATED_FOR_AND_FREE_EVENTS", Event.class);
		query.setParameter(1, user);
		query.setParameter(2, start);
		query.setParameter(3, end);
		List<Event> resultList = (List<Event>) query.getResultList();
		return resultList;
	}

}
