//
//  QRCodeViewController.swift
//  Chat
//
//  Created by apple on 2019/9/6.
//  Copyright © 2019 Raul Studio. All rights reserved.
//

import UIKit
//import SnapKit
import Photos


class QRCodeViewController: UIViewController {
    
   
    @IBOutlet weak var textFieldLink: UITextField!
    @IBOutlet weak var btnGQRCode: UIButton!
    
    @IBOutlet weak var imageView: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Create QR Code"
        
        permissions()
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesBegan(touches, with: event)
        view.endEditing(true)
    }
    
    func generatorCode(content: String) {
        let qrImage = QRCode.generator(content)
        imageView.image = qrImage
    }
    @IBAction func buttonGeneratorClicked(_ sender: Any) {
        view.endEditing(true)
        guard let text = textFieldLink.text?.trimmingCharacters(in: .whitespacesAndNewlines), text.count > 0 else { return }
        generatorCode(content: text)
    }
    
    
    @IBAction func btnSaveImageClicked(_ sender: Any){
        guard let image = imageView.toImage() else { return }
        
        UIImageWriteToSavedPhotosAlbum(image, self, #selector(imageSaved(_:didFinishSavingWithError:contextInfo:)), nil)
    }
    
    
    @objc func imageSaved(_ image: UIImage, didFinishSavingWithError error: NSError?, contextInfo: UnsafeRawPointer) {
        if let error = error {
             let ac = UIAlertController(title: "Failed", message: error.localizedDescription, preferredStyle: .alert)
             ac.addAction(UIAlertAction(title: "OK", style: .default))
             present(ac, animated: true)
         } else {
             let ac = UIAlertController(title: "Successfully", message: "The QR Code has been saved.", preferredStyle: .alert)
             ac.addAction(UIAlertAction(title: "OK", style: .default))
             present(ac, animated: true)
         }
    }
    
    func permissions() {
        let status = PHPhotoLibrary.authorizationStatus(for: .readWrite)
        switch status {
            case .notDetermined:
                if #available(iOS 9.0, *) {
                    PHPhotoLibrary.requestAuthorization { (status) in
                        DispatchQueue.main.async {
                            switch status {
                                case .authorized:
//                                    self.openPhoto()
                                    break
                                default:
                                    self.alertTip()
                            }
                        }
                    }
                }
            case .authorized:
//                openPhoto()
                break
            default:
                alertTip()
        }
    }
    
    func alertTip() {
        DispatchQueue.main.async(execute: { () -> Void in
            let alertController = UIAlertController(title: "Photo access is limited",
                                                    message: "Click \"Settings\" to allow access to your photos",
                                                    preferredStyle: .alert)
            
            let cancelAction = UIAlertAction(title:"Cancel", style: .cancel, handler:nil)
            
            let settingsAction = UIAlertAction(title:"Settings", style: .default, handler: {
                (action) -> Void in
                let url = URL(string: UIApplication.openSettingsURLString)
                if let url = url, UIApplication.shared.canOpenURL(url) {
                    if #available(iOS 10, *) {
                        UIApplication.shared.open(url, options: [:],
                                                  completionHandler: {
                            (success) in
                        })
                    } else {
                        UIApplication.shared.openURL(url)
                    }
                }
            })
            
            alertController.addAction(cancelAction)
            alertController.addAction(settingsAction)
            
            self.present(alertController, animated: true, completion: nil)
        })
    }
}
