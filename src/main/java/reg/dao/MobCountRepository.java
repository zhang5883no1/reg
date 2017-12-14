package reg.dao;

import org.springframework.data.repository.CrudRepository;

import reg.domain.MobCount;

public interface MobCountRepository extends CrudRepository<MobCount, Long> {

	MobCount findByMobile(String mob);

}
