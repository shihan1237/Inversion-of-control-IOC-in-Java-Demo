package IOC.Service;

public class Student implements Service {
    @Autowired
    public School sc;

    public void says(){
        System.out.println("stu speak");
    }

    public void letBroadcast(){
        sc.broadcast();
    }

}
