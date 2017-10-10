package org.firstinspires.ftc.teamcode.framework.autonomous.components;

import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;

/**
 * Created by Kevin on 9/15/2017.
 */

public class AutoComponents {

    private static Autonomous autonomous;
    public static DriveTrain driveTrain;
    public static ComputerVision computerVision;

    public AutoComponents(Autonomous a) {
        this.autonomous = a;
    }

    /**
     * Initializes all components. New components must be registered here.
     */
    public static void init() {
        computerVision = new ComputerVision(autonomous);
        driveTrain = new DriveTrain(autonomous);

        computerVision.init();
        driveTrain.init();
    }

    /**
     * Runs all components. New components must be added here.
     */
    public static void roboStart() {
        computerVision.roboStart();
        driveTrain.roboStart();
    }
}
