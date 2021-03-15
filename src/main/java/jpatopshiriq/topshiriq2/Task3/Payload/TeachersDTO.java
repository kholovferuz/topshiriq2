package jpatopshiriq.topshiriq2.Task3.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeachersDTO {
    private String firstName;
    private String lastName;
    private String gender;

    // university
    private Integer universityId;

    // group
    List<Integer> groupsId;

    //subject
    List<Integer> subjectId;
}
