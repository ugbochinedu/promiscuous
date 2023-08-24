package africa.semicolon.promiscuous.repositories;

import africa.semicolon.promiscuous.models.Media;
import africa.semicolon.promiscuous.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    //    boolean isFoundUser(User user);
    boolean existsByUser(User user);
//    List<Reaction> findByReactions();
//
//    List<Reaction> findAllByReactions();
//    Optional<Media> findByUserAndLike(User user, boolean like);

    @Query("SELECT m FROM Media m WHERE m.user = :user AND m.isLike = true")
    Optional<Media> findMediaByUserAndIsLikeIsTrue(User user);
}
