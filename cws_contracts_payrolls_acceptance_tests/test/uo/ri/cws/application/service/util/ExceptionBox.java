package uo.ri.cws.application.service.util;

import uo.ri.util.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionBox {
	@FunctionalInterface
	public interface Action {
		void execute() throws Exception;
	}

	private Exception exception;
	
	public void tryAndKeep(Action action) {
		try {
			action.execute();
			fail("An exception was expected");
		} catch (Exception e) {
			this.exception = e;
		}
	}

	public void assertBusinessExceptionWithMessage() {
		assertNotNull( exception );
		assertInstanceOf(uo.ri.util.exception.BusinessException.class, exception);
		assertNotNull( exception.getMessage() );
		assertFalse( exception.getMessage().isBlank() );	
	}

	public void assertIllegalArgumentExceptionWithMessage() {
		assertNotNull(exception);
		assertInstanceOf(IllegalArgumentException.class, exception);
		assertNotNull(exception.getMessage());
		assertFalse(exception.getMessage().isBlank());
	}
}
