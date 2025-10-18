package uo.ri.cws.application.service.contract.update;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.util.ExceptionBox;

/**
 * Feature: Update a contract
 * Scenario: [C.U.7] Try to update a null contract
 */
public class ScenarioCU7 {
	private final ExceptionBox ctx = new ExceptionBox();
	private final ContractCrudService service = Factories.service.forContractCrudService();

	@When("[C.U.7] I try to update a null contract")
	public void whenITryToUpdateANullContract() {
		ctx.tryAndKeep(() -> service.update( null ));
	}

	@Then("[C.U.7] argument is rejected with an explaining message")
	public void thenArgumentIsRejectedWithAnExplainingMessage() {
		ctx.assertIllegalArgumentExceptionWithMessage();
	}
}