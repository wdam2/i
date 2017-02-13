package salasa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
		@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
		@NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE u.role = :role"),
		@NamedQuery(name = "User.findAllConfirmed", query = "SELECT u FROM User u WHERE u.confirmed = true and u.role like 'CLIENT'"),
		@NamedQuery(name = "User.findAllUsersWithRoleClient", query = "SELECT u FROM User u WHERE u.role like 'CLIENT'") })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// @NotNull
	@NotEmpty(message = "Pole nie może być puste")
	private String username;
	@Lob
	// @NotNull
	@NotEmpty(message = "Pole nie może być puste")
	private String password;
	// @NotNull
	@NotEmpty(message = "Pole nie może być puste")
	private String firstName;
	// @NotNull
	@NotEmpty(message = "Pole nie może być puste")
	private String lastName;
	private String information;
	@Pattern(regexp = "^((\\d{3})[-]?(\\d{3})[-]?(\\d{3}))?$", message = "Pole powinno być puste bądź pasować do numeru telefonu.")
	private String mobilePhone;
	@Pattern(regexp = "^((\\d{3})[-]?(\\d{3})[-]?(\\d{3}))?$", message = "Pole powinno być puste bądź pasować do numeru telefonu.")
	private String homePhone; 

	private String role;
	private boolean confirmed;

	@OneToMany(mappedBy = "createdBy",  cascade={CascadeType.REMOVE}) // dzieki temu mozna znalezc user do
										// ktorego nalezy spotkanie
	private List<Event> eventsCreatedByYou;

	@OneToMany(mappedBy = "createdFor",  cascade={CascadeType.REMOVE})
	private List<Event> eventsCreatedForYoy;

	@OneToMany(mappedBy = "sendFrom", cascade={CascadeType.REMOVE})
	private List<Message> messageSendFromYou;

	@OneToMany(mappedBy = "sendTo", cascade={CascadeType.REMOVE})
	private List<Message> messagesSendToYou;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getInformation() {
		return information == null ? "Wpisz informajce o sobie, aby prawnik mógł Cię zidentyfikować." : information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public List<Event> getEventsCreatedByYou() {
		return eventsCreatedByYou;
	}

	public void setEventsCreatedByYou(List<Event> eventsCreatedByYou) {
		this.eventsCreatedByYou = eventsCreatedByYou;
	}

	public List<Event> getEventsCreatedForYoy() {
		return eventsCreatedForYoy;
	}

	public void setEventsCreatedForYoy(List<Event> eventsCreatedForYoy) {
		this.eventsCreatedForYoy = eventsCreatedForYoy;
	}

	public List<Message> getMessageSendFromYou() {
		return messageSendFromYou;
	}

	public void setMessageSendFromYou(List<Message> messageSendFromYou) {
		this.messageSendFromYou = messageSendFromYou;
	}

	public List<Message> getMessagesSendToYou() {
		return messagesSendToYou;
	}

	public void setMessagesSendToYou(List<Message> messagesSendToYou) {
		this.messagesSendToYou = messagesSendToYou;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

}