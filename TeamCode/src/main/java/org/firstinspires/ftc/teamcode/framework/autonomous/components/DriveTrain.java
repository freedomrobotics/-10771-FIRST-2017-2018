package org.firstinspires.ftc.teamcode.framework.autonomous.components;

import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kevin on 9/15/2017.
 */

public class DriveTrain extends Component {
    public DriveTrain(Autonomous a) {
        super(a);
    }

    @Override
    public void init() {
        //initialize drivetrain parameters here
    }

    /**
     * Drive forward for a number of milliseconds
     * @param ms The number of milliseconds to drive forward for.
     * @throws InterruptedException
     */
    public void forward (int ms) throws InterruptedException {
        DriveParams params = calcParams(1, 0, 0);
        drive(params.vD, params.thetaD, params.vTheta);
        Thread.sleep(ms);
        DriveParams stop = calcParams(0, 0, 0);
        drive(stop.vD, stop.thetaD, stop.vTheta);
    }

    /**
     * Calculates mechanum drive train parameters.
     * @param frontback The vector for forwards/backwards
     * @param leftright The vector for left/right
     * @param turnspeed The velocity for turning
     * @return The parameters used in mechanum drive.
     * @see DriveTrain.DriveParams
     */
    public DriveTrain.DriveParams calcParams(double frontback, double leftright, double turnspeed) {
        double vD = Math.sqrt(Math.pow(frontback, 2)+Math.pow(leftright, 2));
        double thetaD = Math.atan2(frontback, leftright);
        double vTheta = turnspeed;

        return new DriveParams(vD, thetaD, vTheta);
    }

    /**
     * Drives using low level mechanum parameters.
     * @param vD      The overall speed of the drive
     * @param thetaD  The arctan of left/right and forward/backwards
     * @param vTheta  The velocity to turn
     */
    public void drive(double vD, double thetaD, double vTheta) {
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

    public class DriveParams {

        public double vD;
        public double thetaD;
        public double vTheta;

        /**
         * Store Drive Parameters
         * @param vD      The overall speed of the drive
         * @param thetaD  The arctan of left/right and forward/backwards
         * @param vTheta  The velocity to turn
         */
        public DriveParams(double vD, double thetaD, double vTheta) {
            this.vD = vD;
            this.thetaD = thetaD;
            this.vTheta = vTheta;
        }
    }
}
