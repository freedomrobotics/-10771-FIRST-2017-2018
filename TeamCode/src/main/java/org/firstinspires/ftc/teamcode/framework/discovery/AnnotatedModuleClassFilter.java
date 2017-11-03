package org.firstinspires.ftc.teamcode.framework.discovery;

import android.content.Context;

import com.qualcomm.robotcore.util.ClassUtil;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.framework.teleop.modules.Module;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kevin on 11/2/2017.
 */

public class AnnotatedModuleClassFilter implements ClassFilter {

    public static final String TAG = "ModuleRegistration";

    private Context context;

    private final Set<Class> filteredAnnotatedModuleClasses = new HashSet<Class>();

    private static class InstanceHolder {
        public static AnnotatedModuleClassFilter theInstance = new AnnotatedModuleClassFilter();
    }
    public static AnnotatedModuleClassFilter getInstance() {
        return InstanceHolder.theInstance;
    }

    private AnnotatedModuleClassFilter() {
        this.context = AppUtil.getDefContext();
    }

    boolean checkModuleClassConstraints(Class clazz) {
        // If the class doesn't extend OpMode, that's an error, we'll ignore the class
        if (!isModule(clazz))
        {
            return false;
        }

        // If it's not 'public', it can't be loaded by the system and won't work. We report
        // the error and ignore the class
        if (!Modifier.isPublic(clazz.getModifiers()))
        {
            return false;
        }

        return true;
    }

    @Override
    public void filterClass(Class clazz) {

        // Is this an annotated OpMode?
        boolean isModule = clazz.isAnnotationPresent(org.firstinspires.ftc.teamcode.framework.teleop.Module.class);

        // If it's not a module, it's not interesting to us.
        if (!isModule)
            return;

        // If it doesn't fit the constraints, we don't care about it.
        if (!checkModuleClassConstraints(clazz)) {
            return;
        }

        filteredAnnotatedModuleClasses.add(clazz);
    }

    @Override public void filterAllClassesStart() {
        filteredAnnotatedModuleClasses.clear();
    }

    @Override public void filterAllClassesComplete() {
        // Nothing to do
    }

    private boolean isModule(Class clazz) {
        return ClassUtil.inheritsFrom(clazz, Module.class);
    }

    @Override
    public Set<Class> getClasses() {
        return filteredAnnotatedModuleClasses;
    }
}
