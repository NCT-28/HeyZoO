package vn.heyzoo.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;
import vn.heyzoo.model.MyUserAccount;

/**
 * MySocialUserDetails là lớp thực hiện interface SocialUserDetails, chú ý
 * SocialUserDetails là interface mở rộng từ interface UserDetails. Việc login
 * thông qua mạng xã hội sử dụng Spring MVC Social API về bản chất là dựa trên
 * Spring Security API.
 */
public class MySocialUserDetails implements SocialUserDetails {
	private static final long serialVersionUID = -5246117266247684905L;
	private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	private MyUserAccount myUserAccount;

	public MySocialUserDetails(MyUserAccount myUserAccount) {
		this.myUserAccount = myUserAccount;
		String role = myUserAccount.getRole();

		GrantedAuthority grant = new SimpleGrantedAuthority(role);
		this.list.add(grant);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return list;
	}

	@Override
	public String getPassword() {
		 return myUserAccount.getPassword();
	}

	@Override
	public String getUsername() {
		return myUserAccount.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUserId() {
		 return this.myUserAccount.getId();
	}

}
