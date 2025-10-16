// FindContractTypeByName.java
package uo.ri.cws.application.service.contracttype.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

import java.util.Optional;

public class FindContractTypeByName implements Command<Optional<ContractTypeDto>> {

    private final String name;
    private final ContractTypeGateway gateway = Factories.persistence.forContractType();

    public FindContractTypeByName(String name) {
        ArgumentChecks.isNotBlank(name);
        this.name = name;
    }

    @Override
    public Optional<ContractTypeDto> execute() throws BusinessException {
        try {
            return gateway.findByName(name)
                    .map(ContractTypeRecordAssembler::toDto);
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }
}