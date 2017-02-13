package salasa.filters;

import java.io.IOException;
import salasa.model.ActiveUsers;
import salasa.model.User;
import salasa.service.UserService;
import salasa.util.UserSession;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName = "LoginFilter", urlPatterns = { "/calendar.xhtml", "/messages.xhtml" })
public class LoginFilter implements Filter {

	@Inject
	private UserService userService;

	// @Inject UserController userController;

	@Inject
	ActiveUsers activeUsers;

	@Inject
	UserSession userSession;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		// ustawianie property session beana UserSession 
		String username = (String) ((HttpServletRequest) servletRequest).getUserPrincipal().getName(); // getParameter("j_username");
		User user = userService.findUserByUsername(username);
		userSession.setUser(user);
		if(!activeUsers.contains(user))
			activeUsers.add(user);
			
		filterChain.doFilter(servletRequest, servletResponse);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}