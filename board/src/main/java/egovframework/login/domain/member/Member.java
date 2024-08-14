package egovframework.login.domain.member;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Member {
	private Long id;
	
	@NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 영문과 숫자로 4~12자리여야 합니다.")
	private String loginId;
	
	@NotEmpty
	private String name;

	@NotEmpty
	private String password;
	
	private boolean admin = false;

}
