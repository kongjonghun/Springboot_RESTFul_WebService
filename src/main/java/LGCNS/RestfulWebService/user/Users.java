package LGCNS.RestfulWebService.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"}) // data 필드명으로 지정
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")   // @ApiModel : 객체에 대한 Description(Documentation), @ApiModelProperty : 필드에 대한 Description
@Entity
public class Users {
    @Id              // Table의 ID값
    @GeneratedValue  // 값 자동 생성
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")  // 길이 제한
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;

    @Past    // 과거 데이터만 가능
    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.")
    private Date joinDate;

    //@JsonIgnore  // 데이터 Filtering
    @ApiModelProperty(notes = "사용자 패스워드를를 입력 주세요.")
    private String password;

    //@JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호을 입력해 주세요.")
    private String ssn;

    // 데이터 Filtering
    // 1. 필드 지정 : @JsonIgnore
    // 2. Class 지정 : @JsonIgnoreProperties

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public Users(int id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
