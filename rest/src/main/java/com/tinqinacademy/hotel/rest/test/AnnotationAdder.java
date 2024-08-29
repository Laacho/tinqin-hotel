package com.tinqinacademy.hotel.rest.test;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.sun.tools.attach.VirtualMachine;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.security.ProtectionDomain;


//@Component
//public class AnnotationAdder implements ApplicationRunner {
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        String[] controllerNames = {
//                "com.tinqinacademy.hotel.rest.controllers.hotel.HotelController",
//                "com.tinqinacademy.hotel.rest.controllers.system.SystemController"
//        };
//        for(String controllerName:controllerNames) {
//            Class<?> controllerClass = Class.forName(controllerName);
//            for (Method method : controllerClass.getDeclaredMethods()) {
//                if(!method.isAnnotationPresent(Process.class)) {
//
//                    Method addAnnotation = Proxy.getInvocationHandler(method)
//                            .getClass()
//                            .getDeclaredMethod("addAnnotation", Class.class);
//                    addAnnotation.setAccessible(true);
//                    addAnnotation.invoke(Proxy.getInvocationHandler(method), Process.class);
//                }
//            }
//        }
//
//
//    }
//}
//@Component
//public class MyClassFileTransformer implements ClassFileTransformer, ApplicationRunner {
//    @Override
//    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
//                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
//            throws IllegalClassFormatException {
//        if (className.equals("com/tinqinacademy/hotel/rest/controllers/system/SystemController.java")) {
//            try {
//                ClassPool cp = ClassPool.getDefault();
//                CtClass cc = cp.get("com.tinqinacademy.hotel.rest.controllers.system.SystemController");
//
//                for (CtMethod method : cc.getDeclaredMethods()) {
//                    AnnotationsAttribute attr = new AnnotationsAttribute(cc.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
//                    Annotation annotation = new Annotation("com.tinqinacademy.hotel.rest.test.Process", cc.getClassFile().getConstPool());
//                    attr.addAnnotation(annotation);
//                    method.getMethodInfo().addAttribute(attr);
//                }
//                return cc.toBytecode();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return classfileBuffer;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        transform(null,"com/tinqinacademy/hotel/rest/controllers/system/SystemController.java",null,null,null);
//    }
//}
@Component
public class AnnotationAdder implements ApplicationRunner {
    private static void addProcessAnnotationToMethods() throws Exception {
//        for (CtMethod method : ctClass.getDeclaredMethods()) {
//            if (ctClass.isFrozen()) {
//                ctClass.defrost();
//            }
//            MethodInfo methodInfo = method.getMethodInfo();
//            ConstPool constPool = methodInfo.getConstPool();
//            AnnotationsAttribute attr =new AnnotationsAttribute(constPool,AnnotationsAttribute.visibleTag);
//            Annotation annotation =new Annotation(Process.class.getName(),constPool);
//            attr.addAnnotation(annotation);
//            methodInfo.addAttribute(attr);
//            ctClass.writeFile();
//        }
        try {
            // Създаване на ClassPool и вземане на класа
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get("com.tinqinacademy.hotel.rest.controllers.hotel.HotelController");

            CtMethod method = ctClass.getDeclaredMethod("bookRoom");
            MethodInfo methodInfo = method.getMethodInfo();
            ConstPool constPool = methodInfo.getConstPool();


            AnnotationsAttribute attr = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
            if (attr == null) {
                attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
            }

            Annotation newAnnotation = new Annotation("com.tinqinacademy.hotel.rest.test.Process", constPool);
            attr.addAnnotation(newAnnotation);
            methodInfo.addAttribute(attr);
            ctClass.writeFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));

        String packageName = "com.tinqinacademy.hotel.rest.controllers";
        String[] controllerClasses = {".hotel.HotelController", ".system.SystemController"};

            addProcessAnnotationToMethods();

    }
}


