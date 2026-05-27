package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
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
        follower = PedroConstants.createFollower(hardwareMap);
        follower.update();

        intake = new IntakeSS(hardwareMap);


}
}
