// UpdateContractType.java
package uo.ri.cws.application.service.contracttype.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Optional;

public class UpdateContractType implements Command<Void> {

    private final ContractTypeDto dto;
    private final ContractTypeGateway gateway = Factories.persistence.forContractType();

    public UpdateContractType(ContractTypeDto dto) {
        ArgumentChecks.isNotNull(dto);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isTrue(dto.compensationDays >= 0,
                "Compensation days must be non-negative");
        this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {
        try {
            // Find contract type
            Optional<ContractTypeRecord> record = gateway.findByName(dto.name);
            BusinessChecks.exists(record, "Contract type does not exist");

            // Update only compensation days
            ContractTypeRecord toUpdate = record.get();
            toUpdate.compensationDaysPerYear = dto.compensationDays;
            toUpdate.updatedAt = LocalDateTime.now();

            gateway.update(toUpdate);

            return null;
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }
}