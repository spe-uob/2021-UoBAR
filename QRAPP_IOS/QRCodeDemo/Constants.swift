//
//  Constants.swift
//  QRCodeDemo
//
//  Created by Eleven on 4/21/22.
//

import UIKit

public let kScreenWidth: CGFloat = UIScreen.main.bounds.width

public let kScreenHeight: CGFloat = UIScreen.main.bounds.height

public var kStatusBarHeight: CGFloat = UIDevice.current.isiPhoneX ? 44 : 20

public let kNavigationBarHeight: CGFloat =  64

public let kTabBarHeight: CGFloat =  49

public let kiPhoneXTopHeight: CGFloat =  UIDevice.current.isiPhoneX ? 24 : 0

public let kiPhoneXBottomHeight: CGFloat =  UIDevice.current.isiPhoneX ? 34 : 0

public let klineHeight: CGFloat = 1/UIScreen.main.scale

public let kTopInset: CGFloat = UIDevice.current.isiPhoneX ? 44 : 0

extension UIDevice{
    
    @objc public var isiPhoneX: Bool {
        if UIDevice.current.userInterfaceIdiom == .phone{
            let maxLenght = kScreenWidth > kScreenHeight ? kScreenWidth : kScreenHeight
            if maxLenght >= 812 {
                return true
            }
        }
        return false
        }
}
