//
//  QRScanViewController.swift
//  QRCodeDemo
//
//  Created by Eleven on 4/28/22.
//

import UIKit

class QRScanViewController: UIViewController {
    
    @IBOutlet weak var backTop: NSLayoutConstraint!
    
   
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
//        navigationController?.navigationBar.alpha = 0
        navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
//        navigationController?.navigationBar.alpha = 1
        navigationController?.setNavigationBarHidden(false, animated: animated)
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        backTop.constant = 0
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
    
    
}
