package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import java.util.function.DoubleSupplier;

// PLEASE READ!!! THIS CODE IS NOT FINISHED!!!
// The RobotContainer class initializes the drivetrain subsystem and Xbox controller.
// The configureButtonBindings() method sets up the button bindings. 
// Current code sets the default command of the drivetrain to a TankDriveCommand.
// The TankDriveCommand is a hypothetical command that takes in the drivetrain subsystem 
// and two supplier functions representing the left and right joystick inputs from the Xbox controller. 

// TO-DO: replace TankDriveCommand with an appropriate command class that implements the tank drive functionality based on joystick inputs. 
// This command should take in the drivetrain subsystem and control the motors based on the joystick inputs.

public class TankDriveCommand extends CommandBase {
    private final Drivetrain drivetrain;
    private final DoubleSupplier leftSupplier;
    private final DoubleSupplier rightSupplier;

    public TankDriveCommand(Drivetrain drivetrain, DoubleSupplier leftSupplier, DoubleSupplier rightSupplier) {
        this.drivetrain = drivetrain;
        this.leftSupplier = leftSupplier;
        this.rightSupplier = rightSupplier;
        addRequirements(drivetrain); // Require the Drivetrain subsystem
    }

    @Override
    public void execute() {
        // Get left and right joystick values
        double leftSpeed = leftSupplier.getAsDouble();
        double rightSpeed = rightSupplier.getAsDouble();

        // Drive the robot using tank drive
        drivetrain.drive(leftSpeed, rightSpeed);
    }
}

public class RobotContainer {
    // Define drivetrain subsystem
    private final Drivetrain drivetrain;

    // Define Xbox controller
    private final XboxController controller;

    public RobotContainer() {
        // Initialize the drivetrain subsystem
        drivetrain = new Drivetrain();

        // Initialize the Xbox controller on port 0
        controller = new XboxController(0);

        // Configure the button bindings
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        // Example button binding for driving using tank drive
        drivetrain.setDefaultCommand(
            new TankDriveCommand(
                drivetrain,
                () -> controller.getRawAxis(XboxController.Axis.kLeftY.value),
                () -> controller.getRawAxis(XboxController.Axis.kRightY.value)
            )
        );
    }

    // Method to get the autonomous command
    public Command getAutonomousCommand() {
        // Placeholder for autonomous command
        return null;
    }
}
