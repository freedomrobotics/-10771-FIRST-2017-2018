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
        parameters.vuforiaLicenseKey = "AahfNHb/////AAAAGdUqmr1YykNCmZWL2siX3N474ykLw2BAmnrelfZYmxe3Tocz+OGYc+DikG/A/KMZvTR7gMNvQb3tpVVvKmO0PjjeX9VJyoik8iY83Ck8bWSP1Zx4aaxMaSQNc8WAAhO/9KSE+VFJr1HEU5tPNRUV+Q4ieJkQZhUPCthku2H/94TluzyWS9hoapRtC+H/ASjwELj0eJyW3JhUgXa1LclaZQQiTps5akHhem/S5g1QvQEANipW/rfVuYuYcjL0E7PPfLo4k3gTB333GeBVswfbFJzPMrlgAIViBFklXywpHnP/5uWbAlrfQVWHOec5++ZBQuwAs4SSTIi+lYKrXnCZ+Nsmu4X0h0zO2gXiftymP+w+";
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
