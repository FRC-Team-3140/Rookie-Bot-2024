// Import necessary Java libraries and WPILib components
// TO-DO: Update necessary imports, download phoenix 5+6 libraries
import java.io.IOException;
import java.nio.file.Paths;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.TrajectoryUtil.TrajectorySerializationException;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.commands.BobDrive;
import frc.robot.utils.DriveSignal;

// Define the Drivetrain subsystem class extending SubsystemBase
public class Drivetrain extends SubsystemBase {

  // Define the drive mode (Normal)
  private DriveMode drivemode = Constants.DriveConstants.DriveMode.Normal;

  // Define TalonSRX motor controllers for the drivetrain
  public TalonSRX leftLead = new TalonSRX(4);
  public TalonSRX leftFollow1 = new TalonSRX(5);
  public TalonSRX leftFollow2 = new TalonSRX(6);

  public TalonSRX rightLead = new TalonSRX(1);
  public TalonSRX rightFollow1 = new TalonSRX(2);
  public TalonSRX rightFollow2 = new TalonSRX(3);

  // Define Pigeon IMU for obtaining heading and pitch information
  public WPI_Pigeon2 pigeon = new WPI_Pigeon2(7);

  // Define odometry object for tracking robot position
  private DifferentialDriveOdometry odometry;

  // Define heading variable
  Rotation2d heading = new Rotation2d(Units.degreesToRadians(pigeon.getAngle()));

  // Define ramp rate and pitch variables
  private static final double rampRate = 0.25;
  private static double previousPitch = 0.0;
  private static double deltaPitch = 0.0;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // Set motor configurations, inversions, neutral modes, ramp rates, current limits, and followers
    setMotorConfigsToDefault();
    setMotorInversions();
    setDefaultMotorNeutralModes();
    setMotorRampRates();
    setMotorCurrentLimits();
    setFollowers();

    // Initialize odometry object
    odometry = new DifferentialDriveOdometry(getHeading(), getLeftLeadDriveDistanceMeters(), getRightLeadDriveDistanceMeters());
  }

  // Method to set the default command for the subsystem
  public void initDefaultCommand() {
    setDefaultCommand(new BobDrive());
  }

  // Method called periodically
  @Override
  public void periodic() {
    // Update odometry and pitch information
    odometry.update(getHeading(), getLeftLeadDriveDistanceMeters(), getRightLeadDriveDistanceMeters());
    updateDeltaPitch();
  }
  
  // Method to set motor configurations to default
  private void setMotorConfigsToDefault() {
    // Set motor configurations to factory default
    leftLead.configFactoryDefault();
    leftFollow1.configFactoryDefault();
    leftFollow2.configFactoryDefault();
    rightLead.configFactoryDefault();
    rightFollow1.configFactoryDefault();
    rightFollow2.configFactoryDefault();
  }

  // Method to set motor inversions
  private void setMotorInversions() {
    // Set motor inversions
    boolean right = true;
    boolean left = false;

    leftLead.setInverted(left);
    leftFollow1.setInverted(left);
    leftFollow2.setInverted(left);

    rightLead.setInverted(right);
    rightFollow1.setInverted(right);
    rightFollow2.setInverted(right);
  }

  // Method to set default motor neutral modes
  private void setDefaultMotorNeutralModes() {
    // Set neutral mode to coast for all motors
    leftLead.setNeutralMode(NeutralMode.Coast);
    leftFollow1.setNeutralMode(NeutralMode.Coast);
    leftFollow2.setNeutralMode(NeutralMode.Coast);

    rightLead.setNeutralMode(NeutralMode.Coast);
    rightFollow1.setNeutralMode(NeutralMode.Coast);
    rightFollow2.setNeutralMode(NeutralMode.Coast);
  }

  // Method to set motor ramp rates
  private void setMotorRampRates() {
    // Set open-loop ramp rates for all motors
    leftLead.configOpenloopRamp(rampRate);
    leftFollow1.configOpenloopRamp(rampRate);
    leftFollow2.configOpenloopRamp(rampRate);

    rightLead.configOpenloopRamp(rampRate);
    rightFollow1.configOpenloopRamp(rampRate);
    rightFollow2.configOpenloopRamp(rampRate);
  }

  // Method to set motor current limits
  private void setMotorCurrentLimits(){
    // Configure supply current limits for all motors
    SupplyCurrentLimitConfiguration currentLimit = new SupplyCurrentLimitConfiguration(true, 30, 30, 0.0);
    leftLead.configSupplyCurrentLimit(currentLimit);
    leftFollow1.configSupplyCurrentLimit(currentLimit);
    leftFollow2.configSupplyCurrentLimit(currentLimit);

    rightLead.configSupplyCurrentLimit(currentLimit);
    rightFollow1.configSupplyCurrentLimit(currentLimit);
    rightFollow2.configSupplyCurrentLimit(currentLimit);
  }

  // Method to set neutral mode for all motors
  public void setNeutralMode(NeutralMode neutralMode) {
    // Set neutral mode for all motors
    leftLead.setNeutralMode(neutralMode);
    leftFollow1.setNeutralMode(neutralMode);
    leftFollow2.setNeutralMode(neutralMode);
    rightLead.setNeutralMode(neutralMode);
    rightFollow1.setNeutralMode(neutralMode);
    rightFollow2.setNeutralMode(neutralMode);
  }

  // Method to drive the robot using left and right control modes
  public void drive(ControlMode controlMode, double left, double right) {
    // Set left and right motor control modes and speeds
