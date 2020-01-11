package vn.heyzoo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import vn.heyzoo.dao.MyUserAccountDAO;
import vn.heyzoo.signup.MyConnectionSignUp;

@Configuration
@EnableSocial
//Load to Environment.
@PropertySource("classpath:social-cfg.properties")
public class SocialConfig implements SocialConfigurer {

	private boolean autoSignUp = true;
	@Autowired
	private DataSource dataSource;

	@Autowired
	private MyUserAccountDAO myUserAccountDAO;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		GoogleConnectionFactory gfactory = new GoogleConnectionFactory(//
				environment.getProperty("google.client.id"), //
				environment.getProperty("google.client.secret"));

		gfactory.setScope(environment.getProperty("google.scope"));

		connectionFactoryConfigurer.addConnectionFactory(gfactory);
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		 // org.springframework.social.security.SocialAuthenticationServiceRegistry
	      JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,
	              connectionFactoryLocator,
	 
	              Encryptors.noOpText());
	 
	      if (autoSignUp) {
	          // Cấu hình để:
	          // Sau khi đăng nhập vào mạng xã hội.
	          // Tự động tạo ra USER_ACCOUNT tương ứng nếu chưa có.
	          ConnectionSignUp connectionSignUp = new MyConnectionSignUp(myUserAccountDAO);
	          usersConnectionRepository.setConnectionSignUp(connectionSignUp);
	      } else {
	          // Cấu hình để:
	          // Sau khi đăng nhập vào mạng xã hội.
	          // Nếu không tìm thấy bản ghi USER_ACCOUNT tương ứng
	          // Chuyển tới trang đăng ký.
	          usersConnectionRepository.setConnectionSignUp(null);
	      }
	      return usersConnectionRepository;
	}
	  @Bean
	  public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, //
	          ConnectionRepository connectionRepository) {
	      return new ConnectController(connectionFactoryLocator, connectionRepository);
	  }
	  

}
