package salasa.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import salasa.model.User;
import salasa.service.MD5HashService;
import salasa.service.UserService;

@Named
@ViewScoped
public class RegisterController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	@Inject
	UserService userService;
	@Inject
	private FacesContext facesContext;
	@Inject MD5HashService md5HashService;

	public RegisterController() {
		user = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * This method checks to see if the username already exists.
	 * 
	 * @return A boolean value.
	 */
	private boolean isValidUser() {
		boolean valid = false;
		String username = user.getUsername();
		if (username != null) {
			if (userService.findUserByUsername(username) == null) {
				user.setRole("CLIENT");
				user.setPassword(md5HashService.hash(user.getPassword()));
				user.setConfirmed(false);
				valid = true;
			} else {
				facesContext.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"The username you have entered is already in use. Use a diffrent username.",
								"The username you have entered is already in use. Use a diffrent username."));
			}
		}
		return valid;
	}

	public String signup() {
		if (isValidUser()) {
			userService.saveUser(user);
			return "home.xhtml";
		}
		return null;
	}

}
