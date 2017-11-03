package org.firstinspires.ftc.teamcode.framework.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.discovery.AnnotatedModuleClassFilter;
import org.firstinspires.ftc.teamcode.framework.discovery.Discoverer;
import org.firstinspires.ftc.teamcode.framework.teleop.modules.Module;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Kevin on 5/29/2017.
 */
@TeleOp(name="Teleop", group="Grapefruit")  // @Autonomous(...) is the other common choice
public class Teleop extends OpMode {

    public ArrayList<Module> modules;
    public Hardware hardware;

    @Override
    public void init() {
        modules = new ArrayList<Module>();
        hardware = new Hardware(this);
        hardware.init();

        Discoverer discoverer = Discoverer.getInstance();
        discoverer.registerFilter(AnnotatedModuleClassFilter.getInstance());

        discoverer.processAllClasses();

        Set<Class<Module>> moduleClasses = discoverer.getModules();

        for (Class<Module> moduleClass: moduleClasses) {
            Module m = null;
            try {
                m = moduleClass.getDeclaredConstructor(Teleop.class).newInstance(this);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            modules.add(m);
        }

        for (Module m : modules) {
            m.init();
        }
    }

    @Override
    public void init_loop() {
        for (Module m : modules) {
            if (m.isEnabled()) {
                m.init_loop();
            }
        }
    }

    @Override
    public void loop() {
        for (Module m : modules) {
            if (m.isEnabled()) {
                m.loop();
            }
        }
        telemetry.update();
    }

    @Override
    public void stop() {
        for (Module m : modules) {
            m.stop();
        }
    }

    /**
     * Gets the {@link Hardware} object associated with the robot.
     * @return  Hardware object
     * @see     Hardware
     */
    public Hardware getHardware() {
        return hardware;
    }

    public Gamepad[] getGamepad() {
        Gamepad[] gamepads = null;
        gamepads[1] = gamepad1;
        gamepads[2] = gamepad2;
        return gamepads;
    }
}
