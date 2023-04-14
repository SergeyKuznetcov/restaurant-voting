package ru.topjava.graduate.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.topjava.graduate.restaurantvoting.model.Vote;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v JOIN FETCH User u where u.id = v.user.id " +
            "AND v.user.id = :userId AND v.menu.id = :menuId")
    Optional<Vote> getWithMenuAndUser(int menuId, int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH User, Menu WHERE v.menu.id = :menuId")
    List<Vote> getAllWithMenusAndUsersByMenuId(int menuId);

    @Query("SELECT v FROM Vote v JOIN FETCH User, Menu WHERE v.user.id = :userId")
    List<Vote> getAllWithMenusAndUsersByUserId(int userId);

    @Query("SELECT v FROM Vote v WHERE v.menu.id = :menuId AND v.user.id = :userId")
    Optional<Vote> getByMenuIdAndUserId(int menuId, int userId);

    List<Vote> getAllByMenuId(int menuId);

    List<Vote> getAllByUserId(int userId);

    @Query("DELETE FROM Vote v WHERE v.menu.id = :menuId")
    void deleteAllByMenuId(int menuId);

    @Query("DELETE FROM Vote v WHERE v.user.id = :userId")
    void deleteAllByUserId(int userId);
}
