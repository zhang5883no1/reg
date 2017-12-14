package reg.dao;

import org.springframework.data.repository.CrudRepository;

import reg.domain.Info2;

public interface Info2Repository extends CrudRepository<Info2, Long> {

	Info2 findByMob(String mob);

}
