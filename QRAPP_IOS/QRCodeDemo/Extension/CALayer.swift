//
//  CALayer.swift
//  QRCodeDemo
//
//  Created by Eleven on 4/21/22.
//

import UIKit

extension CALayer {
    
    func clipCorners(_ radus: CGFloat, _ borderColor: UIColor = .clear, _ borderWidth: CGFloat = 0) {
        layoutIfNeeded()
        self.borderColor = borderColor.cgColor
        self.borderWidth = borderWidth
        self.cornerRadius = radus
        self.masksToBounds = true
    }
    
    
    /// 阴影
    /// - Parameter sColor: shadowColor
    /// - Parameter sOffset: shadowOffset
    /// - Parameter sOpacity: 0-1 shadowOpacity
    /// - Parameter sRadius: shadowRadius
    /// - Parameter bRadius: borderRadius
    /// - Parameter bColor: borderColor
    /// - Parameter bWidth: borderWhidth
    func setShadow(sColor: UIColor, sOffset: CGSize, sOpacity: Float, sRadius: CGFloat, bRadius: CGFloat = 0, bColor: UIColor? = .clear, bWidth: CGFloat = 0) {
        self.borderColor = bColor?.cgColor
        self.borderWidth = bWidth
        self.cornerRadius = bRadius
        self.shadowColor = sColor.cgColor
        self.shadowOpacity = sOpacity
        self.shadowRadius = sRadius
        self.shadowOffset = sOffset
    }
    
    
    /// Sketch 阴影效果
    /// - Parameters:
    ///   - color: shadowColor
    ///   - alpha: shadowOpacity
    ///   - x: shadowOffsetX
    ///   - y: shadowOffsetY
    ///   - blur: shadowRadius / 2, 对应 Sketch 里阴影的 “模糊” 的设置，值是 20 / 2 = 10
    ///   - spread: shadowPath, spread 对应 Sketch 里阴影的 “扩展”
    func setShadowOnSketch(color: UIColor? = .black,
                           alpha: CGFloat = 0.5,
                           x: CGFloat = 0, y: CGFloat = 2,
                           blur: CGFloat = 4,
                           spread: CGFloat = 0) {
        shadowOffset = CGSize(width: x, height: y)
        shadowRadius = blur * 0.5
        shadowColor = color?.cgColor
        shadowOpacity = Float(alpha)
        
        let rect = bounds.insetBy(dx: -spread, dy: -spread)
        let path = UIBezierPath(roundedRect: rect, cornerRadius: cornerRadius)
        shadowPath = path.cgPath
    }
    
}
