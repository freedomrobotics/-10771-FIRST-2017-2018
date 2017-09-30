package org.firstinspires.ftc.teamcode.framework.autonomous.autocode;

import org.firstinspires.ftc.teamcode.framework.autonomous.AutoCode;
import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;
import org.firstinspires.ftc.teamcode.framework.autonomous.components.AutoComponents;

/**
 * Created by Kevin on 9/1/2017.
 */
@AutoCode(name="TestProgram")
public class TestAuto extends AutoProgram {
    public TestAuto(Autonomous a) {
        super(a);
    }

    @Override
    public void run() throws InterruptedException {
        AutoComponents.driveTrain.forward(1000); //drive forward for 1 second
    }
}
