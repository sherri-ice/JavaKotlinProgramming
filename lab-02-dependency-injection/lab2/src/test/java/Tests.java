import DependencyInjector.DependencyInjectorImpl;
import org.junit.jupiter.api.*;

import DependencyInjector.IDependencyInjector;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    @Nested
    class BasicTests {
        @org.junit.jupiter.api.Test
        void BasicTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ClassA.class);
            di.Register(ClassB.class);
            di.CompleteRegistration();
            assertEquals(di.Resolve(ClassA.class).getClass(), ClassA.class);
            assertEquals(di.Resolve(ClassB.class).getClass(), ClassB.class);
        }

        @Test
        void TwoClassesTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(ClassA.class);
            di.CompleteRegistration();
            ClassA firstClass = (ClassA) di.Resolve(ClassA.class);
            ClassA secondClass = (ClassA) di.Resolve(ClassA.class);
            assertNotEquals(firstClass, secondClass);
            assertEquals(firstClass.getClass(), secondClass.getClass());
        }

        @Test
        void SingletoneTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(Singletone.class);
            di.CompleteRegistration();
            Singletone first = (Singletone) di.Resolve(Singletone.class);
            Singletone second = (Singletone) di.Resolve(Singletone.class);
            assertEquals(first.getClass(), second.getClass());
            assertEquals(first, second);
        }
    }

    @Nested
    class NotValidCases {
        @Test
        void RegisterAfterRegistrationCompleteTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            di.Register(Singletone.class);
            di.CompleteRegistration();
            Exception exception = assertThrows(RuntimeException.class, () -> di.Register(ClassA.class));
        }

        @Test
        void AbstractClassRegistrationTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            Exception exception = assertThrows(RuntimeException.class, () -> di.Register(AbstractClass.class));
        }

        @Test
        void InterfaceWithoutImplementationTest() {
            IDependencyInjector di = new DependencyInjectorImpl();
            Exception exception = assertThrows(RuntimeException.class, () -> di.Register(Interface.class));
        }
    }
}


//TODO:
// Declare injectable object as abstract
// Many injectable constructors
// Dependencies (complex)
// Interface and other implementation
// Attempt to register two implementations
// Private constructor


