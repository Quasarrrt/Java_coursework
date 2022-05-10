package tea_shop.repositories;

import tea_shop.models.Tea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaRepository extends JpaRepository<Tea, Integer> {
    List<Tea> findAllBySort(String sort);
    Tea findByName(String name);
}
