package salasa.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("activeUsers")
@ApplicationScoped
public class ActiveUsers extends ArrayList<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
