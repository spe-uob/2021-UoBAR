//
//  QRCodeReaderView.swift
//  Chat
//
//  Created by apple on 2019/9/7.
//  Copyright © 2019 Raul Studio. All rights reserved.
//

import UIKit
import AVFoundation

protocol QRCodeReaderDelegate: NSObjectProtocol {
    func scanResult(_ result: String)
}

class QRCodeReaderView: UIView {
    var captureSession: AVCaptureSession?
    let device: AVCaptureDevice? = AVCaptureDevice.default(for: .video)
    var videoPreviewLayer : AVCaptureVideoPreviewLayer?
    
    var topView : UIView?
    var leftView : UIView?
    var rightView : UIView?
    var bottomView : UIView?
    
    var lineImageView: UIImageView?
    
    var leftTopLayer = CAShapeLayer()
    var rightTopLayer = CAShapeLayer()
    var leftBottomLayer = CAShapeLayer()
    var rightBottomLayer = CAShapeLayer()
    
    let defaultSideWidth : CGFloat = 60
    
    weak var delegate: QRCodeReaderDelegate?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = .clear
        create()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    fileprivate func create() {
        
        guard let videoDevice = AVCaptureDevice.default(for: .video),
            let deviceInput = try? AVCaptureDeviceInput(device: videoDevice) else {
                return
        }
        
        captureSession = AVCaptureSession()
        captureSession?.addInput(deviceInput)
        //output
        let captureMetadataOutput = AVCaptureMetadataOutput()
        captureSession?.addOutput(captureMetadataOutput)
        captureMetadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
        //interprets qr codes only
        captureMetadataOutput.metadataObjectTypes = [.qr]
        captureMetadataOutput.rectOfInterest = getScanRectWithPreView()
        
        
        captureSession?.sessionPreset = .high
        //preview
        guard let captureSession = captureSession else { return }
        self.videoPreviewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        self.videoPreviewLayer?.videoGravity = .resizeAspectFill
        self.videoPreviewLayer?.frame = self.layer.bounds
        self.videoPreviewLayer?.zPosition = 0
        self.layer.insertSublayer(self.videoPreviewLayer!, at: 0)
        
        if videoDevice.isFocusPointOfInterestSupported && videoDevice.isFocusModeSupported(.continuousAutoFocus) {
            do {
                try deviceInput.device.lockForConfiguration()
                deviceInput.device.focusMode = .continuousAutoFocus
                deviceInput.device.unlockForConfiguration()
            } catch let error {
                print(error.localizedDescription)
            }
        }
    }
    
    
    fileprivate func getScanRectWithPreView() -> CGRect
    {
        //扫码区域坐标
        let cropRect =  scanRect()
        //计算兴趣区域
        var rectOfInterest:CGRect
        
        //ref:http://www.cocoachina.com/ios/20141225/10763.html
        let size = CGSize(width: kScreenWidth, height: kScreenHeight - kNavigationBarHeight);
        let p1 = size.height/size.width;
        
        let p2:CGFloat = 1920.0/1080.0 //使用了1080p的图像输出
        if p1 < p2 {
            let fixHeight = size.width * 1920.0 / 1080.0;
            let fixPadding = (fixHeight - size.height)/2;
            rectOfInterest = CGRect(x: (cropRect.origin.y + fixPadding)/fixHeight,
                                    y: cropRect.origin.x/size.width,
                                    width: cropRect.size.height/fixHeight,
                                    height: cropRect.size.width/size.width)
        } else {
            let fixWidth = size.height * 1080.0 / 1920.0;
            let fixPadding = (fixWidth - size.width)/2;
            rectOfInterest = CGRect(x: cropRect.origin.y/size.height,
                                    y: (cropRect.origin.x + fixPadding)/fixWidth,
                                    width: cropRect.size.height/size.height,
                                    height: cropRect.size.width/fixWidth)
        }
        return rectOfInterest
    }
    
    fileprivate func scanRect() -> CGRect {
        let scanZoneSize = CGSize(width: kScreenWidth - self.defaultSideWidth * 2, height: kScreenWidth - self.defaultSideWidth * 2)
        let x = self.defaultSideWidth
        let y = kScreenHeight / 2 - scanZoneSize.height / 2 - kNavigationBarHeight
        let scanRect = CGRect(x: x, y: y, width: scanZoneSize.width, height: scanZoneSize.height)
        return scanRect
    }
    
    override func draw(_ rect: CGRect) {
        //        guard let context = UIGraphicsGetCurrentContext() else {
        //            return
        //        }
        
        let srect = scanRect()
        let brect = CGRect(x: srect.origin.x - 10, y: srect.origin.y - 10, width: srect.width + 20, height: srect.height + 20)
        
        self.creatOtherView(srect)
        
        self.setScanZoneLineBorder(brect)
    }
    
    func creatOtherView(_ scanRect : CGRect) {
        let allAlpha : CGFloat = 0.5
        
        //最上部的View
        if topView != nil {
            topView?.removeFromSuperview()
            topView = nil
        }
        topView = UIView.init(frame: CGRect(x: 0, y: 0, width: kScreenWidth, height: scanRect.origin.y))
        topView!.alpha = allAlpha
        topView!.backgroundColor = UIColor.black
        self.addSubview(topView!)
        
        //左侧的View
        if leftView != nil {
            leftView?.removeFromSuperview()
            leftView = nil
        }
        leftView = UIView.init(frame: CGRect(x: 0, y: scanRect.origin.y, width: self.defaultSideWidth, height: scanRect.size.height))
        leftView!.backgroundColor = UIColor.black
        leftView!.alpha = allAlpha
        self.addSubview(leftView!)
        
        
        //右侧的View
        if rightView != nil {
            rightView?.removeFromSuperview()
            rightView = nil
        }
        rightView = UIView.init(frame: CGRect(x: scanRect.maxX, y: scanRect.origin.y, width: self.defaultSideWidth, height: scanRect.size.height))
        rightView!.backgroundColor = UIColor.black
        rightView!.alpha = allAlpha
        self.addSubview(rightView!)
        
        
        //底部的View
        if bottomView != nil {
            bottomView?.removeFromSuperview()
            bottomView = nil
        }
        bottomView = UIView.init(frame: CGRect(x: 0,y: scanRect.maxY, width: kScreenWidth, height: kScreenHeight - scanRect.maxY))
        bottomView!.backgroundColor = UIColor.black
        bottomView!.alpha = allAlpha
        self.addSubview(bottomView!)
        //
        //        let detailLabel = UILabel.init(frame: CGRect(x: 0, y: 10, width: kScreenWidth, height: 20))
        //        detailLabel.backgroundColor = UIColor.clear
        //        detailLabel.textColor = UIColor.white
        //        detailLabel.text = "将条形码放入框内，即可自动扫描"
        //        detailLabel.font = UIFont.systemFont(ofSize: 16)
        //        detailLabel.textAlignment = .center
        //        bottomView!.addSubview(detailLabel)
        
    }
    
    func setScanZoneLineBorder(_ scanRect : CGRect) {
        leftTopLayer.removeFromSuperlayer()
        rightTopLayer.removeFromSuperlayer()
        leftBottomLayer.removeFromSuperlayer()
        rightBottomLayer.removeFromSuperlayer()
        //左上角的框
        let leftTopBezierPath = UIBezierPath()
        leftTopBezierPath.move(to: CGPoint(x: scanRect.minX + 15, y: scanRect.minY - 2))
        leftTopBezierPath.addLine(to: CGPoint(x: scanRect.minX - 2, y: scanRect.minY - 2))
        leftTopBezierPath.addLine(to: CGPoint(x: scanRect.minX - 2, y: scanRect.minY + 15))
        leftTopLayer.path = leftTopBezierPath.cgPath
        leftTopLayer.lineWidth = 4
        leftTopLayer.strokeColor = UIColor.blue.cgColor
        leftTopLayer.fillColor = UIColor.clear.cgColor
        self.layer.addSublayer(leftTopLayer)
        
        //右上角的框
        let rightTopBezierPath = UIBezierPath()
        rightTopBezierPath.move(to: CGPoint(x: scanRect.maxX - 15, y: scanRect.minY - 2))
        rightTopBezierPath.addLine(to: CGPoint(x: scanRect.maxX + 2, y: scanRect.minY - 2))
        rightTopBezierPath.addLine(to: CGPoint(x: scanRect.maxX + 2, y: scanRect.minY + 15))
        rightTopLayer.path = rightTopBezierPath.cgPath
        rightTopLayer.lineWidth = 4
        rightTopLayer.strokeColor = UIColor.blue.cgColor
        rightTopLayer.fillColor = UIColor.clear.cgColor
        self.layer.addSublayer(rightTopLayer)
        
        //左下角
        let leftBottomBezierPath = UIBezierPath()
        leftBottomBezierPath.move(to: CGPoint(x: scanRect.minX + 15, y: scanRect.maxY + 2))
        leftBottomBezierPath.addLine(to: CGPoint(x: scanRect.minX - 2, y: scanRect.maxY + 2))
        leftBottomBezierPath.addLine(to: CGPoint(x: scanRect.minX - 2, y: scanRect.maxY - 15))
        leftBottomLayer.path = leftBottomBezierPath.cgPath
        leftBottomLayer.lineWidth = 4
        leftBottomLayer.strokeColor = UIColor.blue.cgColor
        leftBottomLayer.fillColor = UIColor.clear.cgColor
        self.layer.addSublayer(leftBottomLayer)
        
        
        //右下角
        let rightBottomBezierPath = UIBezierPath()
        rightBottomBezierPath.move(to: CGPoint(x: scanRect.maxX + 2, y: scanRect.maxY - 15))
        rightBottomBezierPath.addLine(to: CGPoint(x: scanRect.maxX + 2, y: scanRect.maxY + 2))
        rightBottomBezierPath.addLine(to: CGPoint(x: scanRect.maxX - 15, y: scanRect.maxY + 2))
        rightBottomLayer.path = rightBottomBezierPath.cgPath
        rightBottomLayer.lineWidth = 4
        rightBottomLayer.strokeColor = UIColor.blue.cgColor
        rightBottomLayer.fillColor = UIColor.clear.cgColor
        self.layer.addSublayer(rightBottomLayer)
        
    }
    
    fileprivate func scanLine() {
        
        let srect = scanRect()
        let rect = CGRect(x: srect.origin.x, y: srect.origin.y, width: srect.width, height: 2)
        
        if self.lineImageView == nil {
            self.lineImageView = UIImageView.init(frame: rect)
            self.lineImageView?.image = UIImage(named: "home_ic_scan_line")
            self.addSubview(self.lineImageView!)
        }
        
        let postion = self.lineImageView?.layer.position
        let transitionAnimation = CABasicAnimation.init(keyPath: "position")
        transitionAnimation.fromValue = postion
        transitionAnimation.toValue = CGPoint(x: postion!.x, y: postion!.y + srect.height)
        transitionAnimation.duration = 1.8
        transitionAnimation.repeatCount = 999
        transitionAnimation.autoreverses = true
        self.lineImageView?.layer.add(transitionAnimation, forKey: "transitionAnimation")
    }
    
    func startLineAnimation() {
        scanLine()
        let pauseTime = self.lineImageView?.layer.timeOffset
        self.lineImageView?.layer.speed = 1
        self.lineImageView?.layer.beginTime = 0
        self.lineImageView?.layer.timeOffset = 0
        let timeSincePause = (self.lineImageView?.layer.convertTime(CACurrentMediaTime(), from: nil))! - pauseTime!
        self.lineImageView?.layer.beginTime = timeSincePause
        
    }
    
    func stopLineAnimation() {
        let pauseTime = self.lineImageView?.layer.convertTime(CACurrentMediaTime(), from: nil)
        self.lineImageView?.layer.speed = 0
        self.lineImageView?.layer.timeOffset = pauseTime!
    }
    
    func startScan() {
        startLineAnimation()
        self.captureSession?.startRunning()
    }
    
    func stopScan() {
        stopLineAnimation()
        self.captureSession?.stopRunning()
    }
}

extension QRCodeReaderView: AVCaptureMetadataOutputObjectsDelegate{
    func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        guard let readableCode = metadataObjects.first as? AVMetadataMachineReadableCodeObject,let result = readableCode.stringValue else { return }
        AudioServicesPlaySystemSound(SystemSoundID(kSystemSoundID_Vibrate))
        stopScan()
        if let delegate = delegate {
            delegate.scanResult(result)
        }
    }
}
