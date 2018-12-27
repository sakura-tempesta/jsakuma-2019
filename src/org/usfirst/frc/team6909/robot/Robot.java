/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team6909.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;

/**
 * 
 * @author jsakuma
 *
 */
public class Robot extends IterativeRobot {
	/******************************
	 * ロボット部品の定義
	 ******************************/
	// コントローラ
	private XboxController driver;
	
	// 足回り
	private PWMTalonSRX m_leftFrontDrive, m_rightFrontDrive,
						m_leftRearDrive, m_rightRearDrive;
	private SpeedControllerGroup m_leftDrive, m_rightDrive;
	private Encoder e_leftDriveEncoder, e_rightDriveEncoder;
	private ADXRS450_Gyro g_gyro;
	
	private Drivetrain drivetrain;
	
	/******************************
	 * robotInit: 最初に呼ばれるスクリプト
	 * - 部品などの初期化
	 ******************************/
	public void robotInit() {
		// ---------- コントローラ ---------- 
		driver = new XboxController(Constants.DriverPort);
		
		// ---------- 足回り ----------
		// モーター関連
		m_leftFrontDrive = new PWMTalonSRX(Constants.PWMLeftFrontDrive);
		m_leftRearDrive = new PWMTalonSRX(Constants.PWMLeftRearDrive);
		m_rightFrontDrive = new PWMTalonSRX(Constants.PWMRightFrontDrive);
		m_rightRearDrive = new PWMTalonSRX(Constants.PWMRightRearDrive);
		m_leftDrive = new SpeedControllerGroup(m_leftFrontDrive, m_leftRearDrive);
		m_rightDrive = new SpeedControllerGroup(m_rightFrontDrive, m_rightRearDrive);
		
		// エンコーダー関連
		e_leftDriveEncoder = new Encoder(Constants.DIODriveLeftEncoderA, Constants.DIODriveLeftEncoderB);
		e_leftDriveEncoder.setDistancePerPulse(Constants.DriverDistancePerPulse);
		e_rightDriveEncoder = new Encoder(Constants.DIODriveRightEncoderA, Constants.DIODriveRightEncoderB);
		e_rightDriveEncoder.setDistancePerPulse(Constants.DriverDistancePerPulse);
		
		// Gyro: ジャイロは特別なポートを使うので、ポートとかはない。
		g_gyro = new ADXRS450_Gyro();
		
		// Drivetrain Wrapper
		drivetrain = new Drivetrain(m_leftDrive, m_rightDrive,
									e_leftDriveEncoder, e_rightDriveEncoder, g_gyro);
	}
	
	/******************************
	 * teleopPeriodic: Telopモードのiteration
	 ******************************/
	public void teleopPeriodic() {
		// ---------- 足回り ----------
		// ToDo: 不感帯をつける
		if (driver.getAButton()) {
			// PID Control
			//	ボタンを押した地点から1m直進する
			if (!drivetrain.isPIDEnabled()) {
				// PIDが作動していなかった場合は、現在地から相対的な距離に目標点を定めてPIDを走らせる
				drivetrain.setRelativeStraightSetpoint(100);
			}
			drivetrain.enablePID();
		} else {
			// Manual control
			drivetrain.disablePID();
			drivetrain.arcadeDrive(driver.getY(Hand.kLeft), driver.getX(Hand.kRight));
		}
	}
}
