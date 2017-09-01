package org.firstinspires.ftc.teamcode.framework.autonomous.autocode;

import org.firstinspires.ftc.teamcode.framework.autonomous.AutoCode;
import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;

/**
 * Created by Kevin on 9/1/2017.
 */
@AutoCode(name="TestProgram")
public class TestAuto extends AutoProgram {
    public TestAuto(Autonomous a) {
        super(a);
    }

    @Override
    public void run() {
        hardware.right.setPower(1);
        hardware.left.setPower(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hardware.right.setPower(0);
        hardware.left.setPower(0);
    }
}
