package ro.developmentfactory.thedrones.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drones")
public class Drones {

    @Id
    @GeneratedValue
    private int id_drone;

    private String name;
    private int count_move;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
