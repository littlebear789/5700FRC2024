package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

//import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;


    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kStart.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kBack.value);
    //private final JoystickButton robotReset = new JoystickButton(driver, XboxController.Button.kRightStick.value);

    /*Intake*/
    private final Intake intake = new Intake();
    private final JoystickButton intakeToggle = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

    /*Auto Chooser */
    private final SendableChooser<Command> autoChooser;

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();

    /* Motor Buttoms */
    private final TalonSRXMotors talonSRXMotors = new TalonSRXMotors();
    private final JoystickButton motor1F = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton motorBT = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton motorBT2 = new JoystickButton(driver, XboxController.Button.kY.value);




    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );



        autoChooser = AutoBuilder.buildAutoChooser();

        // Another option that allows you to specify the default auto by its name
     // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

        SmartDashboard.putData("Auto Chooser", autoChooser);


        // Configure the button bindings
        configureButtonBindings();

    }



    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */

    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));

        motor1F.whileTrue(new SetTalonSpeed(talonSRXMotors,1) );
        motor1F.whileFalse(new SetTalonSpeed(talonSRXMotors,0) );
        motorBT.onTrue(new ToggleTalons(talonSRXMotors,1));
        motorBT2.onTrue(new ToggleTalons(talonSRXMotors,0) );
        new POVButton(driver, 0).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(0)));
        new POVButton(driver, 90).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(270)));
        new POVButton(driver, 270).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(90)));
        new POVButton(driver, 180).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(180)));

        intakeToggle.onTrue(new SetIntake(intake,true));
        intakeToggle.onFalse(new SetIntake(intake,false));


    }


    /*
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
    */

    /*For AutoBuilder */
     public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}