import DependencyInjector.DependencyInjectorImpl;
import org.junit.jupiter.api.*;

import DependencyInjector.IDependencyInjector;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;


public class Tests {
    @Nested
    class BasicTests {
        @org.junit.jupiter.api.Test
        void BasicTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ClassA.class);
            di.Register(ClassB.class);
            di.CompleteRegistration();
            assertEquals(di.Resolve(ClassA.class).getClass(), ClassA.class);
            assertEquals(di.Resolve(ClassB.class).getClass(), ClassB.class);
        }

        @Test
        void TwoClassesTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ClassA.class);
            di.CompleteRegistration();
            ClassA firstClass = (ClassA) di.Resolve(ClassA.class);
            ClassA secondClass = (ClassA) di.Resolve(ClassA.class);
            assertNotEquals(firstClass, secondClass);
            assertEquals(firstClass.getClass(), secondClass.getClass());
        }

        @Test
        void SingletoneTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(Singletone.class);
            di.CompleteRegistration();
            Singletone first = (Singletone) di.Resolve(Singletone.class);
            Singletone second = (Singletone) di.Resolve(Singletone.class);
            assertEquals(first.getClass(), second.getClass());
            assertEquals(first, second);
        }

        @Test
        void InterfaceAndImplementationTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(Interface.class, ClassA.class);
            di.CompleteRegistration();
            assertEquals(ClassA.class, di.Resolve(Interface.class).getClass());
        }
    }

    @Nested
    class NotValidCases {
        @Test
        void RegisterAfterRegistrationCompleteTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(Singletone.class);
            di.CompleteRegistration();
            var exception = assertThrows(RuntimeException.class, () -> di.Register(ClassA.class));
            assertEquals(exception.getMessage(), "Cannot register class after finished registration.");
        }

        @Test
        void AbstractClassRegistrationTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            var exception = assertThrows(RuntimeException.class, () -> di.Register(AbstractClass.class));
            assertEquals(exception.getMessage(), "Cannot register abstract class: " + AbstractClass.class.getName());
        }

        @Test
        void InterfaceWithoutImplementationTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            var exception = assertThrows(RuntimeException.class, () -> di.Register(Interface.class, MyClass.class));
            assertEquals(exception.getMessage(), MyClass.class.getName() + " is not implementation for " + Interface.class.getName());
        }

        @Test
        void ManyInjectConstructorsTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ManyInjectConstructors.class);
            var exception = assertThrows(RuntimeException.class, di::CompleteRegistration);
            assertEquals(exception.getMessage(), "Multiple inject constructors for " + ManyInjectConstructors.class.getName() + " are not supported.");
        }

        @Test
        void PrivateConstructorTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(PrivateConstructor.class);
            var exception = assertThrows(RuntimeException.class, di::CompleteRegistration);
            assertEquals(exception.getMessage(), "Constructor for " + PrivateConstructor.class.getName() + " is private, should be public.");
        }

        @Test
        void ClassTwiceTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ClassA.class);
            var exception = assertThrows(RuntimeException.class, () -> di.Register(ClassA.class));
            assertEquals(exception.getMessage(), ClassA.class.getName() + " already registered.");
        }

        @Test
        void RegisterClassWithUnregisteredDependenciesTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ComplexClass.class);
            var exception = assertThrows(RuntimeException.class, di::CompleteRegistration);
            assertEquals(exception.getMessage(), "Requested " + ClassA.class.getName() + " in " +
                    ComplexClass.class.getName() + " wasn't registered.");
        }
        @Test
        void RegisterClassWithUnregisteredSingletonDependenciesTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ClassWithSingletonDependency.class);
            var exception = assertThrows(RuntimeException.class, di::CompleteRegistration);
            assertEquals(exception.getMessage(), "Requested " + Singletone.class.getName() + " in " +
                    ClassWithSingletonDependency.class.getName() + " wasn't registered.");
        }

        @Test
        void CycleDependenciesMethod() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ComplexClass.class);
            di.Register(ComplexClass2.class);
            di.Register(ClassA.class);
            di.Register(ClassB.class);
            var exception = assertThrows(RuntimeException.class, di::CompleteRegistration);
            assertEquals(exception.getMessage(), "Cyclic dependencies error.");
        }

        @Test
        void InterfaceAndTwoImplementationsTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(Interface.class, ClassA.class);
            var exception = assertThrows(RuntimeException.class, () -> di.Register(Interface.class, ClassB.class));
            assertEquals(exception.getMessage(), "Registering another implementation for " + Interface.class.getName());
        }
    }
}


//TODO:
// Register class with no singleton dependency registered

