package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Tank Drive
// Uses only TalonSRX motors for both left and right sides.
// Configures TalonSRX motors for brake mode and inverted as needed.
// Implements tank drive logic in the setDriveMotors method.

public class Drivetrain extends SubsystemBase {
    public static TalonSRX driveLeftFrontTalon = new TalonSRX(1);
    public static TalonSRX driveLeftBackTalon = new TalonSRX(2);
    public static TalonSRX driveRightFrontTalon = new TalonSRX(4);
    public static TalonSRX driveRightBackTalon = new TalonSRX(3);

    public Drivetrain() {
        driveLeftFrontTalon.setInverted(false);
        driveLeftBackTalon.setInverted(false);
        driveRightFrontTalon.setInverted(true);
        driveRightBackTalon.setInverted(true);

        driveLeftFrontTalon.setNeutralMode(NeutralMode.Brake);
        driveLeftBackTalon.setNeutralMode(NeutralMode.Brake);
        driveRightFrontTalon.setNeutralMode(NeutralMode.Brake);
        driveRightBackTalon.setNeutralMode(NeutralMode.Brake);
    }

 // Feel free to change power percentages

    public void setDriveMotors(double forward, double turn) {
        SmartDashboard.putNumber(0, forward);
        SmartDashboard.putNumber(0, turn);

        double left = forward;
        double right = forward;

        left -= turn;
        right += turn;

        SmartDashboard.putNumber(0, left);
        SmartDashboard.putNumber(0, right);

        driveLeftFrontTalon.set(ControlMode.PercentOutput, left);
        driveLeftBackTalon.set(ControlMode.PercentOutput, left);
        driveRightFrontTalon.set(ControlMode.PercentOutput, right);
        driveRightBackTalon.set(ControlMode.PercentOutput, right);
    }
}
