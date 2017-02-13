package salasa.controller;

import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import salasa.model.User;
import salasa.service.UserService;

//The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
//EL name
//Read more about the @Model stereotype in this FAQ:
//http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
/* 
* Dalszy development zaczniemy od modelu – stwórzmy klasę reprezentującą klienta i oznaczmy ją adnotacją @Model.
*  Jest to zwykła klasa (POJO). Adnotacja @Model robi z niej jednak klasę zarządzaną przez kontener i udostępnia
*  ją do użycia za pomocą EL nadając jej nazwę odpowiadającą nazwie klasy, ale pisaną małą literą. Przy okazji tworzenia
*  klasy modelu zdefiniujemy też constrainty walidacyjne, które zostaną użyte do walidacji formularza.*.
*/
//@Model
/**
 * Klasa kontrolera dla funkcjonalności związanych z użytkownikiem.
 * 
 */

@Named("userController")
@ViewScoped
public class UserController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UserService userService;

	/**
	 * Zwraca obecnie zalogowanego użytkownika.
	 */
	public User getUser() {
		return getLoggedInUser();
	}

	public List<User> getUsersConfirmed() {
		return userService.getUsersConfirmed();
	}

	public User getUserById(int id) {
		return userService.getUserById(id);
	}

}
