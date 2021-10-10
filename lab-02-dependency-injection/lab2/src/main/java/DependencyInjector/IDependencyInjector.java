package DependencyInjector;

import java.lang.reflect.InvocationTargetException;

public interface IDependencyInjector {
    void Register(Class<?> classObject);
    <T> void Register(Class<T> classObject, Class<? extends T> subClassObject);
    void CompleteRegistration() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    Object Resolve(Class<?> classObject) throws InvocationTargetException, InstantiationException, IllegalAccessException;
}
