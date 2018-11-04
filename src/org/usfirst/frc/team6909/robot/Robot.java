/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team6909.robot; 
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {
	private XboxController driver;

	private PWMTalonSRX m_leftArm;
	private PWMTalonSRX m_rightArm;
	
	Robot() {
		driver = new XboxController(0);
		
		m_leftArm = new PWMTalonSRX(5);
		m_rightArm = new PWMTalonSRX(6);
		m_rightArm.setInverted(true);
	}
	
	public void teleopPeriodic() {
		// 左のみが押された場合: armSpeed = 1
		// 右のみが押された場合: armSpeed = -1
		// 両方押された場合: armSpeed = 0 → 何も動かない
		double armSpeed = driver.getTriggerAxis(Hand.kLeft) - driver.getTriggerAxis(Hand.kRight);

		// Arm用の不感帯を設定
		// driverからの入力が0.2以下の場合は無視し、それ以上の場合はMAXで動かす。
		if (Math.abs(armSpeed) > 0.2) {
			// 左右のarmは逆回転すべきなので、m_rightArmを逆向きに
			m_leftArm.set(1.0);
			m_rightArm.set(-1.0);
		} else {
			m_leftArm.set(0.0);
			m_rightArm.set(0.0);
		}
	}
}
