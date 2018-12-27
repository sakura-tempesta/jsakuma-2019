package org.usfirst.frc.team6909.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends DifferentialDrive {
	private SpeedController m_leftDrive, m_rightDrive;
	private Encoder e_leftDriveEncoder, e_rightDriveEncoder;
	private ADXRS450_Gyro g_gyro;
	
	public Drivetrain(SpeedController m_leftDrive, SpeedController m_rightDrive, Encoder e_leftDriveEncoder,
			Encoder e_rightDriveEncoder, ADXRS450_Gyro g_gyro) {
		super(m_leftDrive, m_rightDrive);
		
		this.m_leftDrive = m_leftDrive;
		this.m_rightDrive = m_rightDrive;
		this.e_leftDriveEncoder = e_leftDriveEncoder;
		this.e_rightDriveEncoder = e_rightDriveEncoder;
		this.g_gyro = g_gyro;
	}
}
