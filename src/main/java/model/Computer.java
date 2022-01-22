package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Computer {
    private Long uuid;
    private Long workPlaceId;
    private String powerSupply;
    private String motherboard;
    private String ram;
    private String cpu;
    private String videoCard;
    private String caseFormFactor;
    private String hddOrSsd;
}
