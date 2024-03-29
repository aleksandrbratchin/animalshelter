package ru.teamfour.dao.entity.shelters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "drivingDirections")
public class DrivingDirections {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    @JsonIgnore
    private byte[] data;
    @OneToOne
    private Shelter shelter;
}
