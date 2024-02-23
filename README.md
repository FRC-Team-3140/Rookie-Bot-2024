# Team 3140 - [Robot Name]

  Welcome to the repository for Flagship 3140's rookie bot code for the 2024 FRC season. This README provides an overview of our robot, its subsystems, and how to use this codebase.

## References

 * https://www.youtube.com/@UnqualifiedQuokkasRi3D/featured
 * Programming Style Guide: https://docs.google.com/document/d/1gFDDgxJKg1U1g2qiMRLbdnMCAiIlr2gexJFJaxHWk/edit?usp=sharing

# Actions

  Our robot, [insert robot name here], is designed to: pick up notes from the ground, pick up notes from the human player station, shoot notes in the speaker and amp, and drive under the stage. It has an arm subsystem, shooter subsystem, an intake subsystem (the shooter and intake are grouped together though), and tank drive.

  
  Teleop strategy: During teleop, the player will control the robot to pick up notes on the ground on its side of the field and also go across the field to pick up notes dropped by the human player. The robot will then shoot the notes in both the speaker and the amp until the endgame buzzer goes off, after which the player will not have the robot climb the chain.

  Auto strategy (to be determined): [insert auto strategy here]

# Subsystems

## Arm
  
  The arm uses two motors to move up and down, which are configured as a group, allowing the intake/shooter subsystem to move to the ground to pick up the notes and move back up to shoot them.

  | Component ID | Interface | Connection | Role |
|---|---|---|---|
| Arm Motor 1 | CANSparkMax |CAN ID: | A **motor** for controlling the arm |
| Arm Motor 2 | CANSparkMax |CAN ID: | A **motor** for controlling the arm |
| Arm Encoder | DutyCycleEncoder | PWM Ports 0 & 1 | An **absolute encoder** for sensing the arm angle |

## Shooter/Intake

  The intake and shooter are both in one subsystem, connected to the top of the arm and using one motor. It picks up a ground note using 2 sets of wheels, then another set of wheels pushes the note into a shooter where it is then shot into the air through another set of wheels.

| Component ID | Interface | Connection | Role |
|---|---|---|---|
| Intake Motor | CANSparkMax | CAN ID:   | A **motor** for controlling the intake. |
| Shooter Motor | CANSparkMax | CAN ID:  | A **motor** for controlling the shooter |
| Shooter Motor | CANSparkMax | CAN ID:  | A **motor** for controlling the shooter |

## Drivetrain:

  This robot uses tank drive to move, and it includes 4 TalonSRX motors, white/80A durometer HiGRip wheels, and an 8.46:1 gear ratio.

  | Component ID | Interface | Connection | Role |
|---|---|---|---|
| Turn Motor Front Left | TalonSRX | CAN ID:  | A **motor** and **encoder** for driving left |
| Drive Motor Front Left | TalonSRX | CAN ID:  | A **motor** and **encoder** for driving right |

## Getting Started
To deploy and run this code on the robot, follow these steps:

1. Clone this repository to your development environment.
2. Open the project in your preferred IDE (we use WPIlib VSCode).
3. Ensure that all dependencies are correctly installed and configured.
4. Build the project to ensure there are no compilation errors.
5. Deploy the code to your robot using your preferred deployment method (we use Ethernet).
6. Test each subsystem to verify functionality.


## Contributing
If you would like to contribute to the robot code, please follow these guidelines:

- Do not code directly in the main branch, nor committ to main.
- Make your changes and ensure that the code compiles without errors.
- Test your changes thoroughly.
- Submit a pull request, describing the changes you've made and any relevant information.
