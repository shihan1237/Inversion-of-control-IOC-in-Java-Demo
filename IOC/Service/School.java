package IOC.Service;

public class School implements Service {
    @Autowired
    public Student stu;

    public void letSpeak(){
        stu.says();
    }

    public void broadcast(){
        System.out.println("school broadcast");
    }
    
}
