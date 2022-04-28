//
//  SettingsViewController.swift
//  QRCodeDemo
//
//  Created by Eleven on 4/28/22.
//

import UIKit
import CoreLocation


class SettingsViewController: UIViewController {
    
    @IBOutlet weak var backTop: NSLayoutConstraint!
    
    
    @IBOutlet weak var switchGPS: UISwitch!
    
    
    private lazy var locationManager = LocationManager()
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        //        navigationController?.navigationBar.alpha = 0
        navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        //        navigationController?.navigationBar.alpha = 1
        navigationController?.setNavigationBarHidden(false, animated: animated)
    }
    
    deinit {
        locationManager.stop()
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        locationManager.delegate = self
        // Do any additional setup after loading the view.
        backTop.constant = 0
        
        switchGPS.isOn = false
    }
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    
    @IBAction func buttonBackClicked(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    @IBAction func switchGPSChanged(_ sender: Any) {
        
        locationManager.start()
    }
}

extension SettingsViewController: LocationManagerDelegate {
    func locationDidChange(location: CLLocation) {
        
    }
    
    func locationAddress(address: String?) {
        
    }
    
    func locationAuthorization() {
        
    }
    
    func locationFailWithError(error: Error) {
        
    }
    
    
}
