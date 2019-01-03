package cn.mauth.ccrm.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

@Component
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String accept = request.getHeader(HttpHeaders.ACCEPT);
		if (accept != null) {
			String at=request.getHeader("accept");

			if (at.indexOf(MediaType.TEXT_HTML.toString()) >= 0) {
				redirectStrategy.sendRedirect(request, response, "/toLogin");
			}else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
			}
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		}
	}
}
