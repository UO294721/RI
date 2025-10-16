// ListAllContractTypes.java
package uo.ri.cws.application.service.contracttype.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

public class ListAllContractTypes implements Command<List<ContractTypeDto>> {

    private final ContractTypeGateway gateway = Factories.persistence.forContractType();

    @Override
    public List<ContractTypeDto> execute() throws BusinessException {
        try {
            return gateway.findAll()
                    .stream()
                    .map(ContractTypeRecordAssembler::toDto)
                    .collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }
}