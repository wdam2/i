package salasa.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import salasa.controller.UserController;
import salasa.model.User;

@FacesConverter( forClass = User.class,value = "userConverter")
public class UserConverter implements Converter {

	@Inject
	private UserController ur;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		try {
			return ur.getUserById(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		  if (value instanceof User) { 
			   return String.valueOf(((User) value).getId()); 
			  } else if (value instanceof String) { 
			   return ((String) value); 
			  } 
			  return null;
	}

}
