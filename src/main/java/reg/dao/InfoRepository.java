package reg.dao;

import org.springframework.data.repository.CrudRepository;

import reg.domain.Info;

public interface InfoRepository extends CrudRepository<Info, Long> {

}
