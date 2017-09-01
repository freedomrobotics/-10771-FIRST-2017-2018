package org.firstinspires.ftc.teamcode.framework.autonomous;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Kevin on 5/29/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoCode {
    String name();
}
