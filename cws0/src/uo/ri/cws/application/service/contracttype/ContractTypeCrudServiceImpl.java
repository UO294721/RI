// ContractTypeCrudServiceImpl.java
package uo.ri.cws.application.service.contracttype;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contracttype.commands.*;
import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public class ContractTypeCrudServiceImpl implements ContractTypeCrudService {

    CommandExecutor executor = new CommandExecutor();

    @Override
    public ContractTypeDto create(ContractTypeDto dto) throws BusinessException {
        return executor.execute(new AddContractType(dto));
    }

    @Override
    public void delete(String name) throws BusinessException {
        executor.execute(new DeleteContractType(name));
    }

    @Override
    public void update(ContractTypeDto dto) throws BusinessException {
        executor.execute(new UpdateContractType(dto));
    }

    @Override
    public Optional<ContractTypeDto> findByName(String name) throws BusinessException {
        return executor.execute(new FindContractTypeByName(name));
    }

    @Override
    public List<ContractTypeDto> findAll() throws BusinessException {
        return executor.execute(new ListAllContractTypes());
    }
}