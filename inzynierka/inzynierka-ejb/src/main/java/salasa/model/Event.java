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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * JPA entity for event data.
 * 
 * @author Ian Hlavats (ian@tarantulaconsulting.com)
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "EVENT_FIND_ALL", query = "select e from Event e order by e.startDate desc"),
	@NamedQuery(name = "EVENT_FIND_USER_CREATED_BY_EVENTS", query = "select e from Event e where e.createdBy = ?1 and e.startDate between ?2 and ?3"), 
	@NamedQuery(name = "EVENT_FIND_USER_CREATED_FOR_AND_FREE_EVENTS", query = "select e from Event e where (e.createdFor = ?1 or e.createdFor = null) and e.startDate between ?2 and ?3") })
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="createdBy")
	private User createdBy;		//referencja zwrotna do wlasciciela
	
	@ManyToOne
	@JoinColumn(name="createdFor")
	private User createdFor;
	
	private String description;

	private Date endDate;

	private Date startDate;

	private String title;

	public User getCreatedBy() {
		return createdBy;
	}
	
	public User getCreatedFor() {
		return createdFor;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCreatedFor(User createdFor) {
		this.createdFor = createdFor;
	}


	public String getDescription() {
		return description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return startDate;
	}

	public String getTitle() {
		return title;
	}


	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}