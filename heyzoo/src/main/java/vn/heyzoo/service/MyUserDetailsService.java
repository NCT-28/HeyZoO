package vn.heyzoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import vn.heyzoo.dao.MyUserAccountDAO;
import vn.heyzoo.model.MyUserAccount;
import vn.heyzoo.user.MySocialUserDetails;

/**
 * MyUserDetailsService là dịch vụ được sử dụng bởi Spring Security API để lấy
 * thông tin người dùng từ Database.
 */

//Lấy thông tin User dưới database.
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MyUserAccountDAO myUserAccountDAO;

	public MyUserDetailsService() {

	}

	// (Phương thức này được sử dụng bởi Spring Security API).
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserAccount myUserAccount = myUserAccountDAO.findByUserName(username);

		if (myUserAccount == null) {
			throw new UsernameNotFoundException("No user found with userName: " + username);
		}

		// Chú ý: SocialUserDetails mở rộng từ interface UserDetails.
		SocialUserDetails principal = new MySocialUserDetails(myUserAccount);

		return principal;
	}

}
