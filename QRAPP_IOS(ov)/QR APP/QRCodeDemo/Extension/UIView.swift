//
//  UIView.swift
//  QRCodeDemo
//
//  Created by Eleven on 4/21/22.
//

import UIKit

extension UIView {
    
    func toImage() -> UIImage? {
        UIGraphicsBeginImageContextWithOptions(bounds.size, false, UIScreen.main.scale)
        var thumb: UIImage? = nil
        if let context = UIGraphicsGetCurrentContext() {
            context.clear(bounds)
            self.drawHierarchy(in: bounds, afterScreenUpdates: true)
            thumb = UIGraphicsGetImageFromCurrentImageContext()
        }
        UIGraphicsEndImageContext()
        return thumb
    }

}
