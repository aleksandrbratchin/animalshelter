package ru.teamfour.dao.entity.photoreport;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import ru.teamfour.dao.entity.ParentUUIDEntity;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photoReport")
public class PhotoReport extends ParentUUIDEntity {

    private byte[] data;

    @Builder
    public PhotoReport(UUID id, byte[] data) {
        super(id);
        this.data = data;
    }

}
