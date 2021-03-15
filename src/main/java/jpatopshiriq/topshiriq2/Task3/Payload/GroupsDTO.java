package jpatopshiriq.topshiriq2.Task3.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupsDTO {
    private String name;
    private Integer numberOfStudents;

    private Integer facultyId;
}
