package org.firstinspires.ftc.teamcode.OpMode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpMode.Teleop.BaseTeleOp;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Teleop Red", group = "Teleop")

public class TeleopRed extends BaseTeleOp {
    @Override
    protected Robot.Alliance getAlliance() {
        return Robot.Alliance.RED;
    }

    @Override
    protected double getTagResetCooldownSeconds() {
        return 1.0;
    }
}
