import javax.inject.Inject;

public class ClassB implements Interface {
    @Inject
    public ClassB() {

    }
    @Override
    public String WhoAmI() {
        return "Class B";
    }
}
