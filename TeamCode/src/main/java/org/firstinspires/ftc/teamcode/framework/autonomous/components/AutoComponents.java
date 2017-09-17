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


    public static void init() {
        computerVision = new ComputerVision(autonomous);
        driveTrain = new DriveTrain(autonomous);

        computerVision.init();
        driveTrain.init();
    }
}
