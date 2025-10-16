// AddContractType.java
package uo.ri.cws.application.service.contracttype.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeRecordAssembler;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

import java.util.UUID;

public class AddContractType implements Command<ContractTypeDto> {

    private final ContractTypeDto dto;
    private final ContractTypeGateway gateway = Factories.persistence.forContractType();

    public AddContractType(ContractTypeDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isTrue(dto.compensationDays >= 0,
                "Compensation days must be non-negative");

        // Initialize DTO
        dto.id = UUID.randomUUID().toString();
        dto.version = 1L;
        this.dto = dto;
    }

    @Override
    public ContractTypeDto execute() throws BusinessException {
        try {
            // Check if contract type already exists
            if (gateway.findByName(dto.name).isPresent()) {
                throw new BusinessException(
                        "A contract type with name '" + dto.name + "' already exists");
            }

            // Add to database
            gateway.add(ContractTypeRecordAssembler.toRecord(dto));

            return dto;
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }
}