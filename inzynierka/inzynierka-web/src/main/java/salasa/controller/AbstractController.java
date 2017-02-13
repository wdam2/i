package salasa.controller;

import java.io.Serializable;

import javax.inject.Inject;

import salasa.model.User;
import salasa.util.UserSession;

/**
 * 
 * Podstawowa klasa dla obiektow kontrolerow
 */
public abstract class AbstractController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject UserSession userSession;
	
	protected User getLoggedInUser() {
		User user = null;
		if (userSession != null) {
			user = userSession.getUser();
		}
		return user;
	}
	
}
