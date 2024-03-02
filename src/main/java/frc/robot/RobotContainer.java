// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // Define Xbox controller
  private XboxController driverController = new XboxController(0);
  
  private final Drivetrain drivetrain;

  public RobotContainer(){
    drivetrain = new Drivetrain();

    // Configure the button bindings
     configureButtonBindings();

  }

  private void configureButtonBindings(){
    // Drive forward when pushing the left and right joysticks forward
  new JoystickButton(driverController,XboxController.Button.kLeftBumper.value).onTrue(new InstantCommand(drivetrain.driveTank(driverController.getLeftY())));
  new JoystickButton(driverController,XboxController.Button.kRightBumper.value).onTrue(new InstantCommand(drivetrain.driveTank(driverController.getRightY())));

  }
}


