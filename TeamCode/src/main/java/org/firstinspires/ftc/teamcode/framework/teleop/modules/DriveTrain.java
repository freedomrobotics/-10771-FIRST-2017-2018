package org.firstinspires.ftc.teamcode.framework.teleop.modules;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.framework.teleop.Teleop;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.toRadians;

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

        boolean robocentric = true;
        int robocentricCounter = 0;


        //do state toggle (left_stick_button to toggle robo/field centric)
        if (teleop.getGamepad()[1].left_stick_button && robocentricCounter == 0) {
            robocentric = false;
            robocentricCounter = 1;
        } else if (!teleop.getGamepad()[1].left_stick_button && robocentricCounter == 1) {
            robocentric = false;
            robocentricCounter = 2;
        } else if (teleop.getGamepad()[1].left_stick_button && robocentricCounter == 2) {
            robocentric = true;
            robocentricCounter = 3;
        } else if (!teleop.getGamepad()[1].left_stick_button && robocentricCounter == 3) {
            robocentric = true;
            robocentricCounter = 0;
        }

        if (robocentric) {
            teleop.telemetry.addData("Centric", "Robot");
        } else {
            teleop.telemetry.addData("Centric", "Field");
        }


        double vD = Math.sqrt(Math.pow(teleop.getGamepad()[1].left_stick_x, 2)+Math.pow(teleop.getGamepad()[1].left_stick_y, 2));
        double thetaD = Math.atan2(teleop.getGamepad()[1].left_stick_y, teleop.getGamepad()[1].left_stick_x);
        double vTheta = teleop.getGamepad()[1].right_stick_x;

        if (!robocentric) { //do additional field-centric calculation
            vTheta += Math.toRadians(hardware.gyro.getIntegratedZValue()%360);
        }


        //calculate powers (that's some fun trig stuff)
        double frontleft = vD * Math.sin(thetaD + Math.PI / 4) + vTheta;
        double frontright = vD * Math.cos(thetaD + Math.PI / 4) - vTheta;
        double backleft = vD * Math.cos(thetaD + Math.PI / 4) + vTheta;
        double backright = vD * Math.sin(thetaD + Math.PI / 4) - vTheta;

        //clamp motor speed values

        List<Double> powers = Arrays.asList(frontleft, frontright, backleft, backright);
        double minPower = Collections.min(powers);
        double maxPower = Collections.max(powers);
        double maxMag = Math.max(Math.abs(minPower), Math.abs(maxPower));

        if (maxMag > 1.0) {   //we need to scale these down, they are too big, and will cause errors!
            for (int i = 0; i < powers.size(); i++) {
                powers.set(i, powers.get(i) / maxMag); //will scale the largest to 1, and the smaller ones proportionally.
            }
        }

        //set all motor powers using the values stored in the powers list.
        hardware.frontLeft.setPower(powers.get(0));
        hardware.frontRight.setPower(powers.get(1));
        hardware.backLeft.setPower(powers.get(2));
        hardware.backRight.setPower(powers.get(3));
    }
}
