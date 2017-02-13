package salasa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "MESSAGE_FIND_ALL", query = "select m from Message m order by m.postDate desc"),
	@NamedQuery(name = "MESSAGE_FIND_USER_SEND_FROM_TO_MESSAGES", query = "select m from Message m where m.sendFrom = ?1 or m.sendTo = ?1 order by m.postDate desc"), 
	@NamedQuery(name = "MESSAGE_COUNT_USER_MESSAGE", query = "select COUNT(m) from Message m where m.sendTo = ?1 and m.received = false"), 
	@NamedQuery(name = "MESSAGE_SET_USER_MESSAGE", query = "SELECT m.sendFrom.id,COUNT(m) from Message m where m.sendTo = ?1 and m.received = false group by m.sendFrom") 
//	@NamedQuery(name = "EVENT_FIND_USER_CREATED_FOR_AND_FREE_EVENTS", query = "select e from Event e where (e.createdFor = ?1 or e.createdFor = null) and e.startDate between ?2 and ?3") 
})
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="sendFrom")
	private User sendFrom;
	@ManyToOne
	@JoinColumn(name="sendTo")
	private User sendTo;
	private Date postDate;
	private String message;
	private boolean received;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(User sendFrom) {
		this.sendFrom = sendFrom;
	}
	public User getSendTo() {
		return sendTo;
	}
	public void setSendTo(User sendTo) {
		this.sendTo = sendTo;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
	}

}
