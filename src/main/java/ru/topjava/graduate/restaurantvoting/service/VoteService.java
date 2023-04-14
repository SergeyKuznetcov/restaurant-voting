package ru.topjava.graduate.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduate.restaurantvoting.exception.IllegalRequestDataException;
import ru.topjava.graduate.restaurantvoting.model.Menu;
import ru.topjava.graduate.restaurantvoting.model.User;
import ru.topjava.graduate.restaurantvoting.model.Vote;
import ru.topjava.graduate.restaurantvoting.repository.MenuRepository;
import ru.topjava.graduate.restaurantvoting.repository.VoteRepository;
import ru.topjava.graduate.restaurantvoting.util.DateTimeUtil;
import ru.topjava.graduate.restaurantvoting.util.validation.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final MenuRepository menuRepository;

    @Transactional
    @Modifying
    public Vote vote(int menuId, User user, LocalDateTime localDateTime) {
        Menu menu = menuRepository.getExisted(menuId);
        if (!DateTimeUtil.getActualDateByDateTime(localDateTime).equals(menu.getDate())) {
            throw new IllegalRequestDataException("User cant vote for Menu id = " + menuId);
        }
        Vote vote = voteRepository.getWithMenuAndUser(menuId, user.id()).orElse(null);
        if (vote == null) {
            vote = voteRepository.save(new Vote(menu, user));
            user.addVotes(vote);
            menu.addVotes(vote);
        } else {
            clearDependenciesAndDelete(vote);
            vote = voteRepository.save(new Vote(menu, user));
        }
        return vote;
    }

    @Transactional
    @Modifying
    public void deleteAllByUserId(int userId) {
        List<Vote> votes = voteRepository.getAllWithMenusAndUsersByUserId(userId);
        votes.forEach(this::clearDependenciesAndDelete);
    }


    @Transactional
    @Modifying
    public void deleteAllByMenuId(int menuId) {
        List<Vote> votes = voteRepository.getAllWithMenusAndUsersByMenuId(menuId);
        votes.forEach(this::clearDependenciesAndDelete);
    }

    @Transactional
    @Modifying
    public void delete(int menuId, int userId) {
        Vote vote = voteRepository.getByMenuIdAndUserId(menuId, userId).orElse(null);
    }

    @Transactional
    @Modifying
    public void delete(int id) {
        Vote vote = ValidationUtil.checkExisted(voteRepository.findById(id).orElse(null), id);
        clearDependenciesAndDelete(vote);
    }

    private void clearDependenciesAndDelete(Vote vote) {
        vote.getMenu().getVotes().remove(vote);
        vote.getUser().getVotes().remove(vote);
        voteRepository.delete(vote.id());
    }
}
