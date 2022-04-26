//
//  QRCode.swift
//  Chat
//
//  Created by apple on 2019/9/5.
//  Copyright © 2019 Raul Studio. All rights reserved.
//

import UIKit
import CoreImage


class QRCode {
    
    
    /// 生成二维码图片
    /// - Parameter content: 内容
    class func generator(_ content: String) -> UIImage?{
        
        guard let filter = CIFilter.init(name: "CIQRCodeGenerator") else {
            return nil
        }
        let data = content.data(using: .utf8)
        filter.setDefaults()
        
        filter.setValue(data, forKey: "inputMessage")
        filter.setValue("H", forKeyPath: "inputCorrectionLevel")
        guard let qrCode = filter.outputImage else { return nil }
        guard let image = adjustQRImage(qrCode) else {
            return nil
        }
        
        return image
    }
    
    class fileprivate func adjustQRImage(_ image: CIImage, size: CGSize = CGSize(width: 270, height: 270)) -> UIImage? {
        let extent = image.extent.integral
        let scale = min(size.width / extent.width, size.height / extent.height)
        let width = extent.width * scale
        let height = extent.height * scale
        let cs = CGColorSpaceCreateDeviceGray();
        // 3.2创建位图上下文
        let bitmapRef = CGContext(data: nil, width: Int(width), height: Int(height), bitsPerComponent: 8, bytesPerRow: 0, space: cs, bitmapInfo: 0)
        
        // 4.创建上下文
        let context = CIContext(options: nil)
        
        // 5.将CIImage转为CGImage
        let bitmapImage = context.createCGImage(image, from: extent)
        
        // 6.设置上下文渲染等级
        bitmapRef!.interpolationQuality = .none
        
        // 7.改变上下文的缩放
        bitmapRef?.scaleBy(x: scale, y: scale)
        
        // 8.绘制一张图片在位图上下文中
        bitmapRef?.draw(bitmapImage!, in: extent)
        
        // 9.从位图上下文中取出图片(CGImage)
        guard let scaledImage = bitmapRef?.makeImage() else {return nil}
        
        
        let image = UIImage(cgImage: scaledImage)
        
        return colorQRImage(image)
    }
    
    class fileprivate func colorQRImage(_ image: UIImage) -> UIImage?{
        // 设置二维码颜色
        let imageCI = CIImage(image: image)
        let colorFilter = CIFilter(name:"CIFalseColor")
        colorFilter?.setDefaults()

        // 设置图片
        colorFilter?.setValue(imageCI, forKeyPath: "inputImage")
        // 设置二维码颜色
        colorFilter?.setValue(CIColor.init(color: UIColor.black), forKeyPath: "inputColor0")
        
        // 设置背景颜色
        colorFilter?.setValue(CIColor.init(color: UIColor.white), forKeyPath: "inputColor1")
        guard let colorOutPutImage = colorFilter?.outputImage else {
            return nil
        }
        return UIImage(ciImage: colorOutPutImage)
    }
    
    
    /// 识别二维码图片
    /// - Parameter image: 图片
    class func recognizeImage(_ image: UIImage) -> String? {
        let detector: CIDetector = CIDetector(ofType: CIDetectorTypeQRCode, context: nil, options: [CIDetectorAccuracy: CIDetectorAccuracyHigh])!
        let img = CIImage(cgImage: (image.cgImage)!)
        let features: [CIFeature]? = detector.features(in: img, options: [CIDetectorAccuracy: CIDetectorAccuracyHigh])
        if features != nil && (features?.count)! > 0 {
            let feature = features![0]
            if feature.isKind(of: CIQRCodeFeature.self) {
                let tmpFeature = feature as! CIQRCodeFeature
                let scanResult = tmpFeature.messageString
                return scanResult
            }
        }
        
        return nil
    }
    
    
    
}
