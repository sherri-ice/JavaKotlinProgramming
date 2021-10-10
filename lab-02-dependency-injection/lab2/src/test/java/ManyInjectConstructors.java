import javax.inject.Inject;

public class ManyInjectConstructors {
    @Inject
    public ManyInjectConstructors() {
        System.out.print("Hello form Inject constructor #1!");
    }

    @Inject
    public ManyInjectConstructors(String str) {
        System.out.print("Hello form Inject constructor #2!");
    }
}
