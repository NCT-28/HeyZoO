package vn.heyzoo.dao;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.heyzoo.form.MyUserAccountForm;
import vn.heyzoo.mapper.MyUserAccountMapper;
import vn.heyzoo.model.MyUserAccount;

@Repository
@Transactional
public class MyUserAccountDAO extends JdbcDaoSupport {
	@Autowired
	public MyUserAccountDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public MyUserAccount findById(String id) {
		String sql = "Select id,email,user_name, first_name,last_name,"//
				+ " password,role"//
				+ " from user_accounts u "//
				+ " where id = ? ";
		Object[] params = new Object[] { id };
		MyUserAccountMapper mapper = new MyUserAccountMapper();
		try {
			MyUserAccount userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public MyUserAccount findByEmail(String email) {
		String sql = "Select id, email,user_name,first_name,last_name,"//
				+ " password,role"//
				+ " from user_accounts u "//
				+ " where email = ? ";
		Object[] params = new Object[] { email };
		MyUserAccountMapper mapper = new MyUserAccountMapper();
		try {
			MyUserAccount userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public MyUserAccount findByUserName(String userName) {
		String sql = "Select id, email,user_name,first_name,last_name,"//
				+ " password,role"//
				+ " from user_accounts u "//
				+ " where user_name = ? ";
		Object[] params = new Object[] { userName };
		MyUserAccountMapper mapper = new MyUserAccountMapper();
		try {
			MyUserAccount userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public MyUserAccount registerNewUserAccount(MyUserAccountForm accountForm) {
		String sql = "Insert into user_accounts "//
				+ " (id, email,user_name,first_name,last_name,password,role) "//
				+ " values (?,?,?,?,?,?,?) ";

		// String ngẫu nhiên 36 ký tự.
		String id = UUID.randomUUID().toString();

		this.getJdbcTemplate().update(sql, id, accountForm.getEmail(), //
				accountForm.getUserName(), //
				accountForm.getFirstName(), accountForm.getLastName(), //
				accountForm.getPassword(), MyUserAccount.ROLE_USER);
		return findById(id);
	}

	// Tự động tạo ra User Account.
	public MyUserAccount createUserAccount(Connection<?> connection) {

		ConnectionKey key = connection.getKey();
		// (facebook,12345), (google,123) ...

		System.out.println("key= (" + key.getProviderId() + "," + key.getProviderUserId() + ")");

		UserProfile userProfile = connection.fetchUserProfile();

		String email = userProfile.getEmail();
		MyUserAccount account = this.findByEmail(email);
		if (account != null) {
			return account;
		}

		// Tạo mới User_Account.
		String sql = "Insert into user_accounts "
				+ " (id, email,user_name,first_name,last_name,password,role) "
				+ " values (?,?,?,?,?,?,?) ";

		// String ngẫu nhiên 36 ký tự.
		String id = UUID.randomUUID().toString();

		String userName_prefix = userProfile.getFirstName().trim().toLowerCase()
				+ "_" + userProfile.getLastName().trim().toLowerCase();

		String userName = this.findAvailableUserName(userName_prefix);

		this.getJdbcTemplate().update(sql, id, email, userName, 
				userProfile.getFirstName(), userProfile.getLastName(), 
				"1234", MyUserAccount.ROLE_USER);
		return findById(id);
	}

	private String findAvailableUserName(String userName_prefix) {
		MyUserAccount account = this.findByUserName(userName_prefix);
		if (account == null) {
			return userName_prefix;
		}
		int i = 0;
		while (true) {
			String userName = userName_prefix + "_" + i++;
			account = this.findByUserName(userName);
			if (account == null) {
				return userName;
			}
		}
	}
}
