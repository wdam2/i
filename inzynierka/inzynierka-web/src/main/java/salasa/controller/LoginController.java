package salasa.controller;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import salasa.model.ActiveUsers;

/**
 * Klasa kontrolera związana z funnkcjonalnością logowania. 
 * 
 * @author mateusz
 *
 */
@Named("loginController")
@ViewScoped
public class LoginController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	ActiveUsers activeUsers;

	public String logOut() throws IOException {
		activeUsers.remove(getLoggedInUser());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
		return "home.xhtml";
	}
}
