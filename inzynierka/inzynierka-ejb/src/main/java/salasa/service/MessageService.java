package salasa.service;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import salasa.model.Message;
import salasa.model.User;

@Stateless
public class MessageService {

	@Inject
	private EntityManager em;
	public void saveMessage(Message message){
		em.persist(message);
	}
	
	public void updateMessage(Message message) {
		em.merge(message);
	}
	
	public List<Message> findAllUserMessages(User user) {
		TypedQuery<Message> query = em.createNamedQuery("MESSAGE_FIND_USER_SEND_FROM_TO_MESSAGES",Message.class);
		query.setParameter(1, user);
		List<Message> resultList = (List<Message>) query.getResultList();
		return resultList;
	}
	
	public String findNumberOfNewMessages(User user) {
		Query query = em.createNamedQuery("MESSAGE_COUNT_USER_MESSAGE");
		query.setParameter(1, user);
		String singleResult = Long.toString( (long) query.getSingleResult() );
		return singleResult;
	}
	
	public String findNumberOfNewMessagesFromUser(User user, User user2) {
		Query query = em.createNamedQuery("MESSAGE_SET_USER_MESSAGE");
		query.setParameter(1, user);
		
		List<Object[]> resultList = query.getResultList();
		HashMap<Integer, String> map = new HashMap<>();	
		for (Object[] object : resultList) 
			map.put( (Integer)object[0], Long.toString( (long)object[1]) );

		return map.get(user2.getId());
	}	
}
