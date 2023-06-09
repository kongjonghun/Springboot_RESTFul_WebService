package LGCNS.RestfulWebService.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"}) // data 필드명으로 지정
@JsonFilter("UserInfoV2")
public class UsersV2 extends Users {
    private String grade;
}
