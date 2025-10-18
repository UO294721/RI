// DeleteContractType.java
package uo.ri.cws.application.service.contracttype.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class DeleteContractType implements Command<Void> {

    private final String name;
    private final ContractTypeGateway gateway = Factories.persistence.forContractType();
	private final ContractGateway cg = Factories.persistence.forContract();

    public DeleteContractType(String name) {
        ArgumentChecks.isNotBlank(name);
        this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
        try {
            // Find contract type
            Optional<ContractTypeRecord> record = gateway.findByName(name);
            BusinessChecks.exists(record, "Contract type does not exist");

            // Check if has contracts
            if (cg.findByType(name).isPresent()) {
                throw new BusinessException(
                        "Cannot delete contract type: there are contracts associated with it");
            }

            // Delete
            gateway.remove(record.get().id);

            return null;
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }
}