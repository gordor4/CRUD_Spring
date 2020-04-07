package ru.rus.crud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rus.crud.domain.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
