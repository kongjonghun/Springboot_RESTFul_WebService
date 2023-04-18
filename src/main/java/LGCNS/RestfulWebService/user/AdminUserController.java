package LGCNS.RestfulWebService.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<Users> users = service.findAll();

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    // REST API Version 관리
    // 1. URI 이용 - @GetMapping("/v1/users/{id}") : GET /admin/users/1 -> admin/v1/users/1
    // 2. Request Parameter 이용 - @GetMapping(value = "/users/{id}/", params = "version=1") : Params(version - 1), http://localhost:8088/admin/users/1/?version=1
    // 3. Header 이용 - @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") : Headers(X-API-VERSION - 1)
    // 4. produces (MIME TIME) 이용 - @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") : Headers(Accept - application/vnd.company.appv1+json)

    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "/users/{id}/", params = "version=1")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int  id){
        Users users = service.findOne(id);

        if(users == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // Response Filter
        // Bean의 Property 제어
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");   // Filter 항목

        // .addFilter(Filter Bean Name, filter)
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/", params = "version=2")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int  id){
        Users users = service.findOne(id);

        if(users == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2 Copy
        UsersV2 userV2 = new UsersV2();
        BeanUtils.copyProperties(users, userV2);  // id, name, joinDate, password, ssn
        userV2.setGrade("VIP");

        // Response Filter
        // Bean의 Property 제어
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");   // Filter 항목

        // .addFilter(Filter Bean Name, filter)
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }


}
