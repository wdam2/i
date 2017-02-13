package salasa.util;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import salasa.model.ActiveUsers;
import salasa.model.User;

@Named("userSession")
@SessionScoped
public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * We add user to the list of active users in LoginFilter class while loggin mechanism
	 */
	@Inject
	private ActiveUsers activeUsers;
	
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * CDI calls this method before the bean is destroyed. Since this class is
	 * session-scoped, it will get called if the user session expires, allowing
	 * us to remove the user from the active user list.
	 */
	@PreDestroy
	public void release() {
		activeUsers.remove(user);
	}

}
