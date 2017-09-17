package org.firstinspires.ftc.teamcode.framework.autonomous.components;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.framework.autonomous.Autonomous;

/**
 * Created by Kevin on 9/15/2017.
 */

public class ComputerVision extends Component {
    public ComputerVision(Autonomous a) {
        super(a);
    }

    private VuforiaLocalizer vuforia;

    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;

    @Override
    public void init() {
        int cameraMonitorViewId = autonomous.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", autonomous.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ATsODcD/////AAAAAVw2lR...d45oGpdljdOh5LuFB9nDNfckoxb8COxKSFX";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);


        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
    }

    @Override
    public void roboStart() {
        relicTrackables.activate();
    }

    public CubeLocation getCubeLocation() {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark == RelicRecoveryVuMark.LEFT) {
            return CubeLocation.LEFT;
        } else if (vuMark == RelicRecoveryVuMark.CENTER) {
            return CubeLocation.CENTER;
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            return CubeLocation.RIGHT;
        } else {
            return CubeLocation.UNKNOWN;
        }
    }

    public CubeLocation getCubeLocationGaruntee(long timeoutmillis) throws InterruptedException {
        long startTime = System.nanoTime();
        long timeout = timeoutmillis*1000;
        long endTime = startTime + timeout;
        while (autonomous.opModeIsActive() && endTime >= System.nanoTime()) {
            CubeLocation location = getCubeLocation();
            if (location != CubeLocation.UNKNOWN) {
                return location;
            } else {
                Thread.sleep(10);
            }
        }
        return CubeLocation.UNKNOWN;
    }

    public enum CubeLocation {
        LEFT,
        CENTER,
        RIGHT,
        UNKNOWN
    }
}
