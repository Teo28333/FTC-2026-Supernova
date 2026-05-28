package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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

    // ── State ─────────────────────────────────────────────────────────────────
    private boolean motor1Stopped = false;
    private boolean motor2Stopped = false;
    private boolean needToTurn    = false;
    private boolean isIntaking = false;
    private boolean isTransferring = false;
    private boolean gateClosingForIntake = false;
    private boolean gateOpeningForTransfer = false;
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

    }

    @Override
    public void write() {

    }

    @Override
    public void update() {

    }

    @Override
    public void telemetry() {

    }

    public void Start(){
        firstMotorPow  = 1.0;
        secondMotorPow = 1.0;
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
