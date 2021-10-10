import javax.inject.Inject;

public class ComplexClass {
    private ClassA classA;
    private ClassB classB;
    ComplexClass2 class2;
    @Inject
    public ComplexClass(ClassA classA, ClassB classB, ComplexClass2 class2) {
    }
}
