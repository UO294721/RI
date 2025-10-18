package uo.ri.cws.application.service.contract;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contract.commands.*;
import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public class ContractCrudServiceImpl implements ContractCrudService {
	
	private final CommandExecutor executor = new CommandExecutor();
	
	@Override
	public ContractDto create(ContractDto c) throws BusinessException {
		return executor.execute(new AddContract(c));
	}
	
	@Override
	public void update(ContractDto dto) throws BusinessException {
		executor.execute(new UpdateContract(dto));
	}
	
	@Override
	public void delete(String id) throws BusinessException {
		executor.execute(new DeleteContract(id));
	}
	
	@Override
	public void terminate(String contractId) throws BusinessException {
		executor.execute(new TerminateContract(contractId));
	}
	
	@Override
	public Optional<ContractDto> findById(String id) throws BusinessException {
		return executor.execute(new FindContractById(id));
	}
	
	@Override
	public List<ContractSummaryDto> findByMechanicNif(String nif) throws BusinessException {
		return executor.execute(new FindContractsByMechanicNif(nif));
	}
	
	@Override
	public List<ContractDto> findInforceContracts() throws BusinessException {
		return executor.execute(new FindInforceContracts());
	}
	
	@Override
	public List<ContractSummaryDto> findAll() throws BusinessException {
		return executor.execute(new FindAllContracts());
	}
}
