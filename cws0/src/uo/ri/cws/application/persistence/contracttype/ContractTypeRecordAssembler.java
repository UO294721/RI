// ContractTypeRecordAssembler.java
package uo.ri.cws.application.persistence.contracttype;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;

import java.time.LocalDateTime;

public class ContractTypeRecordAssembler {

    public static ContractTypeRecord toRecord(ContractTypeDto dto) {
        ContractTypeRecord record = new ContractTypeRecord();
        record.id = dto.id;
        record.name = dto.name;
        record.compensationDays = dto.compensationDays;
        record.version = dto.version;
        record.createdAt = LocalDateTime.now();
        record.updatedAt = LocalDateTime.now();
        record.entityState = "ENABLED";
        return record;
    }

    public static ContractTypeDto toDto(ContractTypeRecord record) {
        ContractTypeDto dto = new ContractTypeDto();
        dto.id = record.id;
        dto.name = record.name;
        dto.compensationDays = record.compensationDays;
        dto.version = record.version;
        return dto;
    }
}