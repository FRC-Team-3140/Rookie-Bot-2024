package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
    private final Drivetrain m_drivetrain;
    private final XboxController m_controller;

    public RobotContainer() {
        m_drivetrain = new Drivetrain();
        m_controller = new XboxController(0); // Assuming the Xbox controller is plugged into port 0

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        // Example button bindings
        JoystickButton buttonA = new JoystickButton(m_controller, XboxController.Button.kA.value);

        buttonA.whenPressed(
            () -> m_drivetrain.setDriveMotors(0.5, 0.0),
            m_drivetrain); // Example: Move forward at 50% power when pressed

        buttonA.whenReleased(
            () -> m_drivetrain.setDriveMotors(0.0, 0.0),
            m_drivetrain); // Stop when released

        // Add more button bindings as needed for other controls
    }

    public Command getAutonomousCommand() {
        // We don't have an autonomous command in this example, so return null
        return null;
    }
}
