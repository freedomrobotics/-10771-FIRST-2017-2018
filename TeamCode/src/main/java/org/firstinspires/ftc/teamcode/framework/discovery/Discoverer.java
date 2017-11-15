package org.firstinspires.ftc.teamcode.framework.discovery;

import android.content.Context;

import com.qualcomm.robotcore.util.Util;

import dalvik.system.DexFile;
import org.firstinspires.ftc.robotcore.internal.opmode.InstantRunDexHelper;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.framework.autonomous.autocode.AutoProgram;
import org.firstinspires.ftc.teamcode.framework.teleop.modules.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Kevin on 11/2/2017.
 */

public class Discoverer {
    private static class InstanceHolder
    {
        public static Discoverer theInstance = new Discoverer();
    }
    public static Discoverer getInstance()
    {
        return InstanceHolder.theInstance;
    }

    private static final String TAG = "ClassDiscovery";

    private List<String> packagesAndClassesToIgnore;
    private List<ClassFilter> filters;
    private Context context;
    private DexFile dexFile;
    private Set<Class> modules = null;

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    private Discoverer() {
        try
        {
            this.context = AppUtil.getInstance().getApplication();
            this.dexFile = new DexFile(this.context.getPackageCodePath());
            this.filters = new LinkedList<ClassFilter>();
            this.modules = new HashSet<Class>();
            clearIgnoredList();
        }
        catch (Exception e)
        {
            throw AppUtil.getInstance().unreachable(TAG, e);
        }

    }

    protected void clearIgnoredList() {
        // We ignore certain packages to make us more robust and efficient
        this.packagesAndClassesToIgnore = new ArrayList<String>();
        this.packagesAndClassesToIgnore.addAll(Arrays.asList(
                "com.android.dex",
                "com.google",
                "com.sun.tools",
                "gnu.kawa.swingviews",
                "io.netty",
                "javax.tools",
                "kawa",
                "org.firstinspires.ftc.robotcore.internal.android"
        ));
    }

    public void registerFilter(ClassFilter filter) {
        filters.add(filter);
    }

    private List<String> getAllClassNames() {
        // Load what's built into the APK
        List<String> classNames = new ArrayList<String>(Collections.list(dexFile.entries()));

        // Deal with instant run's craziness
        classNames.addAll(InstantRunDexHelper.getAllClassNames(context));

        return classNames;
    }

    protected List<Class> classNamesToClasses(Collection<String> classNames) {
        List<Class> result = new LinkedList<Class>();
        try {
            for (String className : classNames) {
                // Ignore classes that are in some packages that we know aren't worth considering
                boolean shouldIgnore = false;
                for (String packageName : packagesAndClassesToIgnore) {
                    if (Util.isPrefixOf(packageName, className)) {
                        shouldIgnore = true;
                        break;
                    }
                }
                if (shouldIgnore)
                    continue;

                // Get the Class from the className
                Class clazz;
                try {
                    clazz = Class.forName(className, false, this.getClass().getClassLoader());
                    // RobotLog.dd(TAG, "class %s: loader=%s", className, clazz.getClassLoader().getClass().getSimpleName());
                } catch (NoClassDefFoundError | ClassNotFoundException ex) {
                    // We can't find that class
                    if (className.contains("$")) {
                        // Prevent loading similar inner classes, a performance optimization
                        className = className.substring(0, className.indexOf("$") /* -1 */);
                    }

                    packagesAndClassesToIgnore.add(className);
                    continue;
                }

                // Remember that class
                result.add(clazz);
            }

            return result;
        } finally {
            //do nothing.
        }
    }

    public void processAllClasses()
    {
        clearIgnoredList();
        List<Class> allClasses = classNamesToClasses(getAllClassNames());

        for (ClassFilter f : filters)
        {
            f.filterAllClassesStart();
            for (Class clazz : allClasses)
            {
                f.filterClass(clazz);
            }
            f.filterAllClassesComplete();
            modules.addAll(f.getClasses());
        }
    }

    public Set<Class<Module>> getModules() {
        Set<Class<Module>> realModules = new HashSet<Class<Module>>();
        for(Class c : modules) {
            realModules.add((Class<Module>)c);
        }
        return realModules;
    }

    public Set<Class<AutoProgram>> getAutoPrograms() {
        Set<Class<AutoProgram>> realAutoPrograms = new HashSet<Class<AutoProgram>>();
        for(Class c : modules) {
            realAutoPrograms.add((Class<AutoProgram>)c);
        }
        return realAutoPrograms;
    }
}
