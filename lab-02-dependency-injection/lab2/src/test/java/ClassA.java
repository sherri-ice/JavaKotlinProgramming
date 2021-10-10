import javax.inject.Inject;

public class ClassA implements Interface {
    @Inject
    public ClassA() {
    }

    @Override
    public String WhoAmI() {
        return "Class A";
    }
}
