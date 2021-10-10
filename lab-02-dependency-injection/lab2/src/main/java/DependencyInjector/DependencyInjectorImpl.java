package DependencyInjector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

public class DependencyInjectorImpl implements IDependencyInjector {
    private boolean isRegistrationFinished = false;

    List<Class<?>> classes = new ArrayList<>();
    Map<Class<?>, Object> singletones = new HashMap<>();
    Map<Class<?>, Class<?>> interfaces = new HashMap<>();

    Vector<Vector<Boolean>> graphDependency = new Vector<>();

    @Override
    public void Register(Class<?> class_object) {
        if (isRegistrationFinished) {
            throw new RuntimeException("Cannot register class after finished registration.");
        }
        if (Modifier.isAbstract(class_object.getModifiers())) {
            throw new RuntimeException("Cannot register abstract class: " + class_object.getName());
        }
        if (Modifier.isInterface(class_object.getModifiers())) {
            throw new RuntimeException("Cannot register interface without its implementation, " +
                    "use two argument .Register(Inteface, Implementation): " + class_object.getName());
        }
        if (classes.contains(class_object)) {
            throw new RuntimeException(class_object.getName() + " already registered.");
        }
        classes.add(class_object);
    }

    @Override
    public <T> void Register(Class<T> classObject, Class<? extends T> subClassObject) {
        if (isRegistrationFinished) {
            throw new RuntimeException("Cannot register class after finished registration.");
        }
        if (!Modifier.isAbstract(classObject.getModifiers())) {
            throw new RuntimeException("First argument class should be an interface.");
        }
        if (Modifier.isInterface(subClassObject.getModifiers()) || Modifier.isAbstract(subClassObject.getModifiers())) {
            throw new RuntimeException(subClassObject.getName() + " is not implementation for " + classObject.getName());
        }
        if (interfaces.containsKey(classObject)) {
            throw new RuntimeException("Registering another implementation for " + classObject.getName());
        }
        if (Modifier.isInterface(classObject.getModifiers())) {
            interfaces.put(classObject, subClassObject);
        }
        classes.add(subClassObject);
    }


    private void CreateDependencyGraph() throws NoSuchMethodException {
        graphDependency.setSize(classes.size());
        for (int node = 0; node < graphDependency.size(); ++node) {
            graphDependency.set(node, new Vector<>());
            graphDependency.get(node).setSize(graphDependency.size());
            for (int i = 0; i < graphDependency.size(); ++i) {
                graphDependency.get(node).set(i, false);
            }
        }

        for (var classObject : classes) {
            HandleClassForGraph(classObject);
        }
    }

    private void CreateAllSingletones() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (var classObject : classes) {
            if (classObject.isAnnotationPresent(Singleton.class)) {
                singletones.put(classObject, GetInstance(classObject));
            }
        }
    }

    private void HandleClassForGraph(Class<?> class_object) throws NoSuchMethodException {
        var constructor = GetAnnotatedInjectConstructor(class_object);
        List<Class<?>> dependencyClasses = List.of(constructor.getParameterTypes());
        int classObjectIndex = classes.indexOf(class_object);
        for (var dependencyClass : dependencyClasses) {
            if (!classes.contains(dependencyClass)) {
                throw new RuntimeException("Requested " + dependencyClass.getName() + " in " + class_object.getName() +
                        " wasn't registered.");
            }
            int dependencyClassIndex = classes.indexOf(dependencyClass);
            if (graphDependency.get(dependencyClassIndex).get(classObjectIndex)) {
                throw new RuntimeException("Cyclic dependencies error.");
            }
            graphDependency.get(classObjectIndex).set(dependencyClassIndex, true);
        }

    }

    private Constructor<?> GetAnnotatedInjectConstructor(Class<?> classObject) {
        var constructors = classObject.getDeclaredConstructors();
        List<Constructor<?>> injectConstructors = new ArrayList<>();
        for (var constructor : constructors) {
            var modifiers = constructor.getModifiers();
            if (modifiers == Modifier.PUBLIC) {
                if (constructor.isAnnotationPresent(Inject.class)) {
                    injectConstructors.add(constructor);
                }
            } else {
                throw new RuntimeException("Constructor for " + classObject.getName() + " is private, should be public.");
            }
        }
        if (injectConstructors.size() > 1) {
            throw new RuntimeException("Multiple inject constructors for " + classObject.getName() + " are not supported.");
        }
        if (injectConstructors.isEmpty()) {
            throw new RuntimeException("No inject constructor for " + classObject.getName());
        }
        return injectConstructors.get(0);
    }

    @Override
    public void CompleteRegistration() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        isRegistrationFinished = true;
        CreateDependencyGraph();
        CreateAllSingletones();
    }

    @Override
    public Object Resolve(Class<?> class_object) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!isRegistrationFinished) {
            throw new RuntimeException("Registration wasn't finished.");
        }
        if (!classes.contains(class_object) && !interfaces.containsKey(class_object)) {
            throw new RuntimeException("Class " + class_object.getName() + " wasn't registered.");
        }
        return GetInstance(class_object);
    }

    private Object GetInstance(Class<?> classObject) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (Modifier.isInterface(classObject.getModifiers())) {
            return GetInstance(interfaces.get(classObject));
        }
        var constructor = GetAnnotatedInjectConstructor(classObject);
        if (classObject.isAnnotationPresent(Singleton.class) && singletones.containsKey(classObject)) {
            return singletones.get(classObject);
        }
        var dependencyClasses = constructor.getParameterTypes();
        var instantiatedObjects = new ArrayList<>();
        for (var dependencyClass : dependencyClasses) {
            instantiatedObjects.add(GetInstance(dependencyClass));
        }
        return constructor.newInstance(instantiatedObjects.toArray());
    }
}
