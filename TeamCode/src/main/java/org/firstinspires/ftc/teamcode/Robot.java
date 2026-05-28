package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSS;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSS;

import java.util.IdentityHashMap;
import java.util.Map;

public class Robot {
    public enum Alliance {
        BLUE,
        RED
    }

    private Alliance alliance = Alliance.BLUE;

    private Context appContext;
    private Follower follower;
    private IntakeSS intake;
    private ElevatorSS lift;

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    public void init(HardwareMap hardwareMap) {
        appContext = hardwareMap.appContext;
        follower = Constants.createFollower(hardwareMap);
        follower.update();

        intake = new IntakeSS();
        lift = new ElevatorSS();
    }

    public void startTeleop() {
        follower.startTeleOpDrive();
    }

    public void teleopLoop(Gamepad gamepad1, Gamepad gamepad2, ElapsedTime tagResetTimer,
                           double tagResetCooldownSeconds, JoinedTelemetry joinedTelemetry,
                           TelemetryManager telemetryManager) {
        intake.read();
        lift.read();

        follower.setTeleOpDrive(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                true
        );
        follower.update();

        intake.update();
        lift.update();
        intake.write();
        lift.write();
        HardwareWriteCache.flush();

        joinedTelemetry.addData("Alliance", alliance);
        joinedTelemetry.addData("Tag reset cooldown", tagResetCooldownSeconds);
        joinedTelemetry.update();
    }

    public void stop() {
        if (follower != null) {
            follower.setTeleOpDrive(0.0, 0.0, 0.0, true);
            follower.update();
        }
        HardwareWriteCache.clear();
    }

    public static final class HardwareWriteCache {
        private static final Map<DcMotorEx, Double> motorPowerWrites = new IdentityHashMap<>();
        private static final Map<Servo, Double> servoPositionWrites = new IdentityHashMap<>();

        private HardwareWriteCache() {
        }

        public static void setMotorPower(DcMotorEx motor, double power) {
            if (motor != null) {
                motorPowerWrites.put(motor, power);
            }
        }

        public static void setServoPosition(Servo servo, double position) {
            if (servo != null) {
                servoPositionWrites.put(servo, position);
            }
        }

        public static void flush() {
            for (Map.Entry<DcMotorEx, Double> write : motorPowerWrites.entrySet()) {
                write.getKey().setPower(write.getValue());
            }

            for (Map.Entry<Servo, Double> write : servoPositionWrites.entrySet()) {
                write.getKey().setPosition(write.getValue());
            }

            clear();
        }

        public static void clear() {
            motorPowerWrites.clear();
            servoPositionWrites.clear();
        }
    }
}
