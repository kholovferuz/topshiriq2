package jpatopshiriq.topshiriq2.Task2.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegionDTO {
    private String viloyat;

    private Integer countryId;
}
