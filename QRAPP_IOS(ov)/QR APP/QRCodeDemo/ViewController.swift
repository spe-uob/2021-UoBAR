//
//  ViewController.swift
//  QRCodeDemo
//
//  Created by Eleven on 4/21/22.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var labelTitle: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        labelTitle.text = "WELCOME \n TO \n UOB \n GARDENS"
        labelTitle.numberOfLines = 0
        labelTitle.lineBreakMode = .byCharWrapping
        labelTitle.textAlignment = .center
        
    }

    

}

