package salasa.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import salasa.model.User;
import salasa.service.UserService;

@Named("clientsController")
@ViewScoped
public class ClientsController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;

	private List<User> usersWithRoleClient = null;

	private User selectedUser = null;

	@PostConstruct
	public void init() {
		usersWithRoleClient = userService.getUsersWithRoleClient();
	}

	public List<User> getUsersWithRoleClient() {
		if (usersWithRoleClient == null)
			userService.getUsersWithRoleClient();
		return usersWithRoleClient;
	}

	public void updateUser(User user) {
		userService.updateUser(user);
	}

	public void deleteUser() {
		userService.deleteUser(selectedUser);
		usersWithRoleClient = userService.getUsersWithRoleClient();
		FacesMessage msg = new FacesMessage("Usunięto klienta o id", Integer.toString(selectedUser.getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
		selectedUser = null;
	}

	public void onRowEdit(RowEditEvent event) {
		User user = (User) event.getObject();
		updateUser(user);
		FacesMessage msg = new FacesMessage("Zatwierdzono edycję klienta o id", Integer.toString(user.getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		User user = (User) event.getObject();
		FacesMessage msg = new FacesMessage("Anulowano edycję klienta o id", Integer.toString(user.getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

}
