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
    public static TalonSRX driveLeftTalon = new TalonSRX(1);
    public static TalonSRX driveRightTalon = new TalonSRX(2);

    public Drivetrain() {
        driveLeftTalon.setInverted(false);
        driveRightTalon.setInverted(true);
        driveLeftTalon.setNeutralMode(NeutralMode.Brake);
        driveRightTalon.setNeutralMode(NeutralMode.Brake);
    }

    public void setDriveMotors(double forward, double turn) {
        SmartDashboard.putNumber("drive forward power (%)", forward);
        SmartDashboard.putNumber("drive turn power (%)", turn);

        double left = forward;
        double right = forward;

        left -= turn;
        right += turn;

        SmartDashboard.putNumber("drive left power (%)", left);
        SmartDashboard.putNumber("drive right power (%)", right);

        driveLeftTalon.set(ControlMode.PercentOutput, left);
        driveRightTalon.set(ControlMode.PercentOutput, right);
    }
}