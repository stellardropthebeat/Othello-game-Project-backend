package io.muzoo.ssc.project.backend.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoomResponseDTO {

    private boolean success;
    private String color = "";
    private Long availableRoom;
    private String message;
}
