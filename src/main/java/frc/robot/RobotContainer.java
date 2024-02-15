package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

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
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.AutoCMDs.*;
import frc.robot.commands.Feeder.FeederCMD;
import frc.robot.commands.Intake.*;
import frc.robot.commands.Shooter.*;
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
    private final JoystickButton intakeSmartToggle = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /*Shooter */
    private final int rightTiggerAxis = XboxController.Axis.kRightTrigger.value;

    /*Auto Chooser */
    private final SendableChooser<Command> autoChooser;

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();

    /* Motor Buttoms */
    private final TalonSRXMotors talonSRXMotors = new TalonSRXMotors();




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

        // Configure the button bindings
        configureButtonBindings();
        configureSubsystemDefaults();

        //PP Auto Commands
        NamedCommands.registerCommand("Shooting", new ShootCMD(talonSRXMotors));
        NamedCommands.registerCommand("Fire", new FireCMD(talonSRXMotors));
        NamedCommands.registerCommand("IntakeOut", new IntakeOut(intake));

        //Auto Chooser
        autoChooser = AutoBuilder.buildAutoChooser("New Auto");
        SmartDashboard.putData("Auto Chooser", autoChooser);
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

        //turn2angle with A B X Y
        new JoystickButton(driver, XboxController.Button.kY.value).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(0)));
        new JoystickButton(driver, XboxController.Button.kX.value).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(90)));
        new JoystickButton(driver, XboxController.Button.kA.value).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(180)));
        new JoystickButton(driver, XboxController.Button.kB.value).whileTrue(new TurntoAngle(s_Swerve,
            () -> -driver.getRawAxis(translationAxis), 
            () -> -driver.getRawAxis(strafeAxis),
            Rotation2d.fromDegrees(270))
        );

        new POVButton(driver, 0).whileTrue(new ShooterMotorOn(talonSRXMotors,true) );
        new POVButton(driver, 90).whileTrue(new FeederCMD(talonSRXMotors,true) );
        new POVButton(driver, 180).whileTrue(new IntakeMotorCMD(intake,true) );
        new POVButton(driver, 270).whileTrue(new IntakePistonCMD(intake,true) );


        new Trigger(() -> driver.getRawAxis(rightTiggerAxis) > .5).whileTrue(new ShooterCMD(talonSRXMotors) );

        
        intakeToggle.whileTrue(new IntakeCMD(intake));
        intakeSmartToggle.whileTrue(new SmartIntake(intake));

    }

    private void configureSubsystemDefaults() {
        intake.setDefaultCommand(new IntakeDefaultCMD(intake));
        talonSRXMotors.setDefaultCommand(new ShooterDefaultCMD(talonSRXMotors));
    
    }
    

    /*For AutoBuilder */
     public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
