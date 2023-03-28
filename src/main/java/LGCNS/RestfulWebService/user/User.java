package LGCNS.RestfulWebService.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"}) // data 필드명으로 지정
//@JsonFilter("UserInfo")
public class User {
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")  // 길이 제한
    private String name;
    @Past    // 과거 데이터만 가능
    private Date joinDate;

    //@JsonIgnore  // 데이터 Filtering
    private String password;
    //@JsonIgnore
    private String ssn;

    // 데이터 Filtering
    // 1. 필드 지정 : @JsonIgnore
    // 2. Class 지정 : @JsonIgnoreProperties
}
