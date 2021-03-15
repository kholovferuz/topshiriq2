package jpatopshiriq.topshiriq2.Task3.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentsDTO {
    private String firstName;
    private String lastName;
    private Integer age;

    // group
    private Integer groupId;

    // subject
    private List<Integer> subjectId;
}
