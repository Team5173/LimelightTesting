package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.networktables.*;

public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private XboxController Controller;
  private boolean Bpressed;
  private NetworkTableEntry tx;
  private NetworkTableEntry ty;
  private NetworkTableEntry ta;

  public void robotInit() {
    m_myRobot = new DifferentialDrive(new Spark(0), new Spark(1));

    Controller = new XboxController(0);

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  }

  public void teleopPeriodic() {
    m_myRobot.tankDrive(Controller.getRawAxis(0), Controller.getRawAxis(1));

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0);
    double area = ta.getDouble(0);

    double radians = y/57.2958;

    double d = (62.25-21) / Math.tan(Math.toRadians(1)+Math.toRadians(y));

    SmartDashboard.putNumber("Camera Distance", d);

    SmartDashboard.putNumber("Limelight X", x);
    SmartDashboard.putNumber("Limelight Y", y);
    SmartDashboard.putNumber("Limelight A", area);

    //This changes the Camera Mode if you press the A button or if you press the B button
    if(Controller.getRawButton(1)){
			NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
		}else if(Controller.getRawButton(2)){
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
			NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    }

  }
}

