package DependencyInjector;

import javax.inject.Inject;
import javax.swing.text.StyledEditorKit;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DependencyInjectorImpl implements IDependencyInjector {
    private boolean isRegistrationFinished = false;

    Set<Class<?>> classes = new HashSet<>();

    List<List<Boolean>> graphDependency = new ArrayList<>();


    private void ConstructGraph() {
        for (int i = 0; i < classes.size(); ++i) {
            graphDependency.set(i, new ArrayList<>(classes.size()));
        }
        for (var vector : graphDependency) {
            for (var pos : vector) {
                pos = false;
            }
        }
    }

    @Override
    public void Register(Class<?> class_object) {
        if (isRegistrationFinished) {
            throw new RuntimeException("Cannot register class after finished registration");
        }
        classes.add(class_object);
    }

    @Override
    public <T> void Register(Class<T> classObject, Class<? extends T> subClassObject) {

    }

    @Override
    public void CompleteRegistration() {
        isRegistrationFinished = true;
    }

    @Override
    public Object Resolve(Class<?> class_object) {
        return null;
    }
}
