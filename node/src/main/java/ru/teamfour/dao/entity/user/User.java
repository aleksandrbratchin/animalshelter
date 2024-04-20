package ru.teamfour.dao.entity.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.textcommand.command.api.State;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Таблица клиентов и волонтеров
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditEntity {

    /**
     * id чата в телеграм
     */
    @Column(name = "chat_id", unique = true, nullable = false)
    Long chatId;

    /**
     * role - роль пользователя {@link RoleUser}
     */
    @Column(name = "role_user", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleUser role;

    /**
     * state - название меню в котором пользователь находится в данный момент {@link State}
     */
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    /**
     * userInfo - Общая информация о пользователе и контактные данные {@link UserInfo}
     */
    @Embedded
    private UserInfo userInfo;

    @Embedded
    private VolunteerParam volunteerParam;

    @Embedded
    private Chat chat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    /**
     * Усыновления
     */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AdoptionProcessAnimal> adoptions = new ArrayList<>();

    @Builder
    public User(UUID id, Long chatId, RoleUser role, State state, UserInfo userInfo, VolunteerParam volunteerParam, Chat chat, Shelter shelter, List<AdoptionProcessAnimal> adoptions) {
        super(id);
        this.chatId = chatId;
        this.role = role;
        this.state = state;
        this.userInfo = userInfo;
        this.volunteerParam = VolunteerParam.builder().workload(0).build();
        this.chat = chat;
        this.shelter = shelter;
        this.adoptions = adoptions;
    }

    /**
     * Возвращает активное усыновление в текущем приюте
     * @return {@link AdoptionProcessAnimal}
     */
    public AdoptionProcessAnimal getActiveAdoptionProcess() {
        if (adoptions == null || adoptions.size() == 0) return null;
        List<AdoptionProcessAnimal> adoptionProcessAnimalStream = adoptions.stream().filter(
                adoptionProcessAnimal ->
                        adoptionProcessAnimal.getShelter().getId().equals(shelter.getId()) &&
                                adoptionProcessAnimal.getAdoptionProcessStatus().equals(AdoptionProcessStatus.PROCESS_ADOPTION)
        ).toList();
        if (adoptionProcessAnimalStream.size() > 1) {
            return null;
        }
        return adoptionProcessAnimalStream.get(0);
    }

}
