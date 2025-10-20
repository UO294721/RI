package uo.ri.cws.application.persistence;

import java.util.List;
import java.util.Optional;

/**
 *	Common interface for a gateway that provides common operations for any gateway.
 *  That is, CRUD operations: add, remove, update, find by id and find all.
 * @param <T>
 */
public interface Gateway<T> {
	
	/**
	 * Adds a new item to the table 
	 * @param t new item
	 * @throws PersistenceException on database errors
	 */
	void add(T t) throws PersistenceException;
	
	/**
	 * Removes an object from the table
	 * @param id key to delete
	 * @throws PersistenceException ondatabase errors
	 */
	void remove(String id) throws PersistenceException;
	
	/**
	 * Updates a row
	 * @param t new data to overwrite old one
	 * @throws PersistenceException on database errors
	 */
	void update(T t) throws PersistenceException;
	
	/**
	 * Finds a row in the table
	 * @param id record's primary key to retrieve
	 * @return dto from that record, probably null
	 * @throws PersistenceException on database errors
	 */
	Optional<T> findById(String id) throws PersistenceException;
	
	/**
	 * Retrieves all data in a table
	 * @return list of dtos, probably empty
	 * @throws PersistenceException on database errors
	 */
	List<T> findAll() throws PersistenceException;

}

