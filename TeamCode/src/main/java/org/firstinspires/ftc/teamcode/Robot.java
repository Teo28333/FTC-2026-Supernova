package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSS;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSS;

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

        joinedTelemetry.addData("Alliance", alliance);
        joinedTelemetry.addData("Tag reset cooldown", tagResetCooldownSeconds);
        joinedTelemetry.update();
    }

    public void stop() {
        if (follower != null) {
            follower.setTeleOpDrive(0.0, 0.0, 0.0, true);
            follower.update();
        }
    }
}
