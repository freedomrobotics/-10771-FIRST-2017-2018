package org.firstinspires.ftc.teamcode.framework.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.framework.Hardware;
import org.firstinspires.ftc.teamcode.framework.teleop.modules.Module;
import org.reflections.Reflections;

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

        //REGISTER MODULES HERE
        Reflections reflections = new Reflections("org.firstinspires.ftc.teamcode.framework.teleop.modules"); //package to search
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(org.firstinspires.ftc.teamcode.framework.teleop.Module.class); //get annotated classes
        for (Class<?> clazz : annotated) {
            try {
                modules.add((Module)clazz.getDeclaredConstructor(Teleop.class).newInstance(this)); //add it to the list of modules
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
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
    }

    @Override
    public void stop() {
        for (Module m : modules) {
            m.stop();
        }
    }

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
