package LGCNS.RestfulWebService.user;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    private static List<Users> users = new ArrayList<>();
    private static int userCount = 3;

    static {
        users.add(new Users(1, "kennth", new Date(), "test1", "701010-1111111"));
        users.add(new Users(2, "alice", new Date(), "test2", "820411-1111111"));
        users.add(new Users(3, "elena", new Date(), "test3", "941010-1111111"));
    }

    public List<Users> findAll(){
        return users;
    }

    public Users findOne(int id){
        for(Users users : UserDaoService.users){
            if(users.getId() == id){
                return users;
            }
        }
        return null;
    }

    public Users save(Users users){
        if(users.getId() == null){
            users.setId(++userCount);
        }
        UserDaoService.users.add(users);
        return users;
    }

    public Users deleteById(int id){
        Iterator<Users> iterator = users.iterator();

        while (iterator.hasNext()){
            Users users = iterator.next();

            if(users.getId() == id){
                iterator.remove();
                return users;
            }
        }
        return null;
    }

    public Users updateById(int id, Users updateUsers) {
        Users users = findOne(id);
        if(users != null){
            users.setName(updateUsers.getName());
            users.setJoinDate(updateUsers.getJoinDate());

            return users;
        }
        return null;
    }



}
