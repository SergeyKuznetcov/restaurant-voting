package ru.topjava.graduate.restaurantvoting.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @RestResource(rel = "by-email", path = "by-email")
    @Cacheable("users")
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email) ")
    Optional<User> findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u JOIN FETCH Vote v WHERE u.id = :userId")
    Optional<User> getWithVotes(int userId);
}
