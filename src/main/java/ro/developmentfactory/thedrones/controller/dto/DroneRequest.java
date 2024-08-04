package ro.developmentfactory.thedrones.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneRequest {
    @NotBlank(message = "Name cannot be null, empty, or contain only whitespace characters")
    private String name;

}
