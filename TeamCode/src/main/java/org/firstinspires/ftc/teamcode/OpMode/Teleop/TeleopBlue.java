package org.firstinspires.ftc.teamcode.OpMode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Teleop Blue", group = "Teleop")
public class TeleopBlue extends BaseTeleOp {
    @Override
    protected Robot.Alliance getAlliance() {
        return Robot.Alliance.BLUE;
    }

    @Override
    protected double getTagResetCooldownSeconds() {
        return 1.0;
    }
}
