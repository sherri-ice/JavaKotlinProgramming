import javax.inject.Inject;
import javax.inject.Singleton;

public class ClassWithSingletonDependency {
    private Singletone singletone;

    @Inject
    public ClassWithSingletonDependency(Singletone singletone) {

    }
}
