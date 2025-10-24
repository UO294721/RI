package uo.ri.cws.application.service.client;

import uo.ri.util.exception.BusinessException;

import java.util.List;
import java.util.Optional;

/**
 * This service is intended to be used by the Cashier
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface ClientCrudService {

	ClientDto create( ClientDto dto) throws BusinessException;
	
	Optional<ClientDto> findById(String id) throws BusinessException;
	
	Optional<ClientDto> findByNif(String nif) throws BusinessException;
	
	void update(ClientDto dto) throws BusinessException;
	
	void delete(String id) throws BusinessException;
	
	List<ClientDto> findAll() throws BusinessException;

	class ClientDto {
        public String id;
        public long version;
        
        public String nif;
        public String name;
        public String surname;
        public String addressStreet;
        public String addressCity;
        public String addressZipcode;
        public String phone;
        public String email;
	}

}
