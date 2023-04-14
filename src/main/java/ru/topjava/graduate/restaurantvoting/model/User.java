package ru.topjava.graduate.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import ru.topjava.graduate.restaurantvoting.HasId;
import ru.topjava.graduate.restaurantvoting.web.HasIdAndEmail;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractNamedEntity implements HasIdAndEmail, Serializable {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    @Size(max = 128)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(max = 256)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Vote> votes;

    public User(Integer id, String name, String email, String password, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.setRoles(roles);
    }

    public User(Integer id, String name, String email, String password, Collection<Role> roles, Collection<Vote> votes) {
        super(id, name);
        this.email = email;
        this.password = password;
        setRoles(roles);
        setVotes(votes);
    }

    public void addVotes(Vote...votes){
        this.votes.addAll(List.of(votes));
    }

    public void setVotes(Collection<Vote> votes){
        this.votes = CollectionUtils.isEmpty(votes) ? Collections.emptyList() : List.copyOf(votes);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User:" + id + '[' + email + ']';
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
