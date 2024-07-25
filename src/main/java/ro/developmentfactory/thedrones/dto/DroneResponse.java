package ro.developmentfactory.thedrones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneResponse {
    private UUID idDrone;
    private String name;
    private int countMove;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
