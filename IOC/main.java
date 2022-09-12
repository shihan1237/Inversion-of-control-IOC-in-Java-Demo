package IOC;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import IOC.Service.Autowired;
import IOC.Service.School;
import IOC.Service.Service;
import IOC.Service.Student;

//暂时：单例，不考虑抽象和多态
public class main {
    static HashMap<Class, Object> map;
    @Autowired
    School sc;

    public static void main(String[] args)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        map = new HashMap<>();

        main m = new main();

        System.out.println("hello world");

        DFSInit(m);
        BFSInject(m);
        m.sc.letSpeak();
        m.sc.stu.letBroadcast();
    }


    // 需要获得o的全部成员变量，
    // 判断是否是Autowired，若是：（判断表里是否有相应的类型的对象，有则传入，没有则先创建再传入，再inject该对象）
    // DFS和BFS两种方式实现

    // DFS
    public static void DFSInit(Object o) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Class claz = o.getClass();
        Field[] fields = claz.getDeclaredFields();
        for (Field f : fields) {
            Class type = f.getType();
            // 判断type是否是实现Service接口
            if (Service.class.isAssignableFrom(type)) {
            // 判断type是否是实现AutoWired注解
            // if (f.isAnnotationPresent(Autowired.class)) {
                f.set(o, inject(type));

            }

        }

    }

    public static Object inject(Class clazz) throws InstantiationException, IllegalAccessException {
        if (map.containsKey(clazz))
            return map.get(clazz);
        Object o = clazz.newInstance();
        map.put(clazz, o);
        DFSInit(o);
        return o;

    }

    // BFS
    public static void BFSInject(Object o) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
        
        LinkedList<Object> queue = new LinkedList<>();
        queue.offer(o);
        while(!queue.isEmpty()){
            Object asd = queue.poll();
            Class ASD = asd.getClass();
            Field[] fields = ASD.getDeclaredFields();
            for (Field f : fields) {
                Class type = f.getType();
                // 判断type是否是实现Service接口
            if (Service.class.isAssignableFrom(type)) {
                // 判断type是否是实现AutoWired注解
                // if (f.isAnnotationPresent(Autowired.class)) {
                    if(map.containsKey(type)) f.set(asd, map.get(type));
                    else{
                        Object asd2 = type.newInstance();
                        map.put(type, asd2);
                        f.set(asd, asd2);
                        queue.offer(asd2);
                    }

                }

            }
        }
    }

}
