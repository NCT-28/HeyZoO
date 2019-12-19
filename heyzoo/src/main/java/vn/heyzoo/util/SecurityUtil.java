package vn.heyzoo.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.heyzoo.model.MyUserAccount;
import vn.heyzoo.user.MySocialUserDetails;

public class SecurityUtil {
	// Tự động đăng nhập.
	public static void logInUser(MyUserAccount user) {

		MySocialUserDetails userDetails = new MySocialUserDetails(user);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
