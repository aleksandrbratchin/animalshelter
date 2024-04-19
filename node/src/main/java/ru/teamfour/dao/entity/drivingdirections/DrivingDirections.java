package ru.teamfour.dao.entity.drivingdirections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.teamfour.dao.entity.ParentUUIDEntity;
import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.UUID;

/***
 * Схема проезда к приюту (фото)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drivingDirections")
public class DrivingDirections extends ParentUUIDEntity {
    @JsonIgnore
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", referencedColumnName = "id")
    private Shelter shelter;

    @Builder
    public DrivingDirections(UUID id, byte[] data, Shelter shelter) {
        super(id);
        this.data = data;
        this.shelter = shelter;
    }
}
