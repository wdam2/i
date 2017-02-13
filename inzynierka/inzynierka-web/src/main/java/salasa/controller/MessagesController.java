package salasa.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import salasa.model.Message;
import salasa.model.User;
import salasa.service.MessageService;
import salasa.service.UserService;

@SessionScoped
@Named
public class MessagesController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	MessageService messageService;

	@Inject
	UserService userService;

	private Message message;
	// we send message to this user
	private User user = null;

	@PostConstruct
	public void init() {
		if (message == null) {
			message = new Message();
		}
		if (getLoggedInUser().getRole().equals("CLIENT"))
			user = userService.findUserByRole("ENTREPRENEUR");
	}

	/**
	 * Client uses it to load messages
	 * 
	 * @return
	 */
	public List<Message> getYourMessages() {
		System.out.println("getYourMessages() ");
		List<Message> findAllUserMessages = messageService.findAllUserMessages(getLoggedInUser());
		boolean flag = false;
		for (Message message : findAllUserMessages) {
			if (message.getSendTo().equals(getLoggedInUser()) && message.isReceived() == false) {
				message.setReceived(true);
				messageService.updateMessage(message);
				flag = true;
			}
		}
		System.out.println("Jestem przed if w getYourMessages : ");
		if (flag) {
			System.out.println("Jestem w if w getYourMessages : ");
			sendToYourNewMessageSocket();
		}
		return findAllUserMessages;
	}

	/**
	 * Entrepreneur uses it to load messages
	 * 
	 * @return
	 */
	public List<Message> getYourAndUserMessages() {
		System.out.println("getYourAndUserMessages() ");
		if (user == null)
			return null;
		List<Message> findAllUserMessages = messageService.findAllUserMessages(user);
		boolean flag = false;
		for (Message message : findAllUserMessages) {
			if (message.getSendTo().equals(getLoggedInUser()) && message.isReceived() == false) {
				message.setReceived(true);
				messageService.updateMessage(message);
				flag = true;
			}
		}
		if (flag) {
			sendToYourNewMessageSocket();
		}
		return findAllUserMessages;
	}

	public String getNumberOfNewMessages() {
		return messageService.findNumberOfNewMessages(getLoggedInUser());
	}
	
	public String getNumberOfNewMessagesFromUser(User user) {
		String value = messageService.findNumberOfNewMessagesFromUser(getLoggedInUser(),user);
		return value;
	}
	
	public void sendMessage() {
		message.setPostDate(new Date());
		message.setSendFrom(getLoggedInUser());
		message.setSendTo(user);
		// if (message.getSendTo() == null)
		// message.setSendTo(userService.findUserByRole("ENTREPRENEUR"));
		messageService.saveMessage(message);
		message = new Message();
		sendToWebsocket();

	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		System.out.println("ustawiam usera na : " + user.getUsername());
		this.user = user;
	}

	private void sendToYourNewMessageSocket() {
		EventBus eventBus = EventBusFactory.getDefault().eventBus();
		System.out.println("sendToYourNewMessageSocket + /" + getLoggedInUser().getUsername());
		eventBus.publish("/" + getLoggedInUser().getUsername());
	}

	private void sendToWebsocket() {
		EventBus eventBus = EventBusFactory.getDefault().eventBus();
		eventBus.publish("/" + user.getUsername());
		eventBus.publish("/messages/" + user.getUsername());
	}

}
