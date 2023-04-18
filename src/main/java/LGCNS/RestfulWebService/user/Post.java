package LGCNS.RestfulWebService.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    //User : Post -> 1: (0~N) -> main : sub -> Parent : child
    //LAZY(지연전략) : 사용자 Entity 조회시, Post Entity가 같이 로딩되는 것이 아닌, Post data가 로딩되는 시점에 필요한 사용자 데이터를 가져오겠다
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore //외부에 노출 X
    private Users user;

}
