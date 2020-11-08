package localClient;

import server.User;
import server.UserApi;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class localMain {
    /**
     * 本地调用远程服务
     */
    public static void main(String[] args){
        UserApi userApi = (UserApi) rpc(UserApi.class);
        User user = userApi.selectById(1);
        System.out.println("本地输出远程调用结果：\n"+user.toString());
    }
    /**
     *  动态创建代理对象
     *  Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
     *  参数1：真实对象的类加载器
     *  参数2：真实对象实现的所有的接口,接口是特殊的类，使用Class[]装载多个接口
     *  参数3： 接口，传递一个匿名内部类对象
     * @param clazz
     * @return
     */
    public static Object rpc(final Class clazz){
        // Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
        return Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new InvocationHandler() {
            /*
             * @param proxy  代理对象
             * @param method    代理的方法对象
             * @param args  方法调用时参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1",8888);
                String className = clazz.getName();//api类名
                String methodName=method.getName();//api 类成员方法名
                Class<?>[] parameterTypes=method.getParameterTypes(); //类成员方法参数类型集合

                ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeUTF(className);
                objectOutputStream.writeUTF(methodName);
                objectOutputStream.writeObject(parameterTypes);
                objectOutputStream.writeObject(args);

                ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
                Object o=objectInputStream.readObject();
                objectInputStream.close();
                objectOutputStream.close();
                return o;
            }
        });
    }
}

