package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workplace implements Serializable {
    private Long workplaceId;
    private String domainName;
    private String ip;
    private String mac;
}
