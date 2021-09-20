package ru.skillbench.tasks.javaapi.reflect;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ReflectorImpl implements Reflector {
    private Class<?> clazz;

    public ReflectorImpl() {

    }

    private void checkNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new NullPointerException();
            }
        }
    }

    @Override
    public void setClass(Class<?> clazz) {
//        checkNull(clazz);

        this.clazz = clazz;
    }

    @Override
    public Stream<String> getMethodNames(Class<?>... paramTypes) {
        checkNull(this.clazz, paramTypes);

        ArrayList<String> result = new ArrayList<>();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Class<?>[] paramTypesCurrent = method.getParameterTypes();
            if (Arrays.equals(paramTypesCurrent, paramTypes)) {
                result.add(method.getName());
            }
        }

        return result.stream();
    }

    @Override
    public Stream<Field> getAllDeclaredFields() {
        checkNull(this.clazz);

        Class<?> currentClass = this.clazz;

        ArrayList<Field> result = new ArrayList<>();
        Field[] declaredFields = currentClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                result.add(declaredField);
            }
        }

        while (currentClass.getSuperclass() != null) {
            currentClass = currentClass.getSuperclass();
            declaredFields = currentClass.getDeclaredFields();

            for (Field declaredField : declaredFields) {
                if (!Modifier.isStatic(declaredField.getModifiers())) {
                    result.add(declaredField);
                }
            }
        }

        return result.stream();
    }

    @Override
    public Object getFieldValue(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> currentClass;

        checkNull(fieldName);

        if (this.clazz != null) {
            currentClass = this.clazz;
            Field field = currentClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } else {
            checkNull(target);
            currentClass = target.getClass();
            Field field;
            try {
                field = currentClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException ignored) {

            }

            while (currentClass.getSuperclass() != null) {
                currentClass = currentClass.getSuperclass();
                try {
                    field = currentClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field.get(target);
                } catch (NoSuchFieldException ignored) {

                }
            }

            throw new NoSuchFieldException();
        }
    }

    @Override
    public Object getMethodResult(Object constructorParam, String methodName, Object... methodParams) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<?> constructor;
        Object instance;
        Method method;

        // this method doesn't work

        // at least problem in getting inherited methods

//        Method result -- no such method

//        Caused by: java.lang.reflect.InvocationTargetException
//        at ru.skillbench.tasks.javaapi.reflect.ReflectorImpl.getMethodResult(ReflectorImpl.java:154)
//        at ru.skillbench.tasks.javaapi.reflect.ReflectorTest.testGetMethodResult(ReflectorTest.java:316)
//        at ru.skillbench.tasks.javaapi.reflect.ReflectorTest.testGetMethodResult1NoSuchMethod(ReflectorTest.java:267)
//	...

//        Method result -- nullary constructor and private method
//        Calling a constructor with () or a method with (java.lang.String) failed:

//        Caused by: java.lang.NoSuchMethodException: ru.skillbench.tasks.javaapi.reflect.Reflector$SampleNumber.formatValue([Ljava.lang.Object;)
//        at java.lang.Class.getMethod(Class.java:1773)
//        at ru.skillbench.tasks.javaapi.reflect.ReflectorImpl.getMethodResult(ReflectorImpl.java:149)
//	... 15 more

//        Method result -- public inherited method with a single param
//        Calling a constructor with (java.lang.String) or a method with (java.lang.String) failed:

//        Caused by: java.lang.NoSuchMethodException: java.text.DecimalFormat.parse([Ljava.lang.Object;)
//        at java.lang.Class.getMethod(Class.java:1773)
//        at ru.skillbench.tasks.javaapi.reflect.ReflectorImpl.getMethodResult(ReflectorImpl.java:149)
//	... 15 more

        try {
            if (constructorParam == null) {
                constructor = this.clazz.getConstructor();
                constructor.setAccessible(true);
                instance = constructor.newInstance();
            } else {
                constructor = this.clazz.getConstructor(constructorParam.getClass());
                constructor.setAccessible(true);
                instance = constructor.newInstance(constructorParam);
            }

            if (methodParams.length == 0) {
                try {
                    method = this.clazz.getDeclaredMethod(methodName);
//                    method = instance.getClass().getMethod(methodName);
                } catch (Exception exception) {
                    if (exception instanceof RuntimeException) {
                        throw exception;
                    } else {
                        throw new InvocationTargetException(exception);
                    }
                }
                method.setAccessible(true);
                return method.invoke(instance);
            } else {
                try {
                    method = this.clazz.getDeclaredMethod(methodName, methodParams.getClass());
//                    method = instance.getClass().getMethod(methodName, methodParams.getClass());
                } catch (Exception exception) {
                    if (exception instanceof RuntimeException) {
                        throw exception;
                    } else {
                        throw new InvocationTargetException(exception);
                    }
                }
                method.setAccessible(true);
                return method.invoke(instance, methodParams);
            }
        } catch (InvocationTargetException exception) {
            Exception initialException = (Exception) exception.getCause();
            try {
                throw initialException;
            } catch (RuntimeException runtimeException) {
                throw runtimeException;
            } catch (Exception e) {
                throw exception;
            }
        }
    }
}
