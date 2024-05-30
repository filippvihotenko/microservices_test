package by.viho.feedbackservice.repo;

import by.viho.feedbackservice.entity.FavouriteItem;
import by.viho.feedbackservice.entity.ItemReview;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FavouriteItemRepository extends ReactiveCrudRepository<FavouriteItem, UUID>
{
    Mono<FavouriteItem> getFavouriteItemByItemId(Integer itemId);
}
