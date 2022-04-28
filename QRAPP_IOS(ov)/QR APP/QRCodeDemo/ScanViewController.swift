//
//  ScanViewController.swift
//  Chat
//
//  Created by apple on 2019/9/6.
//  Copyright © 2019 Raul Studio. All rights reserved.
//

import UIKit
import AVFoundation
import SafariServices

class ScanViewController: UIViewController {
    
    lazy var scanImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.image = UIImage(named: "home_ic_scan_frame")
        return imageView
    }()
    
//    fileprivate lazy var rightButtonItem: UIBarButtonItem = {
//        let button = UIBarButtonItem(title:"相册", style: .done, target: self, action: #selector(handleRightBarButtonTapped(_:)))
//        button.tintColor = .black
//        return button
//    }()
    
    
    var readerView: QRCodeReaderView = QRCodeReaderView(frame: CGRect(x: 0, y: 0, width: kScreenWidth, height: kScreenHeight))
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.readerView.startScan()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        self.readerView.stopScan()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        title = "Scan"
//        navigationItem.rightBarButtonItem = rightButtonItem
        view.addSubview(self.readerView)
        constraintsUI()
        self.readerView.delegate = self
        
        
//        Permission.authorizeCamerWith { (final) in
//            if final {
//                self.captureSession?.startRunning()
//            }
//        }
    }
    
    override func viewWillLayoutSubviews() {
        super.viewWillLayoutSubviews()
    }
    
    func constraintsUI() {
        
      
    }
    
    
    
    @objc func handleRightBarButtonTapped(_ sender: Any){
        imagePickerController()
    }
    
    func imagePickerController() {
        if UIImagePickerController.isSourceTypeAvailable(.photoLibrary) {
            DispatchQueue.main.async {
                let picker = UIImagePickerController()
                picker.delegate = self
                //                picker.allowsEditing = true
                picker.sourceType = .photoLibrary
                self.present(picker, animated: true)
            }
        }
    }
    
    func recognizeQRCode(_ image: UIImage?) {
        guard let image = image else { return }
        guard let codeContent = QRCode.recognizeImage(image) else { return }
        
        
        print(codeContent)
    }
    
    
}

extension ScanViewController: UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        guard let pickedImage = info[UIImagePickerController.InfoKey.originalImage]
                as? UIImage else {
            print("...")
            return
        }
        picker.dismiss(animated: true) {
            self.recognizeQRCode(pickedImage)
        }
    }
}

extension ScanViewController: QRCodeReaderDelegate {
    func scanResult(_ result: String) {
        
        let ac = UIAlertController(title: "", message: result, preferredStyle: .alert)
        ac.addAction(UIAlertAction(title: "OK", style: .default, handler: { action in
            if result.hasPrefix("https://") || result.hasPrefix("http://") {
                let url = URL(string: result)!
                let webView = SFSafariViewController(url: url)
                self.present(webView, animated: true)
            }
        }))
        
        present(ac, animated: true)
        
    }
}
