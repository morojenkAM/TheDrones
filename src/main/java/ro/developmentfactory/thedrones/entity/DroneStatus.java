package ro.developmentfactory.thedrones.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.developmentfactory.thedrones.entity.Drone;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DroneStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_drone_status")
    private Long idDroneStatus;

    @Column(name = "current_position_x")
    private Integer currentPositionX;

    @Column(name = "current_position_y")
    private Integer currentPositionY;

    @Enumerated(EnumType.STRING)
    @Column(name = "facing_direction")
    private Direction facingDirection;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_drone")
    private Drone drone;
}
