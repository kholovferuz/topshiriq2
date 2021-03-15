package jpatopshiriq.topshiriq2.Task2.Payload;

import jpatopshiriq.topshiriq2.Task3.Entities.University;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO {
    private String kocha;
    private String uy;

    private Integer tumanId;

    private Integer universityID;
}
