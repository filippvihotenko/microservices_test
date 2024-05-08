package by.viho.catalogueservice.repo;



import by.viho.catalogueservice.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends CrudRepository<Item,Integer>
{
}
