//
//  LocationManager.swift
//  QRCodeDemo
//
//  Created by Eleven on 4/28/22.
//

import UIKit
import CoreLocation
import Contacts

protocol LocationManagerDelegate {
    
    func locationDidChange(location: CLLocation)
    
    func locationAddress(address: String?)
    
    func locationAuthorization()
    
    func locationFailWithError(error: Error)
}

class LocationManager: NSObject {
    
    private lazy var locationManager : CLLocationManager = CLLocationManager()
    
    private lazy var currLocation: CLLocation = CLLocation()

    var delegate: LocationManagerDelegate?
    
    override init() {
        super.init()
        
    }
    
    func start() {
        locationManager.delegate = self
        locationManager.distanceFilter = 0
        locationManager.desiredAccuracy = kCLLocationAccuracyBestForNavigation
        locationManager.requestWhenInUseAuthorization()
        
        if CLLocationManager.locationServicesEnabled() && CLLocationManager.headingAvailable() {
            locationManager.startUpdatingLocation()
            locationManager.startUpdatingHeading()
            print("定位开始")
        } else {
            print("不能获得定位和航向数据")
        }
    }
    
    func stop() {
        locationManager.stopUpdatingHeading()
        locationManager.stopUpdatingLocation()
        locationManager.delegate = nil
    }
    
    private func geocoder(location: CLLocation) {
        let geocoder = CLGeocoder()
        geocoder.reverseGeocodeLocation(location) { (placemarks, error) in
            guard let placemarks = placemarks, placemarks.count > 0 else { return }
            
            let placemark: CLPlacemark = placemarks[0]
            let addressDictionary = placemark.postalAddress
            
            //            guard let country = addressDictionary?.country else { return }
            
            guard let city = addressDictionary?.city else { return }
            
            guard let subLocality = addressDictionary?.subLocality else { return }
            
            guard let street = addressDictionary?.street else { return }
            
            let address = " \(street)  \(subLocality) \(city)"
            
            self.delegate?.locationAddress(address: address)
        }
    }
}

extension LocationManager: CLLocationManagerDelegate {
    
    // 定位成功之后的回调方法，只要位置改变，就会调用这个方法
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else {
            return
        }
        delegate?.locationDidChange(location: location)
//        delegate?.speedDidChange(speed: location.speed)
        geocoder(location: location)
    }
    
    // 获得设备地理和地磁朝向数据，从而转动地理刻度表以及表上的文字标注
    func locationManager(_ manager: CLLocationManager, didUpdateHeading newHeading: CLHeading) {
//        delegate?.updateHeading(newHeading: newHeading)
    }
    
    // 判断设备是否需要校验，受到外来磁场干扰时
    func locationManagerShouldDisplayHeadingCalibration(_ manager: CLLocationManager) -> Bool {
        return true
    }
    
    // 定位代理失败回调
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("定位失败....\(error)")
        delegate?.locationFailWithError(error: error)
    }
    
    /// 如果授权状态发生变化时,调用
    ///
    /// - Parameters:
    ///   - manager: 定位管理器
    ///   - status: 当前的授权状态
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        switch status {
            case .notDetermined:
                print("用户未决定")
            case .restricted:
                print("受限制")
                //                showAlert(title: "Tips.Title".localized, message: "Tips.Message".localized)
                delegate?.locationAuthorization()
            case .denied:
                // 判断当前设备是否支持定位, 并且定位服务是否开启
                if CLLocationManager.locationServicesEnabled() {
                    print("定位开启,被拒绝")
                }else {
                    print("定位服务关闭")
                }
                //                showAlert(title: "Tips.Title".localized, message: "Tips.Message".localized)
                delegate?.locationAuthorization()
            case .authorizedAlways:
                print("前,后台定位授权")
            case .authorizedWhenInUse:
                print("前台定位授权")
            @unknown default:
                fatalError()
        }
    }
}
