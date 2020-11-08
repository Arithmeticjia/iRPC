package server;

public class UserService implements UserApi {

    @Override
    public User selectById(Integer id) {
        User user=new User();
        user.setUsername("张三");
        user.setId(id);
        user.setMessage("张三是胖子");
        System.out.println("UserService调用了selectById()方法查询用户.....");
        return user;
    }
}

