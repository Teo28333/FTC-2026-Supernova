package org.firstinspires.ftc.teamcode.OpMode.Teleop;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

abstract class BaseTeleOp extends OpMode {

    private final ElapsedTime tagResetTimer = new ElapsedTime();

    private Robot robot;
    private JoinedTelemetry joinedTelemetry;
    private TelemetryManager telemetryManager;

    protected abstract CompetitionTeleopRobot.Alliance getAlliance();

    protected abstract double getTagResetCooldownSeconds();

    @Override
    public void init() {
        robot = new CompetitionTeleopRobot();
        robot.setAlliance(getAlliance());
        robot.init(hardwareMap);

        telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
        joinedTelemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
        tagResetTimer.reset();
    }

    @Override
    public void start() {
        robot.startTeleop();
    }

    @Override
    public void loop() {
        robot.teleopLoop(gamepad1, gamepad2, tagResetTimer, getTagResetCooldownSeconds(), joinedTelemetry, telemetryManager);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
