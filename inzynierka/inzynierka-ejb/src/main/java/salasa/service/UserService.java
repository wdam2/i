package salasa.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import salasa.model.User;

/**
 * Session Bean implementation class UserRegistration
 */
@Stateless
@LocalBean
public class UserService {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	/**
	 * Default constructor.
	 */
	public UserService() {
		// TODO Auto-generated constructor stub
	}

	public void saveUser(User user) {
		log.info("Registering " + user.getUsername());
		em.persist(user);
	}

	public void updateUser(User user) {
		System.out.println("Jestem w updateUser SERVICE");
		System.out.println("id " + user.getId() + " firstname " + user.getFirstName());
		em.merge(user);
	}

	public void deleteUser(User user) {
		log.info("Deleting " + user.getUsername());
		User entityToBeRemoved = em.getReference(User.class, user.getId());
		em.remove(entityToBeRemoved);
	}

	public User findUserByUsername(String username) {
		// this solution
		try {
			User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// uzywana aby wyjąc uzytkownika ENTREPRENEUR z bazy
	public User findUserByRole(String role) {
		try {
			User user = (User) em.createNamedQuery("User.findByRole").setParameter("role", role).getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	public User getUserById(int id) {
		System.out.println(
				((User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult()).getId());
		return (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
	}

	public List<User> getUsersConfirmed() {
		return (List<User>) em.createNamedQuery("User.findAllConfirmed", User.class).getResultList();
	}

	public List<User> getUsersWithRoleClient() {
		return (List<User>) em.createNamedQuery("User.findAllUsersWithRoleClient", User.class).getResultList();
	}

}