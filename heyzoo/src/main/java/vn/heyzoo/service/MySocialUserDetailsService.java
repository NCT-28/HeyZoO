package vn.heyzoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import vn.heyzoo.user.MySocialUserDetails;

@Service
public class MySocialUserDetailsService implements SocialUserDetailsService {

	@Autowired
	private UserDetailsService userDetailService;

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		  // Dựa trên UserDetailService. (Xem MyUserDetailService).
	      UserDetails userDetails = userDetailService.loadUserByUsername(userId);
	 
	      return (MySocialUserDetails) userDetails;
	}

	

}
