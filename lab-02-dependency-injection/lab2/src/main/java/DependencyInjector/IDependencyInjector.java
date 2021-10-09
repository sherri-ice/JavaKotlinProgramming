package DependencyInjector;

public interface IDependencyInjector {
    void Register(Class<?> classObject);
    <T> void Register(Class<T> classObject, Class<? extends T> subClassObject);
    void CompleteRegistration();
    Object Resolve(Class<?> class_object);
}
