//
//  UIImage+Add.swift
//  Chat
//
//  Created by apple on 2019/7/24.
//  Copyright © 2019 Raul Studio. All rights reserved.
//

import UIKit

extension UIImage {
    
    public convenience init?(color:UIColor) {
        let size = CGSize(width: 1, height: 1)
        let rect = CGRect(origin: .zero, size: size)
        UIGraphicsBeginImageContextWithOptions(rect.size, false, 0.0)
        color.setFill()
        UIRectFill(rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        guard let cgImage = image?.cgImage else {
            return nil
        }
        self.init(cgImage:cgImage)
    }
    
    public convenience init?(color: UIColor, size: CGSize = CGSize(width: 1, height: 1)) {
        let rect = CGRect(origin: .zero, size: size)
        UIGraphicsBeginImageContextWithOptions(rect.size, false, 0.0)
        color.setFill()
        UIRectFill(rect)
        let image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        guard let cgImage = image?.cgImage else { return nil }
        self.init(cgImage: cgImage)
    }
    
    static func imageFromColor(_ color: UIColor, transparent: Bool = true, size: CGSize = CGSize(width: 1, height: 1)) -> UIImage? {
        let rect = CGRect(origin: .zero, size: size)
        let path = UIBezierPath(rect: rect)
        UIGraphicsBeginImageContextWithOptions(rect.size, !transparent, UIScreen.main.scale)
        
        var image: UIImage?
        if let context = UIGraphicsGetCurrentContext() {
            context.addPath(path.cgPath)
            context.setFillColor(color.cgColor)
            context.fillPath()
            image = UIGraphicsGetImageFromCurrentImageContext()
            UIGraphicsEndImageContext()
        }
        return image
    }
    
    
    func compress() {
        
    }
    
}


fileprivate extension CALayer {

    func shapeLayer() -> CAShapeLayer? {
        guard let sublayers = sublayers else {
            return nil
        }
        for layer in sublayers {
            if let shape = layer as? CAShapeLayer {
                return shape
            }
            return layer.shapeLayer()
        }
        return nil
    }
}



