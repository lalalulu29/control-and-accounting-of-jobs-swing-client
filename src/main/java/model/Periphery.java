package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Periphery {
    private Long uuid;
    private Long workPlaceId;
    private String peripheryType;
    private String model;
    private String comments;
}
