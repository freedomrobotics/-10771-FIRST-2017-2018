package org.firstinspires.ftc.teamcode.framework.discovery;

import java.util.Set;

/**
 * Created by Kevin on 11/2/2017.
 */

public interface ClassFilter {

    /**
     * Clears the result of any previous filtering in preparation for further filtering
     */
    void filterAllClassesStart();

    /**
     * Don't call me, I'll call you.
     *
     * @param clazz Look me in the mirror.
     */
    void filterClass(Class clazz);

    /**
     * Called when a filtering cycle is complete
     */
    void filterAllClassesComplete();

    Set<Class> getClasses();
}