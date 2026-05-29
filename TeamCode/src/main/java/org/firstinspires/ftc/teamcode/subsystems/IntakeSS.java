package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Robot.HardwareWriteCache;

public class IntakeSS implements SubsystemInterface {
    // ── Hardware ──────────────────────────────────────────────────────────────
    private DcMotorEx intake1;
    private DcMotorEx intake2;
    private Servo gate;
    private Servo led;
    private Telemetry telemetry;

    // ── Timers ────────────────────────────────────────────────────────────────
    private final ElapsedTime motor1Timer  = new ElapsedTime();
    private final ElapsedTime motor2Timer  = new ElapsedTime();
    private final ElapsedTime gateFailsafe = new ElapsedTime();

    // ── Sensor readings ───────────────────────────────────────────────────────
    private double motor1Current = 0.0;
    private double motor2Current = 0.0;

    // ── Outputs ───────────────────────────────────────────────────────────────
    private double firstMotorPow  = 0.0;
    private double secondMotorPow = 0.0;
    private double gatePos = 0.0;
    private double ledColor = 0.0;

    // ── State ─────────────────────────────────────────────────────────────────
    private boolean motor1Stopped = false;
    private boolean motor2Stopped = false;
    private boolean needToTurn    = false;
    private boolean isIntaking = false;
    private boolean isTransferring = false;
    private boolean gateClosingForIntake = false;
    private boolean gateOpeningForTransfer = false;
    private boolean showTelemetry = false;
    private String transferBlockReason = "idle";
    private BallState ballState = BallState.EMPTY;
    private TransferState transferState = TransferState.IDLE;

    public enum BallState {
        EMPTY,
        ONE,
        FULL
    }

    public enum TransferState {
        IDLE,
        WAITING_GATE,
        WAITING_READY,
        FEEDING
    }
    @Override
    public void read() {
        motor2Current = intake2.getCurrent(CurrentUnit.MILLIAMPS);
        if (motor2Stopped || isTransferring) {
            motor1Current = intake1.getCurrent(CurrentUnit.MILLIAMPS);
        } else {
            motor1Current = 0.0;
        }

    }

    @Override
    public void write() {
        HardwareWriteCache.setMotorPower(intake1, firstMotorPow);
        HardwareWriteCache.setMotorPower(intake2, secondMotorPow);
        HardwareWriteCache.setServoPosition(gate, gatePos);

        HardwareWriteCache.setServoPosition(led, ledColor);

    }

    @Override
    public void update() {
        read();
        write();
        telemetry();

    }

    @Override
    public void telemetry() {
        if (showTelemetry && telemetry != null) {
            telemetry.addData("Intake motor 1 current", motor1Current);
            telemetry.addData("Intake motor 2 current", motor2Current);
            telemetry.addData("Intake motor 1 power", firstMotorPow);
            telemetry.addData("Intake motor 2 power", secondMotorPow);
            telemetry.addData("Gate position", gatePos);
            telemetry.addData("LED color", ledColor);
            telemetry.addData("Ball state", ballState);
            telemetry.addData("Transfer state", transferState);
            telemetry.addData("Transfer block reason", transferBlockReason);
        }

    }

    public void Start(){
        firstMotorPow  = 1.0;//a modifier pour pas exploser le robot(Le Intake)
        secondMotorPow = -1.0;//a modifier pour pas exploser le robot(Le Intake)
        isIntaking = true;
        isTransferring = true;
        gateClosingForIntake = true;
        gateOpeningForTransfer = true;
        transferState  = TransferState.IDLE;
        transferBlockReason = "idle";
    }

    public void Stop(){
        firstMotorPow  = 0.0;
        secondMotorPow = 0.0;
        isIntaking = false;
        isTransferring = false;
        gateClosingForIntake = false;
        gateOpeningForTransfer = false;
        needToTurn     = false;
        transferState  = TransferState.IDLE;
        transferBlockReason = "idle";
    }
}
