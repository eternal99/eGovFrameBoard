package egovframework.login.domain.exception;

public class DuplicateLoginIdException  extends RuntimeException{
	public DuplicateLoginIdException(String message) {
		super(message);
	}

}
