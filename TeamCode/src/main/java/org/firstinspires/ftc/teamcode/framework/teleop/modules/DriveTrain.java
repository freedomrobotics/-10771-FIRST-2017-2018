package org.firstinspires.ftc.teamcode.framework.teleop.modules;

import org.firstinspires.ftc.teamcode.framework.teleop.Teleop;

/**
 * Created by Kevin on 5/29/2017.
 */
@org.firstinspires.ftc.teamcode.framework.teleop.Module
public class DriveTrain extends Module {
    public DriveTrain(Teleop t) {
        super(t);
    }

    @Override
    public void loop() {
        float joyThr = teleop.getGamepad()[1].left_stick_y;
        float joyYaw = teleop.getGamepad()[1].right_stick_x;

        if (joyThr > .90f) {
            joyThr = .90f;
        } else if (joyThr < -.90f) {
            joyThr = -.90f;
        }

        float rightPow = joyThr + (joyYaw * .5f);
        float leftPow = joyThr + (-joyYaw * .5f);

        if (rightPow > 1) {
            leftPow -= (rightPow - 1.0);
            rightPow = 1.0f;
        }
        if (leftPow > 1) {
            rightPow -= (leftPow - 1.0);
            leftPow = 1.0f;
        }
        if (rightPow < -1) {
            leftPow += (-1.0 - rightPow);
            rightPow = -1.0f;
        }
        if (leftPow < -1) {
            rightPow += (-1.0 - leftPow);
            leftPow = -1.0f;
        }

        hardware.right1.setPower(rightPow);
        hardware.right2.setPower(rightPow);
        hardware.left1.setPower(leftPow);
        hardware.left2.setPower(leftPow);
    }
}
